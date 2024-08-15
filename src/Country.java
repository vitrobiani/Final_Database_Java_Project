public class Country {
    public String countryCode;
    public String name;
    public double tax;

    public Country(String countryCode, String name, double tax){
        this.countryCode = countryCode;
        this.name = name;
        this.tax = tax;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
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

    public String toString(){
        return "Country Code: " + countryCode + ",  Country Name:  " + name + ",  Tax:  " + tax;
    }

}
