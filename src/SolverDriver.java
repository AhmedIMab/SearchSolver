import java.util.ArrayList;
import java.util.List;

public class SolverDriver {
    public static void main(String[] args) {
        System.out.println("Welcome to Search Solver!");
        String[] words = {"HORSE", "MOUSE", "CAT", "DOG", "BAT"};
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
}
