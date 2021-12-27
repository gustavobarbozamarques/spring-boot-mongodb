package br.com.gustavobarbozamarques.services;

import br.com.gustavobarbozamarques.dto.ProductRequestDTO;
import br.com.gustavobarbozamarques.dto.ProductResponseDTO;
import br.com.gustavobarbozamarques.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponseDTO> getAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponseDTO::from)
                .collect(Collectors.toList());
    }

    public ProductResponseDTO getById(String productId) {
        var product = productRepository
                .findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found."));
        return ProductResponseDTO.from(product);
    }


    public void save(String productId, ProductRequestDTO productRequestDTO) {
        var product = ProductRequestDTO.from(productRequestDTO);
        product.setId(productId);
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);
    }

    public void delete(String productId) {
        var product = productRepository
                .findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found."));
        productRepository.delete(product);
    }
}