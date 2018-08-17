package com.example.services;

import com.example.models.Product;

import java.util.List;

public interface ProductService extends BaseService<Product> {

    List<Product> getByNameContaining(String substring);

    List<Product> getByCategory(int category);
    List<Product> getByCategoryAndSubcategory(int category, int subcategory);

    List<Product> getByType (int type);

    List<Product> getByFilter();

}
