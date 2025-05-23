package com.th.controller;

import com.th.entity.Account;
import com.th.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class ProductController{

    @GetMapping("/products")
    public String getProduct() {
        return "HELLO!";
    }

    @GetMapping("/products/{id}")
    public String getProductById(@PathVariable Integer id) {
        return "Product with ID: " + id;
    }
}
