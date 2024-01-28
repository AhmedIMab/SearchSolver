import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SolverDriver {
    public static void main(String[] args) throws IOException {
        SolverDriver s = new SolverDriver();
        System.out.println("Welcome to Search Solver!");
        String[] words = {"HORSE", "MOUSE", "CAT", "DOG", "BAT"};
        Grid g = new Grid("src\\grid1.txt");
        // System.out.println(g.mainGrid[2][3].letter);
    }

    public String[] getArrayOfWordsWithLetterAtPos(String[] words, Letter letterX, int pos) {
        // Assuming the potentially all the words to find could be
        // Nope, have to use an ArrayList as otherwise filled with null values
        List<String> foundStrings = new ArrayList<String>();
        for (int i=0; i<words.length; i++) {
            if (words[i].charAt(pos) == letterX.letter) {
                foundStrings.add(words[i]);
            }
        }
        String[] foundWords = new String[foundStrings.size()];
        return foundStrings.toArray(foundWords);
    }

    public Direction[] findPossibleDirectionsFromPosition(Grid g, int starting_x, int starting_y) {
        List<Direction> directions = new ArrayList<Direction>();
        // For the top y rows
        if (starting_y == 0) {
            if (starting_x == 0) {
                directions.add(Direction.RIGHT);
                directions.add(Direction.BOTTOMRIGHT);
                directions.add(Direction.DOWN);
            } else if (starting_x > 0 && starting_x < g.getWidth() - 1) {
                directions.add(Direction.RIGHT);
                directions.add(Direction.BOTTOMRIGHT);
                directions.add(Direction.DOWN);
                directions.add(Direction.BOTTOMLEFT);
                directions.add(Direction.LEFT);
            } else {
                directions.add(Direction.DOWN);
                directions.add(Direction.BOTTOMLEFT);
                directions.add(Direction.LEFT);
            }
        } // For the middle y rows
        else if (starting_y > 0 && starting_y <g.getHeight() - 1) {
            if (starting_x == 0) {
                directions.add(Direction.UP);
                directions.add(Direction.TOPRIGHT);
                directions.add(Direction.RIGHT);
                directions.add(Direction.BOTTOMRIGHT);
                directions.add(Direction.DOWN);
            } // When any of the middle letters, the word can be made in any direction
            else if (starting_x > 0 && starting_x < g.getWidth() - 1) {
                directions.add(Direction.RIGHT);
                directions.add(Direction.BOTTOMRIGHT);
                directions.add(Direction.DOWN);
                directions.add(Direction.BOTTOMLEFT);
                directions.add(Direction.LEFT);
                directions.add(Direction.TOPLEFT);
                directions.add(Direction.UP);
                directions.add(Direction.TOPRIGHT);
            }
            else {
                directions.add(Direction.DOWN);
                directions.add(Direction.BOTTOMLEFT);
                directions.add(Direction.LEFT);
                directions.add(Direction.TOPLEFT);
                directions.add(Direction.UP);
            }
        } // For the bottom y rows
        else {
            if (starting_x == 0) {
                directions.add(Direction.UP);
                directions.add(Direction.TOPRIGHT);
                directions.add(Direction.RIGHT);
            }
            else if (starting_x > 0 && starting_x < g.getWidth() - 1) {
                directions.add(Direction.LEFT);
                directions.add(Direction.TOPLEFT);
                directions.add(Direction.UP);
                directions.add(Direction.TOPRIGHT);
                directions.add(Direction.RIGHT);
            }
            else {
                directions.add(Direction.LEFT);
                directions.add(Direction.TOPLEFT);
                directions.add(Direction.UP);
            }
        }
        Direction[] foundDirections = new Direction[directions.size()];
        return directions.toArray(foundDirections);
    }

    public boolean checkWordInDirectionFromLetter(Grid g, String word, Letter letter, Direction direction) {
        boolean found = false;
        int xchange = 0;
        int ychange = 0;
        if (direction == Direction.RIGHT) {
            xchange = 1;
            ychange = 0;
        }
        else if (direction == Direction.BOTTOMRIGHT) {
            xchange = 1;
            ychange = 1;
        }
        else if (direction == Direction.DOWN) {
            xchange = 0;
            ychange = 1;
        }
        else if (direction == Direction.BOTTOMLEFT) {
            xchange = -1;
            ychange = 1;
        }
        else if (direction == Direction.LEFT) {
            xchange = -1;
            ychange = 0;
        }
        else if (direction == Direction.TOPLEFT) {
            xchange = -1;
            ychange = -1;
        }
        else if (direction == Direction.UP) {
            xchange = 0;
            ychange = -1;
        }
        // Top right
        else {
            xchange = 1;
            ychange = -1;
        }

        Letter currentLetter = letter;
        // Assume the method has given us the correct starting letter in the word
        int num_correct = 1;
        for (int i=1; i<word.length();i++) {
            currentLetter = g.getLetterAtCoord((currentLetter.xcoord+xchange), currentLetter.ycoord+ychange);
            if (currentLetter.letter == word.charAt(i)) {
                num_correct += 1;
            }
            else {
                break;
            }
        }

        System.out.println(num_correct);

        if (num_correct == word.length()) {
            found = true;
        }

        return found;
    }

    // This method will only be called when the word has been confirmed to be in the wordsearch in the direction
    public CoordinatePair[] getCoordsOfWordInGrid(Grid g, String word, Letter letter, Direction direction) {
        CoordinatePair[] coords = new CoordinatePair[5];
        return coords;
    }
}
