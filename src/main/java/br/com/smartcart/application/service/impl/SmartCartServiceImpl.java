package br.com.smartcart.application.service.impl;

import br.com.smartcart.application.service.SmartCartService;
import br.com.smartcart.domain.entities.Invoice;
import br.com.smartcart.domain.entities.Product;
import br.com.smartcart.domain.entities.ProductPrice;
import br.com.smartcart.domain.entities.Store;
import br.com.smartcart.infraestructure.repositories.InvoiceRepository;
import br.com.smartcart.infraestructure.repositories.ProductPriceRepository;
import br.com.smartcart.infraestructure.repositories.ProductRepository;
import br.com.smartcart.infraestructure.repositories.StoreRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import static br.com.smartcart.application.util.ConvertUtil.toBigDecimal;

@Service
public class SmartCartServiceImpl implements SmartCartService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final ProductPriceRepository productPriceRepository;
    private final InvoiceRepository invoiceRepository;

    public SmartCartServiceImpl(ProductRepository productRepository,
                                StoreRepository storeRepository,
                                ProductPriceRepository productPriceRepository,
                                InvoiceRepository invoiceRepository) {
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
        this.productPriceRepository = productPriceRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public void importRecept(String url) {

        try {

            var html = Jsoup.connect(url).get().html();
            Document doc = Jsoup.parse(html);

            Store store = saveStore(doc);
            var number = getInvoiceNumber(doc);
            var invoices = invoiceRepository.findByNumber(number);
            if (!CollectionUtils.isEmpty(invoices)) {
                return;
            }
            Invoice invoice = saveInvoice(doc, store);

            doc.select("tr").forEach(tr -> {
                var name = tr.select("span.txtTit").get(0).ownText().trim();
                var price = tr.select("span.RvlUnit").get(0).ownText().trim();
                Product product = saveProduct(name);
                savePrice(price, product, invoice);
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private Invoice saveInvoice(Document doc, Store store) {
        var number = getInvoiceNumber(doc);
        var series = getSeriesNumber(doc);
        var dtIssue = getPurchaseDateTime(doc);
        return invoiceRepository.save(Invoice.builder()
                .dtIssue(dtIssue)
                .storeId(store.getStoreId())
                .number(number)
                .series(series)
                .build());
    }

    private String getSeriesNumber(Document doc) {
        var text = doc.select("ul li").get(0).text();
        var pattern = Pattern.compile("Série:\\s*(\\d+)");
        var matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group(1);
        }
        // todo: logar em caso de falha e pensar como proceder nessa situação
        return "0";
    }

    private String getInvoiceNumber(Document doc) {
        var text = doc.select("ul li").get(0).text();
        var pattern = Pattern.compile("Número:\\s*(\\d+)");
        var matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group(1);
        }
        // todo: logar em caso de falha e pensar como proceder nessa situação
        return "0";
    }

    private void savePrice(String price, Product product, Invoice invoice) {

        var productPrices = productPriceRepository.findByProductIdAndInvoiceId(product.getProductId(), invoice.getInvoiceId());
        if (CollectionUtils.isEmpty(productPrices)) {
            productPriceRepository.save(ProductPrice.builder()
                    .price(toBigDecimal(price))
                    .invoiceId(invoice.getInvoiceId())
                    .productId(product.getProductId())
                    .build());
        }
    }

    private Product saveProduct(String name) {

        var products = productRepository.findByName(name);
        if (CollectionUtils.isEmpty(products)) {
            return productRepository.save(Product.builder().name(name).build());
        }
        return products.get(0);
    }

    private LocalDateTime getPurchaseDateTime(Document doc) {
        var information = doc.select("ul li").get(0).ownText();
        Pattern patternDateTime = Pattern.compile("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2}");
        var matcher = patternDateTime.matcher(information);
        if (matcher.find()) {
            var dateTimeString = matcher.group();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            return LocalDateTime.parse(dateTimeString, formatter);
        }
        // todo: informar erro no log por não encontrar a data e a hora
        return LocalDateTime.now();
    }

    private Store saveStore(Document doc) {
        var div = doc.select("div.txtCenter");
        var name = div.select("div.txtTopo").get(0).ownText().trim(); // nome mercado
        var cnpj = div.select("div.text").get(0).ownText().replaceAll("\\D", ""); // cnpj
        var address = div.select("div.text").get(1).ownText().trim(); // endereço

        var stores = storeRepository.findByName(name);
        if (CollectionUtils.isEmpty(stores)) {
            return storeRepository.save(Store.builder()
                    .name(name)
                    .cnpj(cnpj)
                    .address(address).build());
        }
        return stores.get(0);
    }
}
