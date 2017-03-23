package grapheme;

import connector.Connector;

import java.util.HashMap;
import java.util.Map;

public class Graphemes {
    private static Map<Character, String> graphemes;
    static {
        graphemes = Connector.getGraphemes();
    }
    public static String getRepresentation(char c){
        return graphemes.get(c);
    }
}
