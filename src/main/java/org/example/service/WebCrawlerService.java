package org.example.service;

import org.example.entity.HNEntry;
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

    final String FETCH_LINK = "https://news.ycombinator.com/news";

    public void showCrawlerInstructions() {
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("------------------------------------------- INSTRUCTIONS -------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("* f : fetch 30 newest entries");
        System.out.println("* m : Show entries with more than five words in the title, ordered by the number of comments first");
        System.out.println("* l : Show entries with less than or equal to five words in the title, ordered by points");
        System.out.println("");
    }

    public List<HNEntry> getLast30() {
        Document doc = getResponseHTML();
        Elements cleanElements = cleanedElements(doc);
        cleanElements = unifyElements(cleanElements);

        return hnEntriesFromElements(cleanElements);
    }

    private Document getResponseHTML() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(FETCH_LINK, HttpMethod.GET, null, String.class);
        String htmlResponse = response.getBody();
        return Jsoup.parse(htmlResponse);
    }

    private Elements cleanedElements(Document doc) {
        Element titles = doc.getElementById("hnmain");
        Elements titleElements = titles.getElementsByAttributeValue("border", "0");
        Element allEntries = titleElements.get(2);
        Elements allEntriesDivided = allEntries.select("table > tbody > tr");
        return allEntriesDivided.stream().filter(entry -> entry.attributes().size() == 0 || entry.className().equals("athing submission")).collect(Collectors.toCollection(Elements::new));
    }

    private Elements unifyElements(Elements cleanElements) {
        Elements elementsUnified = new Elements();
        for (int i = 0; i < cleanElements.size() - 1; i = i + 2) {
            elementsUnified.add(cleanElements.get(i).append(cleanElements.get(i + 1).html()));
        }
        return elementsUnified;
    }

    private List<HNEntry> hnEntriesFromElements (Elements cleanElements) {
        List<HNEntry> hnEntries = new ArrayList<>();
        List<HNEntry> finalHnEntries = hnEntries;
        cleanElements.forEach(entry -> finalHnEntries.add(new HNEntry(entry)));
        return hnEntries.subList(0, 29);
    }
}
