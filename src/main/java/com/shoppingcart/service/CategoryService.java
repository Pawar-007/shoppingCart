package com.shoppingcart.service;

import java.util.List;

import com.shoppingcart.model.Category;

public interface CategoryService {

    Category saveCategory(Category category);

    Category updateCategory(Long id,
                            Category category);

    void deleteCategory(Long id);

    List<Category> getAllCategories();

    Category getCategory(Long id);

}