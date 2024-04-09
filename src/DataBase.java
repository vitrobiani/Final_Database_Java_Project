import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeSet;

public class DataBase {
    private static DataBase[] _instance = new DataBase[1];
    public static TreeSet<Product> products;
    public static LinkedList<Invoice> invoices;
    public static HashMap<String,Integer> importTax;
    public static Creator<Product> ProductCreator;

    private DataBase(){
        products = new TreeSet<>();
        invoices = new LinkedList<>();
        importTax = new HashMap<>();
        ProductCreator = new ProductCreator();
    }
    public static DataBase getInstance() {
        if (_instance[0] == null) {
            _instance[0] = new DataBase();
        }
        return _instance[0];
    }
    public Creator<Product> getProductCreator(){
        return ProductCreator;
    }

    public TreeSet<Product> getProducts(){
        return products;
    }

    public void addImportTax(String country, int tax){
        importTax.put(country,tax);
    }

    public void addProductToDB(PairSet set){
        products.add(ProductCreator.create(set));
    }

    public boolean checkProductCode(String code){
        if (code.length() != 4){
            System.out.println("code must be 4 characters");
            return false;
        }
        for (int i = 0; i < code.length(); i++) {
            if (!Character.isLetterOrDigit(code.charAt(i))){
                System.out.println("code must contain only letters and digits");
                return false;
            }
            if (Character.isLetter(code.charAt(i)) && Character.isLowerCase(code.charAt(i))){
                System.out.println("code must contain only upper case letters");
                return false;
            }
        }
        for (Product p: products){
            if (p.getCode().equals(code)) return false;
        }
        return true;
    }
}
