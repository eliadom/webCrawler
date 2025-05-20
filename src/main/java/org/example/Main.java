package org.example;

import org.example.entity.HNEntry;
import org.example.service.WebCrawlerService;

import java.io.IOException;
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
            try {
                switch (inputValue) {
                    case "f":
                        allEntries = webCrawlerService.getLast30();
                        webCrawlerService.printEntries(allEntries);
                        webCrawlerService.saveAccess();
                        break;
                    case "m":
                        webCrawlerService.printEntries(webCrawlerService.moreThanFiveByComments(allEntries));
                        webCrawlerService.saveAccess(true);
                        break;
                    case "l":
                        webCrawlerService.printEntries(webCrawlerService.lessOrEqualThanFiveByPoints(allEntries));
                        webCrawlerService.saveAccess(false);
                        break;
                    case "e":
                        break;
                }
            } catch (IOException e) {
                System.out.println("We could not register this action into the access log.");
            }
        } while (!inputValue.equalsIgnoreCase("e"));
    }
}