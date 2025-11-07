package br.com.smartcart.domain.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String name, Long id) {
        super("Product '" + name + "' and id '" + id + "' not found" );
    }
}
