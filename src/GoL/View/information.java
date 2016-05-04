package GoL.View;

import javafx.scene.control.Alert;

/**
 * Created by Boris Ilievski on 04.05.2016.
 */
public class information {

    public static void info()
    {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Game Of Life Instruction");
    alert.setHeaderText("Hvordan å teste det forskjellige tingene i programmet");
    alert.setContentText("Dynamic\n"+
            "1. Trykk dynamic checkboxen.\n"+
            "2. Tegn i canvas (trykk på grid for å få oversikt i griddet)\n"+
            "3. Tegn en Glider hvor som helst på griddet.\n"+
            "4. Trykk start og se griddet ekspandere.\n\n"+
            "OBS: Load klarer og laste noen .rle filer, men ikke optimalisert, for å teste load, bruk Statisk brett\n\n"+
            "Tegning:\n"+
            "1. Trykk på hvilken som helst celle du vil lage, du kan også dra musen for å tegne en strek eller mønster med ett musetrykk.\n"+
            "2. hold CTRL+museklikk for å slette celler i griddet.\n\n"+
            "Static:\n"+
            "1. Trykk static checkboxen\n"+
            "2. Tegn ønsket mønster på canvaset (trykk grid for oversikt over gridet)\n"+
            "3. Trykk start for å se generasjonene utvikle seg\n\n"+
            "Loading av fil:\n"+
            "1. Trykk load knappen\n"+
            "2. Velg hvilken .rle fil ønsket å kjøre.\n"+
            "3. Trykk ok på kommentaren som kommer opp.\n"+
            "4. Trykk start for å iterere.\n\n"+
            "Tips:\n"+
            "-For å få maksimal ytelse, lag ett statisk brett forså og laste ned ett brett\n"+
            "-Brett som er større enn 200x200 synes ikke veldig bra med grid, så huk av checkboxen for å se mønsteret bedre\n\n"+
            "Load URL:\n"+
            "1. Trykk Load URL\n"+
            "2. Skriv inn link (HUSK HTTP://\n"+
            "3. Trykk OK\n"+
            "4. Trykk ok når kommentaren kommer opp\n"+
            "5. Trykk Start for å se nedlastet mønster fra internett\n\n"+
            "Ekstra:\n" +
            "Speed slideren bytter mellom fps (frames per second) der min = 5, max =45\n"+
            "DiscoMode er en random color generator\n"+
            "NextGen knappen gir deg neste generasjon i programmet uten og iterere flere ganger etterhverandre.");

    alert.showAndWait();
    }
}
