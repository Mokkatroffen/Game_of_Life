package GoL.View;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 * Created by Boris Ilievski on 04.05.2016. .
 */
public class Information {

    public void info() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        ImageView imageIcon = addIcon(alert);

        alert.setGraphic(imageIcon);
        alert.setTitle("Game Of Life Instruction");
        alert.setHeaderText("Hvordan å teste det forskjellige tingene i programmet");
        String message = "Hvordan brettet fungerer:\n"+
                "- Du kan tegne ved å klike eller dra på brettet.\n" +
                "- For å slette en celle holder du inne CTRL og trykker.\n" +
                "- For å zoome bruker du musehjulet.\n" +
                "- For å navigere på brettet holdes høyreklikke inne.\n" +
                "- Speed slideren bytter mellom farten på itterasjonene der min = 5, max =35\n" +
                "- DiscoMode er en random color generator\n" +
                "- NextGen knappen gir deg neste generasjon i programmet uten og iterere flere ganger etterhverandre.\n"+
                "\n Dynamisk brett: \n" +
                "1. Dynamisk lastets som standard\n" +
                "2. Trykk på grid for å få oversikt i griddet.\n" +
                "3. Tegn en Glider hvor som helst på griddet for å oppleve det dynamiske liv.\n" +
                "4. Trykk start og se griddet ekspandere.\n" +
                "5. Du kan også laste dynamiske filer fra nett og lokalt. \n" +
                "OBS: Om du velger statisk brett vil ikke dynamisk være tilgjengelig lenger. \n\n" +
                "Statisk brett:\n" +
                "1. Trykk static checkboxen.\n" +
                "2. Tegn ønsket mønster på canvaset (trykk grid for oversikt over gridet).\n" +
                "3. Trykk start for å se generasjonene utvikle seg.\n" +
                "OBS: Om du velger statisk brett vil ikke dynamisk være tilgjengelig lenger. \n\n" +
                "Loading av fil:\n" +
                "1. Trykk load knappen.\n" +
                "2. Velg hvilken .rle fil ønsket å kjøre.\n" +
                "3. Trykk ok på kommentaren som kommer opp.\n" +
                "4. Trykk start for å iterere.\n\n" +
                "Load URL:\n" +
                "1. Trykk Load URL.\n" +
                "2. Skriv inn link (HUSK HTTP://).\n" +
                "3. Trykk OK.\n" +
                "4. Trykk ok når kommentaren kommer opp.\n" +
                "5. Trykk Start for å se nedlastet mønster fra internett.\n\n"+
                " Dette spillet er utviklet som en eksamensoppgave i faget DATS1600 våren 2016 av\n"+
                " Andreas Jacobsen, Brois Ilievski & Kristian Munter Simonsen";

        TextArea textArea = new TextArea(message);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(textArea, 0, 1);

// Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);
        alert.getDialogPane().expandedProperty().setValue(true);

        alert.showAndWait();
    }

    private ImageView addIcon(Alert alert) {
        Image image = new Image(this.getClass().getResource("GoL.gif").toString());
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(image);
        ImageView imageIcon = new ImageView(image);
        imageIcon.setFitWidth(50);
        imageIcon.setPreserveRatio(true);
        return imageIcon;
    }
}
