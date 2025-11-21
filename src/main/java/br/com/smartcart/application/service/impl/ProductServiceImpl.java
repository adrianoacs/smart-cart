package br.com.smartcart.application.service.impl;

import br.com.smartcart.application.service.ProductService;
import br.com.smartcart.domain.valueobjects.response.ProductRsVO;
import br.com.smartcart.infraestructure.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.smartcart.application.util.ProductBuilder.toProductRsVOList;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    @Override
    public List<ProductRsVO> search(String name) {
        return toProductRsVOList(productRepository.findByNameContainingIgnoreCase(name));
    }
}
