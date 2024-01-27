public class SolverDriver {
    public static void main(String[] args) {
        System.out.println("Welcome to Search Solver!");
        String[] words = {"HORSE", "MOUSE", "CAT", "DOG", "BAT"};
    }

    public String[] getArrayOfWordsWithLetterAtPos(String[] words, Letter letterX, int pos) {
        // Assuming the potentially all the words to find could be
        String[] foundWords = new String[words.length];
        for (int i=0; i<words.length; i++) {
            if (Character.compare(words[i].charAt(pos),letterX.letter) == 0) {

            }
        }
    }
}
