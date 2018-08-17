package com.example.product;

import com.example.repo.RepoService;

import java.util.List;

public interface ProductService extends RepoService<Product> {

    List<Product> getByNameContaining(String substring);

    List<Product> getByCategory(int category);
    List<Product> getByCategoryAndSubcategory(int category, int subcategory);

    List<Product> getByType (int type);

    List<Product> getByFilter();

}
