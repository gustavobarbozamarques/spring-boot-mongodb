package br.com.gustavobarbozamarques.dto;

import br.com.gustavobarbozamarques.entities.Product;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class ProductRequestDTO {
    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @NotBlank(message = "Description cannot be blank.")
    private String description;

    @NotNull(message = "Price cannot be null.")
    @DecimalMin(value = "0.00", message = "Invalid price value.")
    private BigDecimal price;

    public static Product from(ProductRequestDTO request) {
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();
    }
}