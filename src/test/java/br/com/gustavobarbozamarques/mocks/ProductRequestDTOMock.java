package br.com.gustavobarbozamarques.mocks;

import br.com.gustavobarbozamarques.dto.ProductRequestDTO;

import java.math.BigDecimal;

public class ProductRequestDTOMock {
    public static ProductRequestDTO get() {
        return ProductRequestDTO.builder()
                .name("Product 1")
                .description("Description 1")
                .price(BigDecimal.ONE)
                .build();
    }
}
