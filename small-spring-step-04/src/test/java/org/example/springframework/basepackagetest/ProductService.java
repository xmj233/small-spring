package org.example.springframework.basepackagetest;

import org.example.springframework.stereotype.Component;

@Component("product")
public class ProductService {

    private String token;

    public String queryProductInfo() {
        return "iphone" + token;
    }
}
