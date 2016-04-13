package sample;

import java.io.IOException;
import java.io.Reader;
import java.io.File;
/**
 * Created by andreas on 4/13/2016.
 */
public class rleParser {

    public void readGameBoard(Reader r) throws IOException {}
    public void readGameBoardFromDisk(File file) throws IOException {
        if(!file.canRead())
            System.out.println("Kunne ikke lese fil.");

    }
    public void readGameBoardFromURL(String url) throws IOException {}
}
