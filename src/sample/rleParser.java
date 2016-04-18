package sample;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by andreas on 4/13/2016.
 */
public class rleParser {

    public void readGameBoard(BufferedReader r) throws IOException {
        //Skal kunne lese metadata og brett
        String author = new String();
        String name = new String();
        String comment = new String();
        String line = new String();

        while ((line = r.readLine()) != null) { //r er en buffered reader, sjekker på om det eksistere noe i BufferedReaderen.
            if (line.charAt(0) == '#') { //leser etter metadata i kommentarer her, åpner det og lagrer dataen om feks forfatter.
                if (line.charAt(1) == 'C') {
                    comment = line;
                } else if (line.charAt(1) == 'N') {
                    name = line;
                } else if (line.charAt(1) == 'O') {
                    author = line;
                } else if (line.charAt(1) == 'c') { //dette kan også være comment, sjekk i RLE filer og på life-wiki.
                    comment = line;
                }
                continue;
            }
            else {
                //her kommer enten selve brettet eller metadata om størrelse på brettet i form av x, y og rule som er en divisjon.
                //Pattern.compile er en en statisk metode som returnerer ett Pattern objekt, denne returnerer bare hva som kreves for å matche.
                //så bruker vi en matcher senere for å kjøre patterne fra kompileren.
                /* pattern for bruk, kopier tre ganger og test for y, x og rulee
                *  pattern RLEpatternY = Pattern.compile("[yY][\\s][=][\\s]([\\d]+)";
               *///
            }

        }

    }

    public void readGameBoardFromDisk(File file) throws IOException {
        if (!file.canRead()) {
            System.out.println("Kunne ikke lese fil.");
        }
        try (BufferedReader bfr = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            readGameBoard(bfr);
        } catch (FileNotFoundException FileNotFound) {
            System.out.println("Feilmelding for buffer");
        } catch (IOException IOEFeil) {
            System.out.println("Feilmelding for IOE-Feil");
        }
    }

    public void readGameBoardFromURL(String url) throws IOException {

        try {
            URL urlString = new URL(url); //Importer java.net
            URLConnection connect = urlString.openConnection();
            try (BufferedReader bfr = new BufferedReader(new InputStreamReader(connect.getInputStream()))) {
                readGameBoard(bfr);
            } catch (IOException ioefeil) {
                System.out.println("Feilmelding for IOE-Feil");
            }
        } catch (MalformedURLException urlFeil) {
            System.out.println("Typisk URL feil");
        } catch (IOException IOEFeil) {
            System.out.println("Typ 404 feil");
        }

    }

}
