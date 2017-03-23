package connector;

import java.util.HashMap;
import java.util.Map;

public class Connector {
    private static Map<Character, String> graphemes;
    static {

    }
    public static String getRepresentation(char c){
        return graphemes.get(c);
    }
}
