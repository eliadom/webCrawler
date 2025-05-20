package webcrawler;

import org.checkerframework.common.value.qual.MinLen;
import org.example.entity.HNEntry;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class TestHNEntry {

    HNEntry hnEntry;

    @Mock
    HNEntry mockHNEntry;

    @Mock
    Element element;

    @Mock
    Elements elements;

    @Mock
    Element elementNoCommentsOrPoints;


    @Mock
    Elements rankElement;

    @Mock
    Elements titleElement;

    @Mock
    Elements scoreElement;

    @Mock
    Elements empty;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        hnEntry = new HNEntry(123, "This is - My Post", 54, 893);
        mockHNEntry = Mockito.mock(HNEntry.class);
        element = Mockito.mock(Element.class);
        empty = Mockito.mock(Elements.class);

        when(elements.text()).thenReturn("75 comments");
        when(rankElement.text()).thenReturn("23");
        when(titleElement.text()).thenReturn("My post");
        when(scoreElement.text()).thenReturn("85 points");
        when(empty.text()).thenReturn("");

        when(element.getElementsByClass("rank")).thenReturn(rankElement);
        when(element.getElementsByClass("titleline")).thenReturn(titleElement);
        when(element.getElementsByClass("score")).thenReturn(scoreElement);
        when(element.getElementsByClass("subtext")).thenReturn(elements);

        when(elementNoCommentsOrPoints.getElementsByClass("rank")).thenReturn(rankElement);
        when(elementNoCommentsOrPoints.getElementsByClass("titleline")).thenReturn(titleElement);
        when(elementNoCommentsOrPoints.getElementsByClass("score")).thenReturn(empty);
        when(elementNoCommentsOrPoints.getElementsByClass("subtext")).thenReturn(empty);
    }

    @Test
    public void testWordsOnTitle() {
        assertTrue(hnEntry.wordsOnTitle() == 4);
    }

    @Test
    public void testGetNumberOfComments() {
        assertEquals(hnEntry.getNumberOfComments(), 893);
    }

    @Test
    public void testGetPoints() {
        assertEquals(hnEntry.getPoints(), 54);
    }

    @Test
    public void testPrint() {
        mockHNEntry.printEntry();
        verify(mockHNEntry).printEntry();
    }

    @Test
    public void testHNEntryEmpty() {
        hnEntry = new HNEntry();
        assertTrue(hnEntry.getPoints() == 0 && hnEntry.getNumberOfComments() == 0 && hnEntry.wordsOnTitle() == 0);
    }

    @Test
    public void testHNEntry() {
        hnEntry = new HNEntry(1, "AAA", 1, 1);
        assertTrue(hnEntry.getPoints() == 1 && hnEntry.getNumberOfComments() == 1 && hnEntry.wordsOnTitle() == 1);
    }

    @Test
    public void testHNEntryFromElement() {
        hnEntry = new HNEntry(element);
        assertTrue(hnEntry.getPoints() == 85 && hnEntry.getNumberOfComments() == 75 && hnEntry.wordsOnTitle() == 2);
    }

    @Test
    public void testHNEntryFromElementNoCommentsOrPoints() {
        hnEntry = new HNEntry(elementNoCommentsOrPoints);
        assertTrue(hnEntry.getPoints() == 0 && hnEntry.getNumberOfComments() == 0 && hnEntry.wordsOnTitle() == 2);
    }
}
