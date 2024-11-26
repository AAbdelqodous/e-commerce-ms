package com.abdelqodous.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
        Integer id,
        @NotNull(message = "Product name is required")
        String name,
        @NotNull(message = "Product description is required")
        String description,
        @Positive(message = "Available quantity must be +ve")
        double availableQuantity,
        @Positive(message = "Product price must be +ve")
        BigDecimal price,
        @NotNull(message = "Product category is required")
        Integer categoryId
) {
}
