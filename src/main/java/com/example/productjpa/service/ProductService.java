package com.example.productjpa.service;

import com.example.productjpa.entity.Product;
import com.example.productjpa.repository.ProductRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
   private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void addProduct(Product product, MultipartFile imageFile) {
        if (!imageFile.isEmpty()) {
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

            File saveFile = new File(uploadDir + fileName);
            try {
                imageFile.transferTo(saveFile);


            } catch (IOException e) {
                throw new RuntimeException("Image upload failed", e);
            }
            product.setImageName(fileName);
        }
        productRepository.save(product);
    }

    public List<Product> findProduct(String name) {
       List<Product> list = productRepository.findAllByNameContaining(name);
       return list;
    }

    public Product findById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
    }
}
