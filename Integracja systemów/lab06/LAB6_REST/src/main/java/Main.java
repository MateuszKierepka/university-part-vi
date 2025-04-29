import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            //Test działania lokalnego REST API
            String temp_url = "http://localhost/IS_LAB6_REST/cities/read/";
            CityService cityService = new CityService(temp_url);
            List<City> cities = cityService.getCities();
            int i = 1;
            for (City city : cities) {
                System.out.println(city.show());
            }
            // System.out.println("City name: " + recieveddata.getJSONObject(0).get("Name"));
        } catch (Exception e) {
            System.err.println("Wystąpił nieoczekiwany błąd!!! ");
            e.printStackTrace(System.err);
        }
    }
}