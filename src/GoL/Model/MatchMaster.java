package GoL.Model;

import java.util.StringJoiner;
/**
 * Matchmaster is a helper class that is used to processes data by the UrlHandler.
 * It returns errors and can take inn any regular expression from the class it is uesd.
 * We only use this in UrlHandler, but it could also be used elsewhere.
 * @Author Andreas Jacobsen
 * @Date 18. April 2016
 */ //
public class MatchMaster {

    private String[] patterns;
    private String[] responses;
    private String preText;
    private boolean ifn;

    /**
     * MatchMaster handles the responses for uploades.
     *
     * Sets the information to this.xxxxx. This is more modular for countinued use.
     *
     * @author Andreas Jacobsen.
     * @version 1.0 May 05, 2016
     *
     * @param patterns Keeps the error from the pattern URL.
     * @param responses Holds the error messages itselfe from the url handler
     * @param preText Holds the text about the file that is loaded
     * @param includeFaultNumber Includes the ammount of errors in the upload
     *
     * @see String represents a string in the UTF-16 format in which supplementary characters are represented by surrogate pairs.
     */
    public MatchMaster(String[] patterns, String[] responses, String preText, boolean includeFaultNumber) {
        this.patterns = patterns;
        this.responses = responses;
        this.preText = preText;
        this.ifn = includeFaultNumber;
    }

    /**
     * match presents the messages gathered from MatchMaster
     *
     * @author Kristian Munter Simonsen.
     * @version 1.0 May 05, 2016
     *
     * @param input gathers the information about the file itselfe
     * @return return returns the messages gathered.
     *
     * @see MatchMaster
     * @see String represents a string in the UTF-16 format in which supplementary characters are represented by surrogate pairs.
     */
    public String match(String input) {
        StringJoiner response = new StringJoiner("\n", "", "");
        int faults = 0;
        for(int i = 0; i < patterns.length; i++) {
            if(!input.matches(patterns[i]))
                response.add(new StringBuilder().append(preText).append(" ")
                        .append((ifn)?++faults:"").append(": ").append(responses[i]));
        }
        return (faults != 0)?"Total faults detected: " + faults + "\n" + response:null;
    }


}