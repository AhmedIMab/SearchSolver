import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class GridTest {
    @Test
    @DisplayName("width matches wordsearch width")
    void Grid() throws IOException {
        Grid g = new Grid("grid1.txt");
        int h = g.getHeight();
        Assertions.assertEquals(5, h);
    }

    @Test
    @DisplayName("height matches wordsearch height")
    void Grid2() throws IOException {
        Grid g = new Grid("grid1.txt");
        int w = g.getWidth();
        Assertions.assertEquals(5, w);
    }

    @Test
    @DisplayName("check height and width are the same")
    void Grid3() throws IOException {
        Grid g = new Grid("grid1.txt");
        int h = g.getHeight();
        int w = g.getWidth();
        Assertions.assertEquals(h, w);
    }
}