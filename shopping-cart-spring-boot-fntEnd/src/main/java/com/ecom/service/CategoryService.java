package com.ecom.service;

import com.ecom.model.Category;

import java.util.List;

public interface CategoryService {
    public int saveCategory(Category category);

    public Boolean existCategory(String name);

    public int updateCategory(Category category);

    public List<Category> getAllCategory();

    public Boolean deleteCategory(int id);

    public Category getCategoryById(int id);

    public List<Category> getAllActiveCategory();

}
