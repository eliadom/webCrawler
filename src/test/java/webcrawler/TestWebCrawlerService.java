package webcrawler;

import org.example.entity.HNEntry;
import org.example.service.WebCrawlerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestWebCrawlerService {


    WebCrawlerService webCrawlerService;

    @Mock
    HNEntry entryOne;

    @Mock
    HNEntry entryTwo;

    HNEntry entryOrderOne;
    HNEntry entryOrderTwo;
    HNEntry entryOrderThree;

    @BeforeEach
    public void setUp() {
        webCrawlerService = new WebCrawlerService();
        MockitoAnnotations.openMocks(this);
        entryOne = Mockito.mock(HNEntry.class);
        entryTwo = Mockito.mock(HNEntry.class);

        entryOrderOne = new HNEntry(1,"This is such a long title it will come out of my screen", 35, 34);
        entryOrderTwo = new HNEntry(2,"Short", 32, 0);
        entryOrderThree = new HNEntry(2,"This is not such a short title", 3, 140);
    }

    @Test
    public void testWebCrawlerServiceSaveAccessFetchAllOk() {
        // Throw can only be caused to internal error. Not client cases. Just controlling the error
        assertDoesNotThrow(() -> webCrawlerService.saveAccess());
    }

    @Test
    public void testWebCrawlerServiceSaveAccessFilterTrue() {
        assertDoesNotThrow(() -> webCrawlerService.saveAccess(true));
    }

    @Test
    public void testWebCrawlerServicePrintEntries() {
        List<HNEntry> entries = Arrays.asList(entryOne, entryTwo);
        webCrawlerService.printEntries(entries);
        verify(entryOne, only()).printEntry();
        verify(entryTwo, only()).printEntry();
    }

    @Test
    public void testWebGetLast30() {
        List<HNEntry> last30 = webCrawlerService.getLast30();
        assertEquals(30, last30.size());
    }

    @Test
    public void testMoreThanFiveByComments() {
        List<HNEntry> entries = Arrays.asList(entryOrderOne, entryOrderTwo, entryOrderThree);
        entries = webCrawlerService.moreThanFiveByComments(entries);
        assertEquals(2, entries.size());
        assertEquals(entryOrderThree, entries.get(0));
        assertEquals(entryOrderOne, entries.get(1));
    }

}
