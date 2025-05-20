package webcrawler;

import org.example.entity.HNEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestHNEntry {
    HNEntry hnEntry;

    @BeforeEach
    public void setUp() {
        hnEntry = new HNEntry(123, "This is - My Post", 54, 893);
    }
}
