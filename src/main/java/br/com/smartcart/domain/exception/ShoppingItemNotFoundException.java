package br.com.smartcart.domain.exception;

public class ShoppingItemNotFoundException extends RuntimeException {

    public ShoppingItemNotFoundException() {
        super("There are no shopping list available");
    }


    public ShoppingItemNotFoundException(Long id) {
        super("Shopping item not found: " + id);
    }
}
