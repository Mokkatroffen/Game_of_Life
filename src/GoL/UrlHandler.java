package GoL;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import javafx.scene.control.Alert;

import java.io.*;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.net.*;
import java.util.Optional;


public class UrlHandler {
    /**
     * readGameBoardFromUrl reads .rle file from an URL an implements it into the board.
     *
     * @param url url is the parameter which you gather the file from.
     * @throws IOException IOException throws out an error message if needed.
     * @author Kristian Munter Simonsen.
     * @version 0.2 - April 18, 2016.
     */


    public static GameBoard readGameBoardFromURL(GameBoard gb) throws IOException {
        Pattern urlPattern = Pattern.compile("^((http[s]?|ftp[s]?|ssh):\\/\\/)   ?    ([^:\\/\\s]+)(:[0-9]+)   ?    ((?:\\/\\w+)*\\/) ([\\w\\-\\.]+[^#?\\s]+) ([^#\\s]*) ? (#[\\w\\-]+)? (.rle)$"
        );

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("URL");
        dialog.setHeaderText("This program only handles java valid URLs, remember http://.");
        dialog.setContentText("Please enter the URL:");
        //prøv å sette Optional STRING result til URL
        Optional<String> result = dialog.showAndWait();
        //pregmatchen her
        // Vi trenger å regexxe resultatet i dialogboksen før det legges i string.


        String test = result.get();

        Matcher matcher = urlPattern.matcher(test);

        /*if(test.matches("((http[s]?|ftp[s]?|ssh):\\/\\/)")) {
            System.out.println("trolol");
        }*/

       // System.out.println(findErrors(test));
        /*Matcher urlMatch = urlPattern.matcher(test);
        if(urlMatch.find()) {
            System.out.println("Regexxen vellykket!");
        }
        else {
            System.out.println("Den funker, men feil input.");

        }
        */System.out.println(test);
        File selectedFile = new File(test);

        //else feilmelding
        String[] patterns = {"^(https?|^ftp|^ssh)(:\\/\\/)", "(.rle$)"};
        String[] responses = {"Manglende http[s], ftp eller ssh.", "Mangler .rle"};
        MatchMaster m = new MatchMaster(patterns, responses, "Feil nr", true);

        m.match(test);



        {
            try {

                URL rlesite = new URL(test);
                ReadableByteChannel rbc = Channels.newChannel(rlesite.openStream());
                FileOutputStream fos = new FileOutputStream("src/GoL/web.rle");
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            } catch (IOException ioefeil) {
                System.out.println("Feilmelding for IOE-Feil");
            }

            if (selectedFile != null) {
                System.out.println("Controller.fileOpener:" + selectedFile);

                rleParser parser = new rleParser();
                try {

                    File file = new File("src/GoL/web.rle");
                    System.out.println(file.getAbsolutePath());
                    parser.readGameBoardFromDisk(file);
                    gb.setBoard(parser.getBoard()); //setter størrelsen på brettet bassert på drawCellGrid i rle fil, fungerer ikke før du trykker start.
                } catch (IOException e) { //spytter ut eventuelle feilmedling
                    System.out.println(e.getMessage());
                }
            }



        //String url1 = result.toString();
        //System.out.println(url1 + "rawr");
        //result.ifPresent(link -> System.out.println(link));

        try {
            URL rlesite = new URL(test);
            ReadableByteChannel rbc = Channels.newChannel(rlesite.openStream());
            FileOutputStream fos = new FileOutputStream("src/GoL/web.rle");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            System.out.println(fos.toString());
        } catch (IOException ioefeil) {
            System.out.println("Feilmelding for IOE-Feil");
        }/*
         catch (MalformedURLException urlFeil) {
            System.out.println("Typisk URL feil");
        } catch (IOException IOEFeil) {
            System.out.println("Typ 404 feil");
        }*/

        //  rleParser lala = new rleParser();

        //  lala.readGameBoard(test);


        //*/}
        return gb;


    }
}}
/*
    private static String findErrors(String s) {
        String[] matcher = {"((http[s]?|ftp[s]?|ssh):\\/\\/)", "([^:\\/\\s]+)(:[0-9]+)", "((?:\\/\\w+)*\\/)", "([\\w\\-\\.]+[^#?\\s]+)", "([^#\\s]*)", "(#[\\w\\-]+)", "(.rle)"};

        StringBuilder result = new StringBuilder();
        if(!s.matches(matcher[0])) {
            result.append("Du mangler http[s], ftp eller ssh.\n");
        } if(!s.matches(matcher[1])) {
            result.append("Du mangler 1 \n");
        } if(!s.matches(matcher[2])) {
            result.append("Du mangler 2 \n");
        } if(!s.matches(matcher[3])) {
            result.append("Du mangler 3 \n");
        } if(!s.matches(matcher[4])) {
            result.append("Du mangler 4 \n");
        } if(!s.matches(matcher[5])) {
            result.append("Du mangler 4 \n");
        } if(!s.matches(matcher[6])) {
            result.append("Du mangler 5 \n");
        }

        return result.toString();
    }
}

*/