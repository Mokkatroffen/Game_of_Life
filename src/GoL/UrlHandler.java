package GoL;

import javafx.scene.control.TextInputDialog;

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

/**
 * Created by andreas on 21.04.16.
 */
public class UrlHandler {


    /**
     * readGameBoardFromUrl reads .rle file from an URL an implements it into the board.
     *
     * @param url url is the parameter which you gather the file from.
     * @throws IOException IOException throws out an error message if needed.
     * @author Kristian Munter Simonsen.
     * @version 0.2 - April 18, 2016.
     */
    public void readGameBoardFromURL(String url) throws IOException {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("URL");
        dialog.setHeaderText("This program only handles java valid URLs, remember http://.");
        dialog.setContentText("Please enter the URL:");
        /*String tekst = dialog.getContentText();
        System.out.println(tekst);*/
        Optional<String> result = dialog.showAndWait();
       // System.out.println(result);
        String test = result.get();
        System.out.println(test);


        //String url1 = result.toString();
        //System.out.println(url1 + "rawr");
        //result.ifPresent(link -> System.out.println(link));

        try {
            URL rlesite = new URL(test);
            ReadableByteChannel rbc = Channels.newChannel(rlesite.openStream());
            FileOutputStream fos = new FileOutputStream("src/GoL/information.rle");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (IOException ioefeil) {
                System.out.println("Feilmelding for IOE-Feil");
        }/*
         catch (MalformedURLException urlFeil) {
            System.out.println("Typisk URL feil");
        } catch (IOException IOEFeil) {
            System.out.println("Typ 404 feil");
        }*/

    }

}
