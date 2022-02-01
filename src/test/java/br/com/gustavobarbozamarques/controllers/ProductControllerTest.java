package br.com.gustavobarbozamarques.controllers;

import br.com.gustavobarbozamarques.mocks.ProductRequestDTOMock;
import br.com.gustavobarbozamarques.mocks.ProductResponseDTOMock;
import br.com.gustavobarbozamarques.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllShouldReturnSuccessIfFound() throws Exception {
        var productList = List.of(ProductResponseDTOMock.get());
        when(productService.getAll()).thenReturn(productList);
        this.mockMvc
                .perform(get("/v1/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(productList.get(0).getId()));
    }

    @Test
    void testGetByIdShouldReturnSuccessIfFound() throws Exception {
        var product = ProductResponseDTOMock.get();
        when(productService.getById(product.getId())).thenReturn(product);
        this.mockMvc
                .perform(get(String.format("/v1/products/%s", product.getId())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()));
    }

    @Test
    void testGetByIdShouldReturnNotFoundIfNotExists() throws Exception {
        var product = ProductResponseDTOMock.get();
        when(productService.getById(product.getId()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found."));
        this.mockMvc
                .perform(get(String.format("/v1/products/%s", product.getId())))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetByIdShouldReturnBadRequestIfBlankId() throws Exception {
        var product = ProductResponseDTOMock.get();
        when(productService.getById(product.getId()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found."));
        this.mockMvc
                .perform(get(String.format("/v1/products/%s", "     ")))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSaveShouldReturnSuccessIfValid() throws Exception {
        var productDTO = ProductRequestDTOMock.get();
        this.mockMvc
                .perform(
                        post("/v1/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(productDTO))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testSaveShouldReturnBadRequestIfMissingRequiredFields() throws Exception {
        var productDTO = ProductRequestDTOMock.get();
        productDTO.setName(null);
        productDTO.setDescription(null);
        productDTO.setPrice(null);
        this.mockMvc
                .perform(
                        post("/v1/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(productDTO))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateShouldReturnSuccessIfValid() throws Exception {
        var productDTO = ProductRequestDTOMock.get();
        this.mockMvc
                .perform(
                        put("/v1/products/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(productDTO))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateShouldReturnBadRequestIfMissingRequiredFields() throws Exception {
        var productDTO = ProductRequestDTOMock.get();
        productDTO.setName(null);
        productDTO.setDescription(null);
        productDTO.setPrice(null);
        this.mockMvc
                .perform(
                        put("/v1/products/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(productDTO))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteShouldReturnSuccessIfValid() throws Exception {
        var productDTO = ProductRequestDTOMock.get();
        this.mockMvc
                .perform(
                        delete("/v1/products/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(productDTO))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteShouldReturnNotFoundIfNotExists() throws Exception {
        var product = ProductResponseDTOMock.get();
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found."))
                .when(productService)
                .delete(product.getId());
        this.mockMvc
                .perform(delete(String.format("/v1/products/%s", product.getId())))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}