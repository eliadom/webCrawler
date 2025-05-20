package org.example;

import org.example.service.WebCrawlerService;

public class Main {
    public static void main(String[] args) {
        WebCrawlerService webCrawlerService = new WebCrawlerService();

        webCrawlerService.getLast30();
        System.out.println("Hello world!");
    }
}