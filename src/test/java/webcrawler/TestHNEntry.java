package webcrawler;

import org.example.entity.HNEntry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestHNEntry {
    HNEntry hnEntry;

    @BeforeEach
    public void setUp() {
        hnEntry = new HNEntry(123, "This is - My Post", 54, 893);
    }

    @Test
    public void testWordsOnTitle() {
        assertTrue(hnEntry.wordsOnTitle() == 4);
    }

    @Test
    public void testGetNumberOfComments() {
        assertEquals(hnEntry.getNumberOfComments(),893);
    }

    @Test
    public void testGetPoints() {
        assertEquals(hnEntry.getPoints(),123);
    }
}
