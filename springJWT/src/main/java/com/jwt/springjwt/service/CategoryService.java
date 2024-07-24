package com.jwt.springjwt.service;

import com.jwt.springjwt.domain.CategoryDto;
import com.jwt.springjwt.exception.EtBadRequestException;
import com.jwt.springjwt.exception.EtResourceNotFoundException;

import java.awt.print.PrinterGraphics;
import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories(Integer userId);

    CategoryDto fetchCategoryById(Integer userId, Integer categoryId) throws EtResourceNotFoundException;

    CategoryDto addCategory(Integer userId, String title, String description)throws EtBadRequestException;

    void updateCategory(Integer userId, Integer categoryId, CategoryDto categoryDto)throws EtBadRequestException;

    void removeCategoryWithAllTransactions(Integer userId, Integer categoryId)throws EtBadRequestException;

}
