import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Grid {
    private int width;
    private int height;

    public Grid(String filename) throws IOException {
        BufferedReader file_reader = new BufferedReader(new FileReader(filename));
        String line = file_reader.readLine();

    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }
}
