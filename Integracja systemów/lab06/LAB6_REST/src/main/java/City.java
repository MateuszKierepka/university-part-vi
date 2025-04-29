public class City {
    private String name;
    private String countryCode;
    private String district;
    private int population;

    public City(String name, String countryCode, String district, int population) {
        this.name = name;
        this.countryCode = countryCode;
        this.district = district;
        this.population = population;
    }

    public String show() {
        return "City name: " + name + "\n"
                + "CountryCode: " + countryCode + "\n"
                + "District: " + district + "\n"
                + "Population: " + population + "\n"
                + "----------------------------------";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
