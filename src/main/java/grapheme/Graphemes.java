package grapheme;

import connector.Connector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Graphemes {
    private Graphemes(){}
    private static Map<String, String> graphemes;
    static {
        graphemes = Connector.getGraphemes();
    }
    public static String getRepresentation(String c){
        return graphemes.get(c);
    }
}
