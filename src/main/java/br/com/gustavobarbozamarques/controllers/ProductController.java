package br.com.gustavobarbozamarques.controllers;

import br.com.gustavobarbozamarques.dto.ProductRequestDTO;
import br.com.gustavobarbozamarques.dto.ProductResponseDTO;
import br.com.gustavobarbozamarques.services.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(tags = "Product Catalog")
@RestController
@Validated
@RequestMapping(path = "/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get all products.")
    public List<ProductResponseDTO> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get product by id.")
    public ProductResponseDTO getById(
            @PathVariable("productId") @NotBlank String productId
    ) {
        return productService.getById(productId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Save new product.")
    public void save(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        productService.save(null, productRequestDTO);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Update product by id.")
    public void update(
            @PathVariable("productId") @NotBlank String productId,
            @Valid @RequestBody ProductRequestDTO productRequestDTO
    ) {
        productService.save(productId, productRequestDTO);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Delete product by id.")
    public void delete(
            @PathVariable("productId") @NotBlank String productId
    ) {
        productService.delete(productId);
    }
}