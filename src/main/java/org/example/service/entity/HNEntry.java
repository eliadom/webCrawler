package org.example.entities;

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
}