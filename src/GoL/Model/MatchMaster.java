package GoL.Model;

import java.util.StringJoiner;
/**
 * Matchmaster is a helper class that is used to processes data by the UrlHandler.
 * It returns errors and can take inn any regular expression from the class it is uesd.
 * We only use this in UrlHandler, but it could also be used elsewhere.
 * @Author Andreas Jacobsen
 * @Date 18. April 2016
 */
public class MatchMaster {

    private String[] patterns;
    private String[] responses;
    private String preText;
    private boolean ifn;

    public MatchMaster(String[] patterns, String[] responses, String preText, boolean includeFaultNumber) {
        this.patterns = patterns;
        this.responses = responses;
        this.preText = preText;
        this.ifn = includeFaultNumber;
    }

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