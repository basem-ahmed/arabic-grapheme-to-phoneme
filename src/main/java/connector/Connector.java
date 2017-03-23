package connector;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Connector {
    private Connector(){}
    private static final String port = "8080";
    private static final String url = "http://localhost:" + port + "/letters";
    private static Map<Character, String> graphemes;
    public static Map<Character, String> getGraphemes(){
        if(graphemes != null)
            return graphemes;
        try {
            Map<Character, String> graphemes = new HashMap<>();
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                builder.append(line);
            }
            JSONObject res = new JSONObject(builder.toString());
            JSONArray arr = res.getJSONArray("letters");
            int size = arr.length();
            for (int i = 0; i < size; i++) {
                JSONObject grapheme = arr.getJSONObject(i);
                Character letter = grapheme.getString("letter");
                String phoneme = grapheme.getString("phoneme");
                graphemes.put(letter, phoneme);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return graphemes;
    }
}
