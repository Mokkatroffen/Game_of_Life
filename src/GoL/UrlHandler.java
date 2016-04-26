package GoL;

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


        /*try {
            URL urlString = new URL(url); //Importer java.netf
            URLConnection connect = urlString.openConnection();*/
           try /*(BufferedReader bfr = new BufferedReader(new InputStreamReader(connect.getInputStream())))*/ {


               URL rlesite = new URL("http://hioagaming.no/deepcell.rle");
               ReadableByteChannel rbc = Channels.newChannel(rlesite.openStream());
               FileOutputStream fos = new FileOutputStream("src/GoL/information.rle");
               fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);


            } catch (IOException ioefeil) {
                System.out.println("Feilmelding for IOE-Feil");
            }
       /* } catch (MalformedURLException urlFeil) {
            System.out.println("Typisk URL feil");
        } catch (IOException IOEFeil) {
            System.out.println("Typ 404 feil");
        }*/

    }

}
