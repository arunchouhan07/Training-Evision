package com.ecom.service.impl;

import com.ecom.model.Product;
import com.ecom.repository.ProductRepository;
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
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

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
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product updateProduct(Product product, MultipartFile multipartFile) {
        Product dbProduct = getProductById(product.getId());
        String fileName=multipartFile.isEmpty() ? dbProduct.getImage() : multipartFile.getOriginalFilename();

        dbProduct.setTitle(product.getTitle());
        dbProduct.setDescription(product.getDescription());
        dbProduct.setCategory(product.getCategory());
        dbProduct.setPrice(product.getPrice());
        dbProduct.setStock(product.getStock());
        dbProduct.setImage(fileName);
        dbProduct.setIsActive(product.getIsActive());
        dbProduct.setDiscount(product.getDiscount());

        // 5=100*(5/100); 100-5=95
        Double disocunt = product.getPrice() * (product.getDiscount() / 100.0);
        Double discountPrice = product.getPrice() - disocunt;
        dbProduct.setDiscountPrice(discountPrice);

        Product updateProduct = productRepository.save(dbProduct);

        if (!ObjectUtils.isEmpty(updateProduct)) {

            if (!multipartFile.isEmpty()) {

                try {
                    File saveFile = new ClassPathResource("static/img").getFile();

                    Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "product_img" + File.separator
                            + fileName);
                    Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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
