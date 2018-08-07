package com.example.product;

import com.example.product.Product;

import java.util.List;

public interface ProductService {

    Product getById (long id);
    Product getByArticle (String article);

    List<Product> getByNameContaining(String substring);

    List<Product> getByCategory(int category);
    List<Product> getByCategoryAndSubcategory(int category,
                                              int subcategory);

    List<Product> getByType (int type);

    void addProduct(Product product);
    void editProduct(Product product);

    void deleteProduct(Product product);
    void deleteProduct(long id);

    List<Product> getAll();
    List<Product> getByFilter();

}
