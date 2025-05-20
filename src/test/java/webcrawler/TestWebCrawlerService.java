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

    @BeforeEach
    public void setUp() {
        webCrawlerService = new WebCrawlerService();
        MockitoAnnotations.openMocks(this);
        entryOne = Mockito.mock(HNEntry.class);
        entryTwo = Mockito.mock(HNEntry.class);

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

}
