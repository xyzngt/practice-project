package com.gray.business.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;


class BusinessControllerTest {

    @Test
    void getOrderVersion() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/business/getOrderVersion";
        String forObject = restTemplate.getForObject(url, String.class);
        System.out.println("forObject = " + forObject);
        Assertions.assertEquals(forObject,"1234");
    }
    @Test
    void getOrderGrayVersion() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("version","v2");
        String url = "http://localhost:8080/business/getOrderVersion";
        String forObject = restTemplate.getForObject(url, String.class,httpHeaders);
        System.out.println("forObject = " + forObject);
        Assertions.assertEquals(forObject,"v2");
    }
}