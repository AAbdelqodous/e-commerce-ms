package com.abdelqodous.ecommerce.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        String id,
        @NotNull(message = "Customer 1st name is required")
        String firstname,
        @NotNull(message = "Customer last name is required")
        String lastname,
        @NotNull(message = "Customer email is required")
        @Email(message = "Customer email is not valid")
        String email,
        Address address
) {
}
