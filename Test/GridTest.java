import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

class GridTest {
    @Test
    @DisplayName("height matches wordsearch height")
    void Grid() throws IOException {
        Grid g = new Grid("src\\grid1.txt");
        int h = g.getHeight();
        Assertions.assertEquals(5, h);
    }

    @Test
    @DisplayName("width matches wordsearch width")
    void Grid2() throws IOException {
        Grid g = new Grid("src\\grid1.txt");
        int w = g.getWidth();
        Assertions.assertEquals(5, w);
    }

    @Test
    @DisplayName("check height and width are the same")
    void Grid3() throws IOException {
        Grid g = new Grid("src\\grid1.txt");
        int h = g.getHeight();
        int w = g.getWidth();
        Assertions.assertEquals(h, w);
    }

    @Test
    @DisplayName("Check the grid created is the same as the expected grid")
    void populateGrid() throws IOException {
        Grid g = new Grid("src\\grid1.txt");
        // Compares the grids rows as string to the expected row as a string
        String[] expectedRows = {"HORSE", "ESUOM", "TDBRO", "AONAE", "CGYOT"};

        String[] actualRows = new String[5];
        // Convert the actual grid to rows of strings stored in a string array
        for (int i=0; i<g.mainGrid.length; i++) {
            StringBuilder rowX = new StringBuilder();
            for (int j=0;j<actualRows.length; j++) {
                rowX.append(g.mainGrid[j][i].letter);
            }
            actualRows[i] = String.valueOf(rowX);
        }

        for (int i=0; i<expectedRows.length; i++) {
            // Check every row
            Assertions.assertEquals(expectedRows[i], actualRows[i]);
        }
    }

    @Test
    @DisplayName("Checks it can get a specific letter at a position in the grid")
    @CsvSource({
            "0,0, H",
            "2,0, R",
            "2,2, B",
            "4,4, T",
            "8,8, X"
    })
    void getLetterAtCoord(int xcoord, int ycoord, char expected) throws IOException {
        Grid g = new Grid("src\\grid1.txt");
        char letter = g.getLetterAtCoord(xcoord, ycoord).letter;
        Assertions.assertEquals(expected, letter);
    }
}