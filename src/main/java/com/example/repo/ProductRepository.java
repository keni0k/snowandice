package com.example.repo;

import com.example.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product getProductById(long id);

    List<Product> getProductsByNameContaining(String substring);

    Product getProductByArticle(String article);

    List<Product> getProductsByCategory(int category);

    List<Product> getProductsByCategoryAndSubcategory(int category, int subcategory);

    List<Product> getProductsByCategoryAndSubcategoryAndPriceBetweenAndType(int category, int subcategory, int priceDown, int priceUp, int type);

    List<Product> getProductsByType(int type);
}
