package org.example.entity;

import org.jsoup.nodes.Element;

import java.util.List;
import java.util.stream.Collectors;

public class HNEntry {
    int postNumber;

    String title;

    int points;

    int numberOfComments;

    public HNEntry() {
        postNumber = 0;
        title = "";
        points = 0;
        numberOfComments = 0;
    }

    public HNEntry(int postNumber, String title, int points, int numberOfComments) {
        this.postNumber = postNumber;
        this.title = title;
        this.points = points;
        this.numberOfComments = numberOfComments;
    }

    public HNEntry(Element element) {
        String rankString = element.getElementsByClass("rank").text();
        String possiblePoints = element.getElementsByClass("score").text().split(" ")[0];
        int amntOfComments = 0;
        String[] details = element.getElementsByClass("subtext").text().split(" ");

        if (details.length > 1 && details[details.length - 1].equals("comments")) {
            amntOfComments = Integer.valueOf(details[details.length - 2]);
        }

        this.postNumber = Integer.parseInt(rankString.substring(0, rankString.length() - 1));
        this.title = element.getElementsByClass("titleline").text();
        this.points = possiblePoints.equals("") ? 0 : Integer.parseInt(possiblePoints);
        this.numberOfComments = amntOfComments;
    }

    public void printEntry(){
        System.out.println(postNumber + " -- " + title + " -- " + points + " -- " + numberOfComments);
    }

    public int wordsOnTitle() {
        List<String> separatedTitle = List.of(title.split("\\s"));
        separatedTitle = separatedTitle.stream().filter(s -> s.matches("[a-zA-Z]+")).collect(Collectors.toList());
        return separatedTitle.size();
    }
}