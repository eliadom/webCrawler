package org.example.service;

import lombok.Generated;
import org.example.entity.HNEntry;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class WebCrawlerService {

    static final String FETCH_LINK = "https://news.ycombinator.com/news";

    // Pre:
    // Post:
    @Generated
    public void showCrawlerInstructions() {
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("------------------------------------------- INSTRUCTIONS -------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("* f : fetch 30 newest entries");
        System.out.println("* m : Show entries with more than five words in the title, ordered by the number of comments first");
        System.out.println("* l : Show entries with less than or equal to five words in the title, ordered by points");
        System.out.println("* e : Exit");
    }

    // Pre:
    // Post:
    // Action: Saves log in case it can. Throws IOException otherwise
    public void saveAccess() throws IOException {
        saveLog("FETCH");
    }

    // Pre:
    // Post:
    // Action: Saves log in case it can. Throws IOException otherwise
    public void saveAccess(boolean filterMore) throws IOException {
        saveLog(filterMore ? "FILTER MORE THAN 5" : "FILTER LESSEQ 5");
    }

    // Pre:
    // Post:
    // Action: Saves log in case it can. Throws IOException otherwise
    private void saveLog(String action) throws IOException {
        Writer writer = new BufferedWriter(new FileWriter("C:/Github/webCrawler/accessLog.txt", true));
        writer.append(new Date() + " --- " + action  +"\n");
        writer.close();
    }

    // Pre:
    // Post:
    public void printEntries(List<HNEntry> hnEntries) {
        for (HNEntry hnEntry : hnEntries) {
            hnEntry.printEntry();
        }
    }

    // Pre:
    // Post: Returns most recent entries on the website
    public List<HNEntry> getLast30() {
        Document doc = getResponseHTML();
        Elements cleanElements = cleanedElements(doc);
        cleanElements = unifyElements(cleanElements);

        return hnEntriesFromElements(cleanElements);
    }

    // Pre: None. We are assuming website will not be down.
    // Post: Jsoup Document ready to be manipulated
    private Document getResponseHTML() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(FETCH_LINK, HttpMethod.GET, null, String.class);
        String htmlResponse = response.getBody();
        return Jsoup.parse(htmlResponse);
    }

    // Pre: Document passed through arguments is not null
    // Post: Jsoup minimal Elements adapted to our news format.
    private Elements cleanedElements(Document doc) {
        Element titles = doc.getElementById("hnmain");
        Elements titleElements = titles.getElementsByAttributeValue("border", "0");
        Element allEntries = titleElements.get(2);
        Elements allEntriesDivided = allEntries.select("table > tbody > tr");
        return allEntriesDivided.stream().filter(entry -> entry.attributes().size() == 0 || entry.className().equals("athing submission")).collect(Collectors.toCollection(Elements::new));
    }

    // Pre: Elements is not null and have our desired format (they must have been cleaned as news combinator HTML format)
    // Post: Elements, now unifying basic data and subtext data
    private Elements unifyElements(Elements cleanElements) {
        Elements elementsUnified = new Elements();
        for (int i = 0; i < cleanElements.size() - 1; i = i + 2) {
            elementsUnified.add(cleanElements.get(i).append(cleanElements.get(i + 1).html()));
        }
        return elementsUnified;
    }

    // Pre: Elements have been cleaned and one element contains an entire entry, having basic data and subtext data.
    // Post: The 30 first elements themselves, formatted as custom HNEntry
    private List<HNEntry> hnEntriesFromElements(Elements cleanElements) {
        List<HNEntry> hnEntries = new ArrayList<>();
        List<HNEntry> finalHnEntries = hnEntries;
        cleanElements.forEach(entry -> finalHnEntries.add(new HNEntry(entry)));
        return hnEntries.subList(0, 30);
    }

    // Pre:
    // Post: Entries filtered only showing those that contain more than five words in its title, sorted by amount of comments (DESC)
    public List<HNEntry> moreThanFiveByComments(List<HNEntry> entries) {
        entries = entries.stream().filter(entry -> entry.wordsOnTitle() > 5).collect(Collectors.toList());
        entries.sort((a, b) -> b.getNumberOfComments() - a.getNumberOfComments());
        return entries;
    }

    // Pre:
    // Post: Entries filtered only showing those that contain five or less words in its title, sorted by amount of points (DESC)
    public List<HNEntry> lessOrEqualThanFiveByPoints(List<HNEntry> entries) {
        entries = entries.stream().filter(entry -> entry.wordsOnTitle() <= 5).collect(Collectors.toList());
        entries.sort((a, b) -> b.getPoints() - a.getPoints());
        return entries;
    }
}
