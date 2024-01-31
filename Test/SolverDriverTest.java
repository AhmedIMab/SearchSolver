import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.stream.Stream;

class SolverDriverTest {
    @DisplayName("Checks if the possible words with the letter at the position are the same")
    @ParameterizedTest
    @MethodSource("provideStringArraysForCheckingWords")
    void checkWordsAtPos(String[] allWords, char letter, int pos, String[] expected_words) {
        SolverDriver solverDriver = new SolverDriver();
        // The below positions are defaulted as the method being tested does not use the coordinates
        Letter letterX = new Letter(letter, 0,0);
        String[] foundWords = solverDriver.getArrayOfWordsWithLetterAtPos(allWords, letterX, pos);
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
        Grid g = new Grid("Grids\\examplegrid1-5x5.txt");
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
        Grid g = new Grid("Grids\\examplegrid1-5x5.txt");
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
        Grid g = new Grid("Grids\\examplegrid1-5x5.txt");
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


    @ParameterizedTest(name = "{index} => grid={0},words={1},expected_ints={2}")
    @DisplayName("Checks if the LinkedHashMap returned has the right coordinates for all the words")
    @MethodSource("provideWordsAndCoordinatePairs")
    // Easier to make an integer array which holds x coordinate followed by y coordinate and then compare 2 integer arrays
    void getAllCoordinatePairsOfWords(Grid g, String[] words, Integer[][] expected_coordinatePairs) {
        SolverDriver solverDriver = new SolverDriver();
        LinkedHashMap<String, CoordinatePair[]> calculatedHashmap = solverDriver.getAllCoordinatePairsOfWords(g, words);
        int ptr2;
        for (int i=0;i<expected_coordinatePairs.length; i++) {
            ptr2 = 0;
            String wordX = words[i];
            Integer[] expected_coordsX = expected_coordinatePairs[i];
            if (expected_coordsX.length == 0) {
                continue;
            }
            CoordinatePair[] coordsX = calculatedHashmap.get(wordX);
            // The length of the coordinate pair stored for the word * 2 as now converting to integer array
            Integer[] integerCoordsX = new Integer[coordsX.length*2];
            // Converts the coordinate array
            for (int j=0; j<integerCoordsX.length; j=j+2) {
                integerCoordsX[j] = coordsX[ptr2].getXcoord();
                integerCoordsX[j+1] = coordsX[ptr2].getYcoord();
                ptr2 += 1;
            }

            System.out.println("Word: " + wordX + " Expected Coords: " + Arrays.toString(expected_coordsX) + " Calculated Coords: " + Arrays.toString(integerCoordsX));
            Assertions.assertArrayEquals(expected_coordsX, integerCoordsX);
        }
    }

    private static Stream<Arguments> provideWordsAndCoordinatePairs() throws IOException {
        return Stream.of(
                Arguments.of(new Grid("Grids\\examplegrid1-5x5.txt"), new String[]{"HORSE", "BAT", "DOG", "MOUSE", "MO"}, new Integer[][]{
                        {0,0,1,0,2,0,3,0,4,0}, {2,2,3,3,4,4}, {1,2,1,3,1,4}, {4,1,3,1,2,1,1,1,0,1}, {4,1,4,2}
                }),
                Arguments.of(new Grid("Grids\\examplegrid3-6x6.txt"), new String[]{"BARACK", "CLAY", "NOPE", "BAN", "OPEN", "COPE", "DOG", "SORE", "END"}, new Integer[][] {
                        {0,0,1,0,2,0,3,0,4,0,5,0}, {3,1,2,1,1,1,0,1}, {2,2,3,2,4,2,5,2}, {0,0,1,1,2,2}, {3,2,2,3,1,4,0,5}, {4,5,3,4,2,3,1,2}, {3,5,3,4,3,3}, {2,5,3,4,4,3,5,2}, {5,2,5,3,5,4}
                }),
                // BANK is not a word in the grid but END is. This test will check if the method can still get the coordinates for END
                Arguments.of(new Grid("Grids\\examplegrid3-6x6.txt"), new String[]{"BARACK", "CLAY", "NOPE", "BANK", "END"}, new Integer[][] {
                        {0,0,1,0,2,0,3,0,4,0,5,0}, {3,1,2,1,1,1,0,1}, {2,2,3,2,4,2,5,2}, {}, {5,2,5,3,5,4}
                }),
                Arguments.of(new Grid("Grids\\examplegrid4-7x7.txt"), new String[]{"CATTLE", "COW", "DUCK", "GOAT", "HORSE", "LAMB", "LLAMA", "PIG", "TURKEY", "YAK"}, new Integer[][] {
                        {5,0,5,1,5,2,5,3,5,4,5,5}, {4,1,3,1,2,1}, {6,2,6,3,6,4,6,5}, {2,0,3,1,4,2,5,3}, {0,5,0,4,0,3,0,2,0,1}, {1,4,2,4,3,4,4,4}, {0,6,1,5,2,4,3,3,4,2}, {0,0,1,0,2,0}, {6,6,5,6,4,6,3,6,2,6,1,6}
                })
        );
    }

    @ParameterizedTest
    @DisplayName("Gets the words from the file to search for")
    @MethodSource("provideFilesToDetectWords")
    void getWordsFromFile(String filename, String[] expected_words) throws IOException {
        Grid g = new Grid(filename);
        String[] words = SolverDriver.getWordsFromFile(filename);
        Assertions.assertArrayEquals(expected_words, words);
    }

    private static Stream<Arguments> provideFilesToDetectWords() {
        return Stream.of(
                Arguments.of("Grids\\examplegrid1-5x5.txt", new String[]{"HORSE", "BAT", "DOG", "MOUSE", "CAT"}),
                Arguments.of("Grids\\examplegrid3-6x6.txt", new String[]{"BARACK", "CLAY", "NOPE", "BAN", "OPEN", "COPE", "DOG", "SORE", "END"}),
                Arguments.of("Grids\\examplegrid4-7x7.txt", new String[]{"CATTLE", "COW", "DUCK", "GOAT", "HORSE", "LAMB", "LLAMA", "PIG", "TURKEY", "YAK"}),
                // This final test will have the words at the end of the file with no space inbetween to test if the program can handle the user writing the words without spaces
                Arguments.of("Grids\\examplegrid6-15x15.txt", new String[]{"BINGO", "BOUTIQUE", "CABARET", "CARAVAN", "CHALET", "CHILDREN", "CRECHE", "DIVING",
                        "GAMES", "PEDALO", "POOL", "POSTCARD", "REDCOAT", "SQUASH", "SWIMMING", "TENNIS", "TENTS", "VOLLEYBALL"}),
                // The below will have a file with an empty line at the end just to test if the program can handle a file without any words at the end
                Arguments.of("Grids\\examplegrid5-7x7.txt", null)
        );
    }



}













