package br.com.gustavobarbozamarques.mocks;

import br.com.gustavobarbozamarques.dto.ProductResponseDTO;

import java.math.BigDecimal;

public class ProductResponseDTOMock {
    public static ProductResponseDTO get() {
        return ProductResponseDTO.builder()
                .id("5099803df3f4948bd2f98391")
                .name("Product 1")
                .description("Description 1")
                .price(new BigDecimal("9.99"))
                .build();
    }
}
