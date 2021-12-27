package br.com.gustavobarbozamarques.mocks;

import br.com.gustavobarbozamarques.entities.Product;

import java.math.BigDecimal;

public class ProductMock {
    public static Product get() {
        return Product.builder()
                .id("5099803df3f4948bd2f98391")
                .name("product1")
                .description("description 1")
                .price(new BigDecimal("1.50"))
                .build();
    }
}
