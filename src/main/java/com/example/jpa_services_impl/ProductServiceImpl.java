package com.example.jpa_services_impl;

import com.example.models.Product;
import com.example.repo.ProductRepository;
import com.example.services.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Product findById(Long id) {
        return productRepository.getProductById(id);
    }

    @Override
    public List<Product> getByNameContaining(String substring) {
        return productRepository.getProductsByNameContaining(substring);
    }

    @Override
    public List<Product> getByCategory(int category) {
        return productRepository.getProductsByCategory(category);
    }

    @Override
    public List<Product> getByCategoryAndSubcategory(int category, int subcategory) {
        return productRepository.getProductsByCategoryAndSubcategory(category, subcategory);
    }

    @Override
    public List<Product> getByType(int type) {
        return productRepository.getProductsByType(type);
    }

    @Override
    public void add(Product product) {
        productRepository.saveAndFlush(product);
    }

    public void update(Product product) {
        productRepository.saveAndFlush(product);
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getByFilter() {
        return productRepository.findAll();
    }
}
