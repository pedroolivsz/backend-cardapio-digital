package com.io.github.pedroolivsz.cardapio.exceptions;

public class FoodNotFoundException extends RuntimeException {
    public FoodNotFoundException(Long id) {
        super("Food not found with id: " + id);
    }
}
