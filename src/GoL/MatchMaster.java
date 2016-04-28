package GoL;

import java.util.StringJoiner;
/**
 * Created by Andreas Jacobsen on 27/04/2016.
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