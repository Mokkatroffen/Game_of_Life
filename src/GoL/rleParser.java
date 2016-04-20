package GoL;

import javafx.scene.control.Alert;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class rleParser {


    private byte[][] boardTufte; //Skal sendes til gameboard på slutten av metuden under. lar oss ha costum størrelse.

    //henter data fra boardTufte som henter størrelse om brettet
    public byte[][] getBoard() {
        return boardTufte;
    }

    //setter størrelsen fra boardTufte, siden den er public kan vi kalle den ifra GameBoard
    /*public void setBoard(boolean[][] getBrett) {

        this.boardTufte = getBrett;
    }*/
   /* private String[] boardAlive;

    public String getAlive() {
        return setAlive();
    }
    public void isAlive(String getLife){
        this.boardAlive = getLife;
    }
*/
    public void readGameBoard(BufferedReader r) throws IOException { //split metoden, en for meta, en for celle og en for innhold.
        //Skal kunne lese metadata og brett
        String author = new String();
        String name = new String();
        String comment = new String();
        String line = new String();
        int colms;
        int rows;
        int number;


        StringBuilder melding = new StringBuilder();  //"Kommentar: " + comment + "\n Navn" + name + "Forfatter: " + author + "Kommentar: " + comment;

        //WHILE LOOP FOR METADATA OG BRETTSTØRRELSE
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

            } else if (line.charAt(0) == 'x') {

                //[x][ ][=][ ]([\\d+])[,][ ][y][ ][=][ ]([\\d+])
                Pattern bokstaver = Pattern.compile("[x][ ][=][ ]([\\d]+)[,][ ][y][ ][=][ ]([\\d]+)"); //her bruker vi groups så sidte \\d tester på tall, vi kan kalle dem med tallposisjoner
                Matcher matcher = bokstaver.matcher(line);
                if (matcher.find()) { //returnerer en bolsk verdi
                    rows = Integer.parseInt(matcher.group(2)); //for å gjøre det om til tall istedenfor string på rows (x-akse)
                    colms = Integer.parseInt(matcher.group(1)); //for å gjøre det om til tall istedenfor string på colmns (y-akse)

                    boardTufte = new byte[rows][colms];
                    // System.out.println(Arrays.deepToString(boardTufte)); // <- test for å se om boardTufte funker, drep senere.

                    /*if(line.matches(String.valueOf(patternMatch)) ) {
                    System.out.println("lol");
                    //trenger å laste inn dataen først
                   // Pattern thePattern = Pattern.compile("([0-9]{0,7})([a-zA-Z$]{1})");
                   // Matcher patternMatch = thePattern.matcher(line);
                    if (patternMatch.find()) { //returnener en bolsk verdi på hver av matchene den får, gruppert slik at bokstaver og tall skilles. Bruker ikke gruppene
                        cells = patternMatch.group(0);


                        System.out.println(cells); */
                    break;
                    //Nå vil du være på første linje som omhandler brettet
                }
            }
        }
        String meldingResultat = melding.toString();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Kommentarer fra filen");
        alert.setHeaderText(null);
        alert.setContentText(meldingResultat);
        alert.showAndWait();


        Pattern thePattern = Pattern.compile("([0-9]{0,7})([oObB$]{1})");
        int row = 0;
        int col = 0;
        //WHILE LOOP FOR SELVE BRETTET
        while ((line = r.readLine()) != null) {
            Matcher patternMatch = thePattern.matcher(line);
            while(patternMatch.find()) {
                if (!patternMatch.group(1).matches("[0-9]")) { //sjekker om det er ett tall
                    if (patternMatch.group(2).matches("o")) { //hvis pattern match er levende (0(
                        boardTufte[row][col] = 1; //så sett patternet til true, startposision 00.
                    } else if (patternMatch.group(2).matches("[$]")) {
                        row++;
                        col = 0;
                        continue;
                    }
                    col++;
                } else {
                    number = Integer.parseInt(patternMatch.group(1));
                    while(number > 0) {
                        if (patternMatch.group(2).matches("o")) { //hvis pattern match er levende (0(
                            boardTufte[row][col] = 1; //så sett patternet til true, startposision 00.
                        } else if (patternMatch.group(2).matches("[$]")) {
                            row++;
                            col = 0;
                            continue;
                        }
                        col++;
                        number--;
                    }
                }
                continue;

            }
        }
        System.out.println("Ferdig å laste program!");
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
