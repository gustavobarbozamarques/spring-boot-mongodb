package br.com.gustavobarbozamarques.services;

import br.com.gustavobarbozamarques.entities.Product;
import br.com.gustavobarbozamarques.mocks.ProductMock;
import br.com.gustavobarbozamarques.mocks.ProductRequestDTOMock;
import br.com.gustavobarbozamarques.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testGetAll() {
        var productsFromDatabase = List.of(ProductMock.get());
        when(productRepository.findAll()).thenReturn(productsFromDatabase);
        var products = productService.getAll();
        assertThat(products)
                .isNotEmpty()
                .hasSize(productsFromDatabase.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetByIdShouldReturnProductIfFound() {
        var product = ProductMock.get();
        when(productRepository.findById(anyString())).thenReturn(Optional.of(product));
        var productDTO = productService.getById("123");
        assertThat(productDTO)
                .isNotNull();
        verify(productRepository, times(1)).findById(anyString());
    }

    @Test
    void testGetByIdShouldThrowExceptionIfNotFound() {
        when(productRepository.findById(anyString())).thenReturn(Optional.ofNullable(null));
        assertThrows(ResponseStatusException.class, () -> {
            productService.getById("123");
        });
    }

    @Test
    void testSaveShouldSaveSuccessfully() {
        productService.save(null, ProductRequestDTOMock.get());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testDeleteShouldDeleteSuccessfullyIfProductExists() {
        var product = ProductMock.get();
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        productService.delete(product.getId());
        verify(productRepository, times(1)).findById(product.getId());
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void testDeleteShouldThrowExceptionIfProductNotFound() {
        when(productRepository.findById(anyString())).thenReturn(Optional.ofNullable(null));
        assertThrows(ResponseStatusException.class, () -> {
            productService.delete("123");
        });
        verify(productRepository, times(1)).findById(anyString());
        verify(productRepository, never()).delete(any(Product.class));
    }
}
