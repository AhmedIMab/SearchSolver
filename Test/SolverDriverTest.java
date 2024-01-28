import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

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
        System.out.println(Arrays.toString(expected_words));
        System.out.println(Arrays.toString(foundWords));
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
    void findPossibleDirectionsAtPos(int starting_x, int starting_y, Direction[] expected_directions) {
        SolverDriver solverDriver = new SolverDriver();
        Direction[] directions = solverDriver.findPossibleDirectionsFromPosition(starting_x,starting_y);
        Assertions.assertArrayEquals(expected_directions, directions);
    }

    private static Stream<Arguments> providePositionsForCheckingDirections() {
        return Stream.of(
                Arguments.of(0,0,new Direction[]{Direction.RIGHT, Direction.BOTTOMRIGHT, Direction.DOWN}),
                Arguments.of(1,0,new Direction[]{Direction.RIGHT, Direction.BOTTOMRIGHT, Direction.DOWN, Direction.BOTTOMLEFT, Direction.LEFT})
                Arguments.of(4,0,new Direction[]{Direction.LEFT,Direction.BOTTOMLEFT,Direction.DOWN}),
                Arguments.of(2,2,new Direction[]{Direction.RIGHT, Direction.BOTTOMRIGHT, Direction.DOWN,Direction.BOTTOMLEFT,Direction.LEFT,Direction.TOPLEFT,Direction.UP,Direction.TOPRIGHT}),
                Arguments.of(0,4,new Direction[]{Direction.UP, Direction.TOPRIGHT, Direction.RIGHT}),
                Arguments.of(4,4,new Direction[]{Direction.UP, Direction.TOPLEFT, Direction.LEFT})
        );
    }

}