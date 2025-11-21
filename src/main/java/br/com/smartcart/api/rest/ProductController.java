package br.com.smartcart.api.rest;

import br.com.smartcart.application.service.ProductService;
import br.com.smartcart.domain.valueobjects.response.ProductRsVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<ProductRsVO>> search(@PathVariable String name) {

        return ResponseEntity.ok(productService.search(name));

    }
}
