import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class SolverDriver {
    public static void main(String[] args) throws IOException {
        SolverDriver s = new SolverDriver();
        System.out.println("Welcome to Search Solver!");
        String[] words = {"HORSE", "MOUSE", "CAT", "DOG", "BAT"};
        Grid g = new Grid("src\\grid1.txt");
        // System.out.println(g.mainGrid[2][3].letter);
    }

    // The main method to find all the words and add them to a linked hashmap
    public LinkedHashMap<String, CoordinatePair[]> getAllCoordinatePairsOfWords(Grid g, String[] words) {
        // Linked HashMap used as it is preferred to have the order of words maintained
        LinkedHashMap<String, CoordinatePair[]> allWordCoordinates = new LinkedHashMap<>();
        return allWordCoordinates;
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

    // Regardless of the word, this function tries to find the possible directions a word can be made
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

    public static Integer[] getChangeInCoordsForTraversing(Direction direction) {
        int xchange = 0;
        int ychange = 0;
        if (direction == Direction.RIGHT) {
            xchange = 1;
        }
        else if (direction == Direction.BOTTOMRIGHT) {
            xchange = 1;
            ychange = 1;
        }
        else if (direction == Direction.DOWN) {
            ychange = 1;
        }
        else if (direction == Direction.BOTTOMLEFT) {
            xchange = -1;
            ychange = 1;
        }
        else if (direction == Direction.LEFT) {
            xchange = -1;
        }
        else if (direction == Direction.TOPLEFT) {
            xchange = -1;
            ychange = -1;
        }
        else if (direction == Direction.UP) {
            ychange = -1;
        }
        // Top right
        else {
            xchange = 1;
            ychange = -1;
        }

        return new Integer[]{xchange,ychange};
    }

    // Tries to find if a word can be made in a given direction
    public boolean checkWordInDirectionFromLetter(Grid g, String word, Letter letter, Direction direction) {
        boolean found = false;
        Integer[] change = getChangeInCoordsForTraversing(direction);
        int xchange = change[0];
        int ychange = change[1];

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
        CoordinatePair[] coords = new CoordinatePair[word.length()];
        Integer[] change = getChangeInCoordsForTraversing(direction);
        int xchange = change[0];
        int ychange = change[1];

        coords[0] = new CoordinatePair(letter.xcoord, letter.ycoord);

        Letter current_letter = letter;
        for (int i=1; i<word.length();i++) {
            current_letter = g.getLetterAtCoord((current_letter.xcoord+xchange), current_letter.ycoord+ychange);
            coords[i] = new CoordinatePair(current_letter.xcoord,current_letter.ycoord);
        }

        return coords;
    }
}
