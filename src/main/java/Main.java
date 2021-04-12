import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;


public class Main {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static void main(String[] args) throws IOException {
        JSONObject JSON = readJsonFromUrl("https://www.cbr-xml-daily.ru/daily_json.js");
        JSONObject valutesJSON = JSON.getJSONObject("Valute");

        ArrayList<Valute> valutes = new ArrayList<>(valutesJSON.length());
        for (String key : valutesJSON.keySet()) {
            JSONObject valuteJSON = valutesJSON.getJSONObject(key);
            String name = (String) valuteJSON.get("Name");
            double value = (double) valuteJSON.get("Value");
            double previous = (double) valuteJSON.get("Previous");
            Valute valute = new Valute(name, Math.abs(value - previous));
            valutes.add(valute);
        }


        valutes.sort(new Comparator<Valute>() {
            @Override
            public int compare(Valute o1, Valute o2) {
                double o1diff = o1.getDifference();
                double o2diff = o2.getDifference();
                if (o1diff == o2diff) return 0;
                return o1diff < o2diff ? 1 : -1;
            }
        });
        for (int i = 0; i < 5; i++) {
            System.out.println(valutes.get(i).getName());
        }
    }
}
