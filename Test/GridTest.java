import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GridTest {
    @Test
    @DisplayName("width matches wordsearch width")
    void Grid() {
        Grid g = new Grid("grid1.txt");
        int h = g.getHeight();
        Assertions.assertEquals(5, h);
    }

    @Test
    @DisplayName("height matches wordsearch height")
    void Grid2() {
        Grid g = new Grid("grid1.txt");
        int w = g.getWidth();
        Assertions.assertEquals(5, w);
    }
}