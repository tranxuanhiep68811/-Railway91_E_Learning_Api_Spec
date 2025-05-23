package com.th.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
    @GetMapping("/categories")
    public String getCategories() {
        return "HELLO!";
    }
}
