package webcrawler;

import org.example.service.WebCrawlerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestWebCrawlerService {

    @Mock
    WebCrawlerService webCrawlerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        webCrawlerService = Mockito.mock(WebCrawlerService.class);
    }

    @Test
    public void testWebCrawlerServiceSaveAccessFetchAllOk() {
        // Throw can only be caused to internal error. Not client cases. Just controlling the error
        assertDoesNotThrow(() -> webCrawlerService.saveAccess());
    }
}
