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
        Grid g = new Grid("Grids\\examplegrid1-5x5.txt");
        int h = g.getHeight();
        Assertions.assertEquals(5, h);
    }

    @Test
    @DisplayName("width matches wordsearch width")
    void Grid2() throws IOException {
        Grid g = new Grid("Grids\\examplegrid1-5x5.txt");
        int w = g.getWidth();
        Assertions.assertEquals(5, w);
    }

    @Test
    @DisplayName("check height and width are the same")
    void Grid3() throws IOException {
        Grid g = new Grid("Grids\\examplegrid1-5x5.txt");
        int h = g.getHeight();
        int w = g.getWidth();
        Assertions.assertEquals(h, w);
    }

    @Test
    @DisplayName("Check the grid created is the same as the expected grid")
    void populateGrid() throws IOException {
        Grid g = new Grid("Grids\\examplegrid1-5x5.txt");
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
    @DisplayName("Checks the grid created is the same as the expected grid, tests another grid")
    void populateGrid2() throws IOException {
        Grid g = new Grid("Grids\\examplegrid3-6x6.txt");
        // Compares the grids rows as string to the expected row as a string
        String[] expectedRows = {"BARACK", "YALCSB", "OENOPE", "AOPGRN", "CEYOED", "NASDCA"};

        String[] actualRows = new String[6];
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

    @ParameterizedTest(name="Test {index}: x={0}, y={1}, expected_letter={2}")
    @DisplayName("Checks it can get a specific letter at a position in the grid")
    @CsvSource({
            "0,0, H",
            "2,0, R",
            "2,2, B",
            "4,4, T",
            "1,3, O"
    })
    void getLetterAtCoord(int xcoord, int ycoord, char expected) throws IOException {
        Grid g = new Grid("Grids\\examplegrid1-5x5.txt");
        char letter = g.getLetterAtCoord(xcoord, ycoord).letter;
        Assertions.assertEquals(expected, letter);
    }

    @ParameterizedTest(name="Test {index}: x={0}, y={1}, expected_letter={2}")
    @DisplayName("Checks it can get a specific letter at a position in the grid, test another grid")
    @CsvSource({
            "0,0, B",
            "4,2, P",
            "1,1, A",
            "3,3, G",
            "5,5, A"
    })
    void getLetterAtCoord2(int xcoord, int ycoord, char expected) throws IOException {
        Grid g = new Grid("Grids\\examplegrid3-6x6.txt");
        char letter = g.getLetterAtCoord(xcoord, ycoord).letter;
        Assertions.assertEquals(expected, letter);
    }

    @Test
    @DisplayName("Check an exception is thrown if the xcoord or ycoord is outside the size of the grid")
    void getLetterAtCoord2() throws IOException {
        Grid g = new Grid("Grids\\examplegrid1-5x5.txt");
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            g.getLetterAtCoord(7,7);
        });
    }

    @Test
    @DisplayName("Check an exception is thrown if the xcoord or ycoord is negative")
    void getLetterAtCoord3() throws IOException {
        Grid g = new Grid("Grids\\examplegrid1-5x5.txt");
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            g.getLetterAtCoord(-5,-2);
        });
    }
}