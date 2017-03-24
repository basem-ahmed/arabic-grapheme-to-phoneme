package connector;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Connector {
    private Connector() {
    }

    private static final String port = "8080";
    private static final String url = "http://localhost:" + port + "/letters";
    private static Map<String, String> graphemes = new HashMap<>();
    static {
        graphemes.put("ء", "?");
        graphemes.put("ا", "a");
        graphemes.put("ب", "b");
        graphemes.put("ت", "t");
        graphemes.put("ث", "T");
        graphemes.put("ج", "g");
        graphemes.put("خ", "x");
        graphemes.put("ح", "X");
        graphemes.put("د", "d");
        graphemes.put("ذ", "D");
        graphemes.put("ر", "r");
        graphemes.put("ز", "z");
        graphemes.put("س", "s");
        graphemes.put("ش", "S");
        graphemes.put("ص", "s'");
        graphemes.put("ض", "d'");
        graphemes.put("ط", "t'");
        graphemes.put("ظ", "D'");
        graphemes.put("ع", "?'");
        graphemes.put("غ", "G");
        graphemes.put("ف", "f");
        graphemes.put("ق", "q");
        graphemes.put("ك", "k");
        graphemes.put("ل", "l");
        graphemes.put("م", "m");
        graphemes.put("ن", "n");
        graphemes.put("ه", "h");
        graphemes.put("و", "w");
        graphemes.put("ي", "j");
    }
    public static Map<String, String> getGraphemes() {
        return graphemes;
//        try {
//            HttpClient client = new DefaultHttpClient();
//            HttpGet request = new HttpGet(url);
//            HttpResponse response = client.execute(request);
//            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//            String line;
//            StringBuilder builder = new StringBuilder();
//            while ((line = rd.readLine()) != null) {
//                builder.append(line);
//            }
//            JSONObject res = new JSONObject(builder.toString());
//            JSONArray arr = res.getJSONArray("letters");
//            int size = arr.length();
//            for (int i = 0; i < size; i++) {
//                JSONObject grapheme = arr.getJSONObject(i);
//                Character letter = grapheme.getString("letter");
//                String phoneme = grapheme.getString("phoneme");
//                graphemes.put(letter, phoneme);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return graphemes;
    }
}
