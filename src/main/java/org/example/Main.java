package org.example;

import org.example.entity.HNEntry;
import org.example.service.WebCrawlerService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        WebCrawlerService webCrawlerService = new WebCrawlerService();
        List<HNEntry> allEntries = webCrawlerService.getLast30();
        webCrawlerService.printEntries(allEntries);
        Scanner input = new Scanner(System.in);
        String inputValue = "";
        do {
            webCrawlerService.showCrawlerInstructions();
            inputValue = input.nextLine();
            switch (inputValue) {
                case "f":
                    allEntries = webCrawlerService.getLast30();
                    webCrawlerService.printEntries(allEntries);
                    break;
                case "m":
                    webCrawlerService.printEntries(webCrawlerService.moreThanFiveByComments(allEntries));
                    break;
                case "l":
                    webCrawlerService.printEntries(webCrawlerService.lessOrEqualThanFiveByPoints(allEntries));
                    break;
                case "e":
                    break;
            }
        } while (!inputValue.equals("e"));
    }
}