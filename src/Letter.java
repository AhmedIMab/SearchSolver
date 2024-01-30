public class Letter {
    public char letter;
    int xcoord;
    int ycoord;

    public Letter(char letter, int x, int y) {
        this.letter = letter;
        this.xcoord = x;
        this.ycoord = y;
    }

    public String toString() {
        return String.valueOf(letter);
    }
}
