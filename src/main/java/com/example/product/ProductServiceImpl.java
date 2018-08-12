package com.example.product;

import com.example.repo.ProductRepository;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Product getById(long id) {
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
    public void addProduct(Product product) {
        productRepository.saveAndFlush(product);
    }

    @Override
    public void editProduct(Product product) {
        productRepository.saveAndFlush(product);
    }

    @Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getByFilter() {
        return productRepository.findAll();
    }
}
