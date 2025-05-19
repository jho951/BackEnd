package src.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import src.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}