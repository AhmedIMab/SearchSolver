import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;


import static org.junit.jupiter.api.Assertions.*;

class SolverDriverTest {
    @DisplayName("Checks if the possible words with the letter at the position are the same")
    @ParameterizedTest
    @MethodSource("provideStringArraysForCheckingWords")
    void checkWordsAtPos(String[] allWords, char letter, int pos, String[] expected_words) {
        SolverDriver solverDriver = new SolverDriver();
        // The below positions are defaulted as the method being tested does not use the coordinates
        Letter letterX = new Letter(letter, 0,0);
        String[] foundWords = solverDriver.getArrayOfWordsWithLetterAtPos(allWords, letterX, pos);
        // System.out.println(Arrays.toString(expected_words));
        // System.out.println(Arrays.toString(foundWords));
        Assertions.assertArrayEquals(expected_words, foundWords);
    }

    private static Stream<Arguments> provideStringArraysForCheckingWords() {
        return Stream.of(
                Arguments.of(new String[]{"HORSE","HAPPY", "TRAIN"}, 'H', 0, new String[]{"HORSE", "HAPPY"}),
                Arguments.of(new String[]{"HORSE","HAPPY", "TRAIN"}, 'A', 1, new String[]{"HAPPY"})
        );
    }

    @DisplayName("Checks if the possible directions to make a word from a starting position are valid")
    @ParameterizedTest
    @MethodSource("providePositionsForCheckingDirections")
    void findPossibleDirectionsAtPos(int starting_x, int starting_y, Direction[] expected_directions) throws IOException {
        SolverDriver solverDriver = new SolverDriver();
        Grid g = new Grid("src\\grid1.txt");
        Direction[] directions = solverDriver.findPossibleDirectionsFromPosition(g, starting_x,starting_y);
        Arrays.sort(directions);
        Arrays.sort(expected_directions);
        Assertions.assertArrayEquals(expected_directions, directions);
    }

    private static Stream<Arguments> providePositionsForCheckingDirections() {
        return Stream.of(
                Arguments.of(0,0,new Direction[]{Direction.RIGHT, Direction.BOTTOMRIGHT, Direction.DOWN}),
                Arguments.of(1,0,new Direction[]{Direction.RIGHT, Direction.BOTTOMRIGHT, Direction.DOWN, Direction.BOTTOMLEFT, Direction.LEFT}),
                Arguments.of(4,0,new Direction[]{Direction.LEFT,Direction.BOTTOMLEFT,Direction.DOWN}),
                Arguments.of(2,2,new Direction[]{Direction.RIGHT, Direction.BOTTOMRIGHT, Direction.DOWN,Direction.BOTTOMLEFT,Direction.LEFT,Direction.TOPLEFT,Direction.UP,Direction.TOPRIGHT}),
                Arguments.of(0,4,new Direction[]{Direction.UP, Direction.TOPRIGHT, Direction.RIGHT}),
                Arguments.of(4,4,new Direction[]{Direction.UP, Direction.TOPLEFT, Direction.LEFT})
        );
    }

    @DisplayName("Checks if a word can be made in a specific direction")
    @ParameterizedTest
    @MethodSource("provideWordsToCheckFromLetter")
    void checkWordInDirectionFromLetter(String word, Letter letter, Direction direction, boolean expected_value) throws IOException {
        SolverDriver solverDriver = new SolverDriver();
        Grid g = new Grid("src\\grid1.txt");
        boolean boolX = solverDriver.checkWordInDirectionFromLetter(g, word, letter, direction);
        Assertions.assertEquals(expected_value, boolX);
    }

    private static Stream<Arguments> provideWordsToCheckFromLetter() {
        return Stream.of(
                Arguments.of("HORSE", new Letter('H', 0,0), Direction.RIGHT, true),
                Arguments.of("HORSE", new Letter('H', 0,0), Direction.BOTTOMRIGHT, false),
                Arguments.of("HORSE", new Letter('H', 0,0), Direction.DOWN, false),
                Arguments.of("MOUSE", new Letter('D', 1,3), Direction.RIGHT, false),
                Arguments.of("MOUSE", new Letter('M', 4,1), Direction.LEFT, true),
                Arguments.of("MOUSE", new Letter('M', 4,1), Direction.UP, false),
                Arguments.of("DOG", new Letter('D', 1,2), Direction.DOWN, true),
                Arguments.of("DOG", new Letter('D', 1,2), Direction.RIGHT, false),
                Arguments.of("BAT", new Letter('B', 2,2), Direction.BOTTOMRIGHT, true),
                Arguments.of("BAT", new Letter('B', 2,2), Direction.LEFT, false),
                Arguments.of("ABS", new Letter('A', 3,3), Direction.TOPLEFT, true),
                Arguments.of("ABS", new Letter('A', 3,3), Direction.TOPRIGHT, false),
                Arguments.of("ME", new Letter('M', 4,1), Direction.UP, true),
                Arguments.of("ME", new Letter('M', 4,1), Direction.DOWN, false),
                Arguments.of("UDA", new Letter('U', 2,1), Direction.BOTTOMLEFT, true)
        );
    }

    // To test the coords and avoid issues with objects of CoordinatePair, format will be comparing 2 integer arrays
    // Only given words actually in the wordsearch as the method being tested will only be run after checking the word can be made
    @DisplayName("Tests to see if the coordinates for making a word are correct")
    @ParameterizedTest
    @MethodSource("provideWordsToCheckCoords")
    void getCoordsOfWordInGrid(String word, Letter letter, Direction direction, Integer[] expectedCoords) throws IOException {
        SolverDriver solverDriver = new SolverDriver();
        Grid g = new Grid("src\\grid1.txt");
        CoordinatePair[] coordinates = solverDriver.getCoordsOfWordInGrid(g, word, letter, direction);
        Integer[] calculatedCoords = new Integer[word.length()*2];
        int ptr2 = 0;
        for (int i=0; i<calculatedCoords.length; i=i+2) {
            calculatedCoords[i] = coordinates[ptr2].getXcoord();
            calculatedCoords[i+1] = coordinates[ptr2].getYcoord();
            ptr2 += 1;
        }

        Assertions.assertArrayEquals(expectedCoords, calculatedCoords);
    }

    private static Stream<Arguments> provideWordsToCheckCoords() {
        return Stream.of(
                Arguments.of("HORSE", new Letter('H', 0,0), Direction.RIGHT, new Integer[]{0,0,1,0,2,0,3,0,4,0}),
                Arguments.of("BAT", new Letter('B',2,2), Direction.BOTTOMRIGHT, new Integer[]{2,2,3,3,4,4}),
                Arguments.of("DOG", new Letter('D',1,2), Direction.DOWN, new Integer[]{1,2,1,3,1,4}),
                Arguments.of("OBOC", new Letter('O',3,1), Direction.BOTTOMLEFT, new Integer[]{3,1,2,2,1,3,0,4}),
                Arguments.of("MO", new Letter('M',4,1), Direction.LEFT, new Integer[]{4,1,3,1}),
                Arguments.of("TABSH", new Letter('T',4,4), Direction.TOPLEFT, new Integer[]{4,4,3,3,2,2,1,1,0,0}),
                Arguments.of("DS", new Letter('D',1,2), Direction.UP, new Integer[]{1,2,1,1}),
                Arguments.of("ADUS", new Letter('A',0,3), Direction.TOPRIGHT, new Integer[]{0,3,1,2,2,1,3,0})
        );
    }

    @ParameterizedTest
    @DisplayName("Checks if the program can determine the directions that the word can be made")
    @MethodSource("provideWordsToCheckDirections")
    void findDirectionsToMakeWord(String word, Letter letter, Direction[] expected_directions) throws IOException {
        SolverDriver solverDriver = new SolverDriver();
        Grid g = new Grid("grid2.txt");
        Direction[] calculated_directions = solverDriver.findDirectionsToMakeWord(word, letter);
    }
}












