package br.com.smartcart.application.service;

import br.com.smartcart.domain.valueobjects.response.ProductRsVO;

import java.util.List;

public interface ProductService {
    List<ProductRsVO> search(String name);
}
