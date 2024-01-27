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
    void checkWordsAtPos(String[] allWords, char letter, int pos, String[] expectedWords) {
        SolverDriver solverDriver = new SolverDriver();
        // The below positions are defaulted as the method being tested does not use the coordinates
        Letter letterX = new Letter(letter, 0,0);
        String[] foundWords = solverDriver.getArrayOfWordsWithLetterAtPos(allWords, letterX, pos);
        System.out.println(Arrays.toString(expectedWords));
        System.out.println(Arrays.toString(foundWords));
        assertArrayEquals(expectedWords, foundWords);
    }

    private static Stream<Arguments> provideStringArraysForCheckingWords() {
        return Stream.of(
                Arguments.of(new String[]{"HORSE","HAPPY", "TRAIN"}, 'H', 0, new String[]{"HORSE", "HAPPY"}),
                Arguments.of(new String[]{"HORSE","HAPPY", "TRAIN"}, 'A', 1, new String[]{"HAPPY"})
        );
    }
}