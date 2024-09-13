package com.ecom.service.impl;

import com.ecom.entity.Product;
import com.ecom.repository.ProductRepository;
import com.ecom.service.ImageService;
import com.ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImageService imageService;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Boolean deleteProduct(Integer id) {
        Product product = productRepository.findById(id).orElse(null);
        if(!ObjectUtils.isEmpty(product))
        {
            productRepository.delete(product);
            return true;
        }
        return false;
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException(MessageFormat.format("Product with id {0} not found", id)));
    }

    @Override
    public Product updateProduct(Product product, MultipartFile multipartFile) {
        Product dbProduct = getProductById(product.getId());
        String uploadImageUrl = imageService.upload(multipartFile);

        dbProduct.setTitle(product.getTitle());
        dbProduct.setDescription(product.getDescription());
        dbProduct.setCategory(product.getCategory());
        dbProduct.setPrice(product.getPrice());
        dbProduct.setStock(product.getStock());
        dbProduct.setImage(uploadImageUrl);
        dbProduct.setIsActive(product.getIsActive());
        dbProduct.setDiscount(product.getDiscount());

        // 5=100*(5/100); 100-5=95
        Double discount = product.getPrice() * (product.getDiscount() / 100.0);
        Double discountPrice = product.getPrice() - discount;
        dbProduct.setDiscountPrice(discountPrice);

        Product updateProduct = productRepository.save(dbProduct);

        if (!ObjectUtils.isEmpty(updateProduct)) {
            return product;
        }
        return null;
    }

    @Override
    public List<Product> getAllActiveProducts(String category) {
        List<Product> list;
        if(ObjectUtils.isEmpty(category)){
            list=productRepository.findByIsActiveTrue();
            return list;
        }
        return productRepository.findByIsActiveTrue(category);
    }
}
