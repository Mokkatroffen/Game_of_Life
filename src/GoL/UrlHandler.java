package GoL;

import javafx.scene.control.Alert;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.TextInputDialog;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
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


        System.out.println(test);
        File selectedFile = new File(test);

        //else feilmelding
        String[] patterns = {"^(https?|^ftp|^ssh)(:\\/\\/).*", "^.*(\\.rle$)"};
        String[] responses = {"Manglende http[s], ftp eller ssh.", "Mangler .rle"};
        MatchMaster m = new MatchMaster(patterns, responses, "Feil nr", true);

        String tester = test;
        System.out.println(tester);
        m.match("hioagaming.no/rats.rle");

        String error = m.match(test);
        System.out.println(error);
        int x = 0;
        if (error == null) {
            System.out.println("lolriktigadriandildi");
            URL rlesite = new URL(test);
            ReadableByteChannel rbc = Channels.newChannel(rlesite.openStream());
            FileOutputStream fos = new FileOutputStream("src/GoL/web.rle");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            x++;

        } else {
            System.out.println(error);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("The URL did not meet the URL-formating standards");
            alert.setHeaderText("The inserted URL was malformated");
            alert.setContentText(error);

            alert.showAndWait();
        }

        try {
            if(x == 1) {
                URL rlesite = new URL(test);
                ReadableByteChannel rbc = Channels.newChannel(rlesite.openStream());
                FileOutputStream fos = new FileOutputStream("src/GoL/web.rle");
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            }
            else {
                System.out.println("Ingen gyldig fil funnet i URL");
            }

        } catch (IOException ioefeil) {
            System.out.println("Feilmelding for IOE-Feil");
        }

        if (selectedFile != null && x == 1) {
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
        try {
            URL rlesite = new URL(test);
            ReadableByteChannel rbc = Channels.newChannel(rlesite.openStream());
            FileOutputStream fos = new FileOutputStream("src/GoL/web.rle");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            System.out.println(fos.toString());
        } catch (IOException ioefeil) {
            System.out.println("Feilmelding for IOE-Feil");
        }
        return gb;


    }
}