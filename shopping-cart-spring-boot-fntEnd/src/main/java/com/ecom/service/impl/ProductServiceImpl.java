package com.ecom.service.impl;

import com.ecom.model.Product;
import com.ecom.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public Product saveProduct(Product product) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Boolean deleteProduct(Integer id) {
        return null;
    }

    @Override
    public Product getProductById(Integer id) {
        return null;
    }

    @Override
    public Product updateProduct(Product product, MultipartFile file) {
        return null;
    }

    @Override
    public List<Product> getAllActiveProducts(String category) {
        return List.of();
    }
}
