package com.example.productjpa.controller;

import com.example.productjpa.entity.Product;
import com.example.productjpa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/products")
    public String list(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product/list";
    }

    @GetMapping("/products/new")
    public String newForm() {
        return "product/form";
    }

    @PostMapping("/products")
    public String createForm(@ModelAttribute Product product,
                             @RequestParam("imageFile") MultipartFile imageFile) {
        productService.addProduct(product, imageFile);
        return "redirect:/products";
    }

    @PostMapping("/findProduct")
    public String findProduct(@RequestParam("name") String name, Model model) {
        List<Product> list = productService.findProduct(name);
        model.addAttribute("products", list);
        return "product/list";
    }

@GetMapping("/products/{id}")
    public String editForm(@PathVariable("id") long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "product/detail";
    }
}
