package org.example.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WebCrawlerService {

    public Elements getLast30() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://news.ycombinator.com/news";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        String htmlResponse = response.getBody();
        Document doc = Jsoup.parse(htmlResponse);

        Element titles = doc.getElementById("hnmain");
        Elements titleElements = titles.getElementsByAttributeValue("border", "0");
        Element allEntries = titleElements.get(2);
        Elements allEntriesDivided = allEntries.select("table > tbody > tr");
        allEntriesDivided = allEntriesDivided.stream().filter(entry -> entry.attributes().size() == 0 || entry.className().equals("athing submission")).collect(Collectors.toCollection(Elements::new));
        // Divided into number,title; points and number of comments.
        return allEntriesDivided;
    }
}
