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
            h += 1;
            w = line.length();
        }

        // The wordsearch will not work without being provided words
        // setting not_part_of_grid to 1 represents the final line with words
        int not_part_of_grid = 1;
        int total_lines = 0;

        while (line != null) {
            line = file_reader.readLine();
            total_lines += 1;
            if (line != null) {
                if (!SolverDriver.containsLetters(line)) {
                    // An empty line
                    not_part_of_grid += 1;
                }
            }
        }

        //System.out.println("total:" + total_lines);
        //System.out.println("not part: " + not_part_of_grid);

        this.width = w;
        this.height = total_lines - not_part_of_grid;
        mainGrid = new Letter[this.width][this.height];
        populateGrid(filename, total_lines);
    }

    private void populateGrid(String filename, int total_lines) throws IOException {
        BufferedReader file_reader = new BufferedReader(new FileReader(filename));
        String line = file_reader.readLine();
        int row = 1;
        boolean last_line = false;
        System.out.println("Height: " + this.height);
        System.out.println("Width: " + this.width);

        while (line != null) {
            System.out.println("This is line:" + line);
            if (row == total_lines || row == getHeight()+1) {
                System.out.println("Final Line");
                last_line = true;
            }
            if (SolverDriver.containsLetters(line) && last_line == false) {
                // Not an empty line
                System.out.println("Here we are");
                for (int i=0; i<line.length(); i++) {
                    Letter l = new Letter(line.charAt(i), i, row);
                    mainGrid[i][row-1] = l;
                }
            }
            if (last_line == true) {
                break;
            }

            line = file_reader.readLine();
            row += 1;
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
