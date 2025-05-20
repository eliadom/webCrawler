package org.example;

import org.example.entity.HNEntry;
import org.example.service.WebCrawlerService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        WebCrawlerService webCrawlerService = new WebCrawlerService();
        webCrawlerService.showCrawlerInstructions();
        List<HNEntry> allEntries = webCrawlerService.getLast30();
        webCrawlerService.printEntries(allEntries);
    }
}