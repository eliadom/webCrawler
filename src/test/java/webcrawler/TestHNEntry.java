package webcrawler;

import org.example.entity.HNEntry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class TestHNEntry {

    HNEntry hnEntry;

    @Mock
    HNEntry mockHNEntry;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        hnEntry = new HNEntry(123, "This is - My Post", 54, 893);
        mockHNEntry = Mockito.mock(HNEntry.class);

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
        assertEquals(hnEntry.getPoints(),54);
    }

}
