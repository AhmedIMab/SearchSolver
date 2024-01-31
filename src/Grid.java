import java.io.*;

public class Grid {
    private int width;
    private int height;
    public Letter[][] mainGrid;

    public Grid(String filename) throws IOException {
        BufferedReader file_reader = new BufferedReader(new FileReader(filename));
        String line = file_reader.readLine();
        int w = 0;
        int h = 0;

        if (line == null) {
            throw new FileNotFoundException("The file could not be found or is empty - please check you have created a file in the right location");
        }
        else {
            // Main design assumption is the wordsearch is square (same height as width)
            // Sets the width to the length of the first line
            w = line.length();
        }

        int not_part_of_grid = 0;

        while (line != null) {
            System.out.println("This is line:" + line);
            line = file_reader.readLine();
            if (line != null) {
                if (!line.equals("") && !line.equals(" ")) {
                    // Not an empty line
                    h += 1;
                }
                else {
                    not_part_of_grid += 1;
                }
            }
        }

        // The wordsearch will not work without being provided words
        // represents the final line with words
        not_part_of_grid += 1;

        System.out.println("height:" + h);

        this.width = w;
        this.height = h - not_part_of_grid;
        mainGrid = new Letter[this.width][this.height];
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
