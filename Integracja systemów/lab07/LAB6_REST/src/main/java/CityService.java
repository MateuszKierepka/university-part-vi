import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CityService {
    private final String endpoint;

    public CityService(String endpoint) {
        this.endpoint = endpoint;
    }

    public List<City> getCities() throws Exception {
        URL url = new URL(endpoint);
        System.out.println("Wysyłanie zapytania...");
        InputStream is = url.openStream();
        System.out.println("Pobieranie odpowiedzi...");
        String source = new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining("\n"));
        System.out.println("Przetwarzanie danych...");
        JSONObject json = new JSONObject(source);
        JSONArray recieveddata = (JSONArray) json.get("cities");
        List<City> cities = new ArrayList<>();
        recieveddata.forEach(
                (item) -> {
//                    System.out.println("City name: " + ((JSONObject) item).get("Name"));
//                    System.out.println("CountryCode: " + ((JSONObject) item).get("CountryCode"));
//                    System.out.println("District: " + ((JSONObject) item).get("District"));
//                    System.out.println("Population: " + ((JSONObject) item).get("Population"));
//                    System.out.println("----------------------------------");
                    String name = (String) ((JSONObject) item).get("Name");
                    String countryCode = (String) ((JSONObject) item).get("CountryCode");
                    String district = (String) ((JSONObject) item).get("District");
                    int population = (int) ((JSONObject) item).get("Population");
                    cities.add(new City(name, countryCode, district, population));
                }
        );
        return cities;
    }
}
