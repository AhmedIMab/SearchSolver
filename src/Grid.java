import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Grid {
    private int width;
    private int height;
    public Letter[][] mainGrid;

    public Grid(String filename) throws IOException {
        BufferedReader file_reader = new BufferedReader(new FileReader(filename));
        String line = file_reader.readLine();
        int w = 0;
        int h = 0;

        // Main design assumption is the wordsearch is square (same height as width)
        // Sets the width to the length of the first line
        if (line != null) {
            w = line.length();
        }

        while (line != null) {
            line = file_reader.readLine();
            h += 1;
        }

        this.width = w;
        this.height = h;
        mainGrid = new Letter[w][h];
        populateGrid(filename);
    }

    private void populateGrid(String filename) throws IOException {
        BufferedReader file_reader = new BufferedReader(new FileReader(filename));
        String line = file_reader.readLine();
        int row = 0;

        while (line != null) {
            // System.out.println(line);
            for (int i=0; i<line.length(); i++) {
                Letter l = new Letter(line.charAt(i), i, row);
                mainGrid[i][row] = l;
            }
            row += 1;
            line = file_reader.readLine();
        }
    }


    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public Letter getLetterAtCoord(int xcoord, int ycoord) {
        if (xcoord > getWidth() | ycoord > getHeight() | xcoord < 0 | ycoord < 0) {
            throw new IndexOutOfBoundsException("Outside the range of the grid");
        }
        return mainGrid[xcoord][ycoord];
    }
}
