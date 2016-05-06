package GoL.Model;

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

/**
 * readGameBoardFromUrl reads .rle file from an URL an implements it into the board.
 * This class also uses the MatchMaster-class to check for and return errors.
 * <p>
 * Using other classes this class can both retrive the file, save it, and send tell the rle-parser to start work on the file.
 *
 * @author Kristian Munter Simonsen.
 * @version 0.2 - April 18, 2016.
 * @throws IOException IOException throws out an error message if needed.
 */
public class UrlHandler {

    /**
     * readGameBoardFromUrl reads .rle file from an URL an implements it into the board.
     * <p>
     * This method is spesificly for the static game board. Due to little time left we had to duplicate the mathod for dynamic aswell.
     * Handling error messages when wrong files or urls are inserted. Strings and alike also are implemented here.
     *
     * @param gb Parameter for inserting url to GameBoard.
     * @return gb gb holds the return board from the URL.
     * @throws IOException IOException throws out an error message if needed.
     * @author Kristian Munter Simonsen.
     * @version 1.0 - May 05, 2016.
     * @see GameBoard
     */
    public static GameBoard readGameBoardFromURL(GameBoard gb) throws IOException {
        Pattern urlPattern = Pattern.compile("^((http[s]?|ftp[s]?|ssh):\\/\\/)   ?    ([^:\\/\\s]+)(:[0-9]+)   ?    ((?:\\/\\w+)*\\/) ([\\w\\-\\.]+[^#?\\s]+) ([^#\\s]*) ? (#[\\w\\-]+)? (.rle)$"
        );

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("URL");
        dialog.setHeaderText("This program only handles java valid URLs, remember http://.");
        dialog.setContentText("Please enter the URL:");
        Optional<String> result = dialog.showAndWait();
        String test;
        if (!result.isPresent()) {
            return new GameBoard(20, 20);
        } else {
            test = result.get();
        }

        Matcher matcher = urlPattern.matcher(test);

        System.out.println(test);
        File selectedFile = new File(test);

        //else feilmelding
        String[] patterns = {"^(https?|^ftp|^ssh)(:\\/\\/).*", "^.*(\\.rle$)"};
        String[] responses = {"Manglende http[s], ftp eller ssh.", "Mangler .rle"};
        MatchMaster m = new MatchMaster(patterns, responses, "Feil nr", true);

        String tester = test;
        System.out.println(tester);

        String error = m.match(test);
        System.out.println(error);
        int x = 0;
        try {
            if (error == null) {
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


            if (x == 1) {
                URL rlesite = new URL(test);
                ReadableByteChannel rbc = Channels.newChannel(rlesite.openStream());
                FileOutputStream fos = new FileOutputStream("src/GoL/web.rle");
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            }
        } catch (IOException ioefeil) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error reading file");
            alert.setHeaderText("Could not get the .rle-file from URL");
            alert.setContentText("Please check that your URL contains a .rle file");
            alert.showAndWait();
        }

        if (selectedFile != null && x == 1) {
            System.out.println("Controller.fileOpener:" + selectedFile);

            RleParser parser = new RleParser();
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

    /**
     * readGameBoardFromURL reads .rle file from an URL an implements it into the board.
     * <p>
     * This method is specifically for the dynamic game board. Due to little time left we had to duplicate the method for dynamic as well.
     * Handling error messages when wrong files or urls are inserted. Strings and alike also are implemented here.
     *
     * @param gb Parameter for inserting url to GameBoard.
     * @return gb gb holds the board from the url and returns it.
     * @throws IOException IOException throws out an error message if needed.
     * @author Kristian Munter Simonsen.
     * @version 1.0 - May 05, 2016.
     */
    public static DynamicBoard readGameBoardFromURL(DynamicBoard gb) throws IOException {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("URL");
        dialog.setHeaderText("This program only handles java valid URLs, remember http://.");
        dialog.setContentText("Please enter the URL:");
        Optional<String> result = dialog.showAndWait();

        String test;
        if (!result.isPresent()) {
            return new DynamicBoard(20, 20);
        } else {
            test = result.get();
        }

        System.out.println(test);
        File selectedFile = new File(test);

        //else feilmelding
        String[] patterns = {"^(https?|^ftp|^ssh)(:\\/\\/).*", "^.*(\\.rle$)"};
        String[] responses = {"Manglende http[s], ftp eller ssh.", "Mangler .rle"};
        MatchMaster m = new MatchMaster(patterns, responses, "Feil nr", true);

        String tester = test;
        System.out.println(tester);

        String error = m.match(test);
        System.out.println(error);
        int x = 0;
        try {
            if (error == null) {
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
            if (x == 1) {
                URL rlesite = new URL(test);
                ReadableByteChannel rbc = Channels.newChannel(rlesite.openStream());
                FileOutputStream fos = new FileOutputStream("src/GoL/web.rle");
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            }
        } catch (IOException ioefeil) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error reading file");
            alert.setHeaderText("Could not get the .rle-file from URL");
            alert.setContentText("Please check that your URL contains a .rle file");
            alert.showAndWait();
        }
        if (selectedFile != null && x == 1) {
            System.out.println("Controller.fileOpener:" + selectedFile);
            RleParser parser = new RleParser();
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