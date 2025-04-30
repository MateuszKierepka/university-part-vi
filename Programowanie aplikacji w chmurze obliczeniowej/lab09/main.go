package main

// potrzebne importy
import (
    "os"
    "encoding/json"
    "fmt"
    "html/template"
    "io"
    "log"
    "net/http"
    "net/url"
    "strings"
    "time"
)

// slownik z lokalizacjami
var locations = map[string][]string{
    "Polska": {"Warszawa", "Szczecin", "Lublin"},
    "Niemcy": {"Berlin", "Dortmund", "Hamburg"},
}

// struktura odpowiedzi API
type WeatherResponse struct {
    Location struct {
        Name string `json:"name"`
        Country string `json:"country"`
        Localtime string `json:"localtime"`
    } `json:"location"`
    Current struct {
        TempC float64 `json:"temp_c"`
        Condition struct {
            Text string `json:"text"`
            Icon string `json:"icon"`
        } `json:"condition"`
        FeelslikeC float64 `json:"feelslike_c"`
        Humidity int `json:"humidity"`
        WindKph float64 `json:"wind_kph"`
        WindDir string `json:"wind_dir"`
        PressureMb float64 `json:"pressure_mb"`
        VisKm float64 `json:"vis_km"`
        UV float64 `json:"uv"`
    } `json:"current"`
}

func main() {
    log.Printf("Aplikacja uruchomiona: %s", time.Now().Format("2006-01-02 15:04:05"))
    log.Println("Autor: Mateusz Kierepka")
    log.Println("Nasłuchiwanie na porcie TCP: 8080")

    // rejestracja handlerow dla endpointow
    http.HandleFunc("/", indexHandler)
    http.HandleFunc("/cities/", citiesHandler)
    http.HandleFunc("/weather", weatherHandler)

    log.Fatal(http.ListenAndServe(":8080", nil))
}

// handler strony glownej
func indexHandler(w http.ResponseWriter, r *http.Request) {
    // wczytanie szablonu HTML
    tmpl, err := template.ParseFiles("templates/index.html")
    if err != nil {
        http.Error(w, "blad szablonu", http.StatusInternalServerError)
        log.Println("blad szablonu: ", err)
        return
    }

    // przygotowanie listy krajow do wyswietlenia w liscie rozwijanej
    countries := make([]string, 0, len(locations))
    for country := range locations {
        countries = append(countries, country)
    }

    // przygotowanie danych dla szablonu
    data := map[string]interface{}{
        "locations": countries,
    }

    // wygenerowanie szablonu HTML
    w.Header().Set("Content-Type", "text/html; charset=utf-8")
    if err := tmpl.Execute(w, data); err != nil {
        http.Error(w, "blad generowania strony", http.StatusInternalServerError)
        log.Println("blad generowania: ", err)
    }
}

// handler dla zapytania o miasta
func citiesHandler(w http.ResponseWriter, r *http.Request) {
    country := strings.TrimPrefix(r.URL.Path, "/cities/")
    // pobranie miast dla danego kraju
    cities, ok := locations[country]
    if !ok {
        http.Error(w, "brak kraju w slowniku", http.StatusBadRequest)
        return
    }

    // zwrocenie listy miast w formacie JSON
    w.Header().Set("Content-Type", "application/json")
    json.NewEncoder(w).Encode(map[string][]string{"cities": cities})
}

// handler dla zapytania o pogode
func weatherHandler(w http.ResponseWriter, r *http.Request) {
    w.Header().Set("Content-Type", "application/json")

    if r.Method != http.MethodPost {
        w.WriteHeader(http.StatusMethodNotAllowed)
        json.NewEncoder(w).Encode(map[string]string{"error": "blad - zla metoda http"})
        return
    }

    // parsowanie formularza
    if err := r.ParseForm(); err != nil {
        log.Printf("blad parsowania: %v", err)
        w.WriteHeader(http.StatusBadRequest)
        json.NewEncoder(w).Encode(map[string]string{"error": "nieprawidlowe dane formularza", "details": err.Error()})
        return
    }

    // pobranie danych z formularza (kraj i miasto)
    city := r.FormValue("city")
    country := r.FormValue("country")

    // walidacja danych
    if city == "" || country == "" {
        w.WriteHeader(http.StatusBadRequest)
        json.NewEncoder(w).Encode(map[string]string{"error": "brak danych wejsciowych", "details": fmt.Sprintf("city=%s, country=%s", city, country)})
        return
    }

    encodedCity := url.QueryEscape(city)
    apiKey := os.Getenv("API_KEY")
    if apiKey == "" {
        log.Println("brak klucza API w zmiennych środowiskowych")
        w.WriteHeader(http.StatusInternalServerError)
        json.NewEncoder(w).Encode(map[string]string{"error": "brak klucza API"})
        return
    }
    apiURL := fmt.Sprintf("http://api.weatherapi.com/v1/current.json?key=%s&q=%s", apiKey, encodedCity)

    // wyslanie zapytania do API
    resp, err := http.Get(apiURL)
    if err != nil {
        log.Printf("blad podczas zapytania do API: %v", err)
        w.WriteHeader(http.StatusInternalServerError)
        json.NewEncoder(w).Encode(map[string]string{"error": "blad pobierania danych", "details": err.Error()})
        return
    }
    // zamkniecie odpowiedzi
    defer resp.Body.Close()

    // sprawdzenie statusu odpowiedzi
    if resp.StatusCode != http.StatusOK {
        log.Printf("api zwrocilo nieprawdilowy status: %d", resp.StatusCode)
        
        body, _ := io.ReadAll(resp.Body)
        log.Printf(string(body))
        
        w.WriteHeader(http.StatusInternalServerError)
        json.NewEncoder(w).Encode(map[string]string{"error": "nie udalo sie pobrac pogody", "details": fmt.Sprintf("Status: %d", resp.StatusCode)})
        return
    }

    // dekodowanie odpowiedzi
    var weather WeatherResponse
    if err := json.NewDecoder(resp.Body).Decode(&weather); err != nil {
        log.Printf("blad dekodowania odpowiedzi API: %v", err)
        w.WriteHeader(http.StatusInternalServerError)
        json.NewEncoder(w).Encode(map[string]string{"error": "blad dekodowania danych pogodowych", "details": err.Error()})
        return
    }
    // zwrocenie danych pogodowych
    json.NewEncoder(w).Encode(weather)
}