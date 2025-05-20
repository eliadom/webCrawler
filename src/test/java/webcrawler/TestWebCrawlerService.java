package webcrawler;

import org.example.service.WebCrawlerService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class TestWebCrawlerService {

    @Mock
    WebCrawlerService webCrawlerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        webCrawlerService = Mockito.mock(WebCrawlerService.class);
    }
}
