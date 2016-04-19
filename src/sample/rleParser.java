package sample;

import com.sun.javafx.scene.control.skin.IntegerFieldSkin;
import javafx.scene.control.Alert;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class rleParser {


     private boolean[][] boardTufte; //Skal sendes til gameboard på slutten av metuden under. lar oss ha costum størrelse.
     public boolean[][] getBoard(){
         return boardTufte;
     }
    public void setBoard(boolean [][]getBrett){
         // boardTufte = getBrett;
        this.boardTufte = getBrett;
    }




     public void readGameBoard(BufferedReader r) throws IOException {
        //Skal kunne lese metadata og brett
        String author = new String();
        String name = new String();
        String comment = new String();
        String line = new String();
        int colms = 0;
        int rows = 0;


        StringBuilder melding = new StringBuilder();  //"Kommentar: " + comment + "\n Navn" + name + "Forfatter: " + author + "Kommentar: " + comment;

        while ((line = r.readLine()) != null) { //r er en buffered reader, sjekker på om det eksistere noe i BufferedReaderen.
            if (line.charAt(0) == '#') { //leser etter metadata i kommentarer her, åpner det og lagrer dataen om feks forfatter.
                if (line.charAt(1) == 'C') {
                    comment = line.substring(3);
                    melding.append("Kommentar: " + comment + "\n");
                } else if (line.charAt(1) == 'N') {
                    name = line.substring(3);
                    melding.append("Navn: " + name + "\n");
                } else if (line.charAt(1) == 'O') {
                    author = line.substring(3);
                    melding.append("Forfatter: " + author + "\n");
                } else if (line.charAt(1) == 'c') { //dette kan også være comment, sjekk i RLE filer og på life-wiki..
                    comment = line.substring(3);
                    melding.append("Kommentar: " + "\n");
                }

                continue;


            } else

            {

                //[x][ ][=][ ]([\\d+])[,][ ][y][ ][=][ ]([\\d+])
                Pattern bokstaver = Pattern.compile("[x][ ][=][ ]([\\d]+)[,][ ][y][ ][=][ ]([\\d]+)"); //her bruker vi groups så sidte \\d tester på tall, vi kan kalle dem med tallposisjoner
                Matcher matcher = bokstaver.matcher(line);
                if(matcher.find()) { //returnerer en bolsk verdi
                     rows = Integer.parseInt(matcher.group(2)); //for å gjøre det om til tall istedenfor string på rows (x-akse)
                     colms = Integer.parseInt(matcher.group(1)); //for å gjøre det om til tall istedenfor string på colmns (y-akse)

                     boardTufte = new boolean[rows][colms];
                    setBoard(boardTufte);
                   System.out.println(Arrays.deepToString(boardTufte)); // <- test for å se om boardTufte funker, drep senere.


                }
                    // Denne sier at hvis X eller Y er større enn 500 så ikke load filen og
                if(rows>500 || colms>500){
                    throw new IOException("Imported pattern to big");
                }






                //String[] metadata = line.split(",");
                //Pattern tall = Pattern.compile("[0-9]");

                  for (int i = 0; i < 2; i++) { //bruker pattern for å teste de to første posisjonene
            //løkken går igjennom x og y-posisjon
        }
            }


        }





        String meldingResultat = melding.toString();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Kommentarer fra filen");
        alert.setHeaderText(null);
        alert.setContentText(meldingResultat);
        alert.showAndWait();

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
