package com.ecom.service.impl;

import com.ecom.entity.Category;
import com.ecom.repository.CategoryRepository;
import com.ecom.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public int saveCategory(Category category) {
        return categoryRepository.save(category.getId(),category.getName(),category.getImageName(),category.getIsActive());
    }
    @Override
    public int updateCategory(Category category) {
        return categoryRepository.updateCategory(category.getId(),category.getName(),category.getImageName(),category.getIsActive());
    }

    @Override
    public Boolean existCategory(String name) {
       return categoryRepository.countByCategoryName(name)>0?true:false;
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAllCategory();
    }

    @Override
    public Boolean deleteCategory(int id) {
     Category category= categoryRepository.findById(id).orElse(null);
     if(ObjectUtils.isEmpty(category)){
         return false;
     }else{
         categoryRepository.delete(category);
         return true;
     }
    }

    @Override
    public Category getCategoryById(int id) {
        Category category=categoryRepository.findById(id).orElse(null);
        return category;
    }

    @Override
    public List<Category> getAllActiveCategory() {
        return categoryRepository.findByIsActiveTrue();
    }
}
