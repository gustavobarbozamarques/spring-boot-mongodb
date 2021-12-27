package br.com.gustavobarbozamarques.repositories;

import br.com.gustavobarbozamarques.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
