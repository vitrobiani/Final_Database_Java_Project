import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Stack;
import java.util.TreeSet;

public class DataBase implements Serializable {
    private Stack<Product.Memento> stack;
    private static DataBase[] _instance = new DataBase[1];
    public TreeSet<Product> products;
    public PairSet countries;

    private DataBase(){
        products = new TreeSet<>();
        stack = new Stack<>();
        countries = new PairSet();
        countries.addPair("USA", 20);
        countries.addPair("Israel", 20);
        countries.addPair("Omerland", 20);
        countries.addPair("France", 20);
        countries.addPair("Spain", 20);
        countries.addPair("Thailand", 20);
        countries.addPair("Canada", 20);
        countries.addPair("Mexico", 20);
    }
    public static DataBase getInstance() {
        if (_instance[0] == null) {
            _instance[0] = new DataBase();
        }
        return _instance[0];
    }

    public TreeSet<Product> getProducts(){
        return products;
    }

    public void setProducts(TreeSet<Product> p){
        this.products = p;
    }

    public PairSet getCountries(){
        return countries;
    }

    public Stack<Product.Memento> getStack(){
        return stack;
    }

    public void setStack(Stack<Product.Memento> s){
        this.stack = s;
    }

    public void addImportTax(String country, int tax){
        countries.addPair(country, tax);
    }

    public void addProductToDB(Product p){
        products.add(p);
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

    public Product findProduct(String code){
        for (Product p: products){
            if (p.getCode().equals(code)) return p;
        }
        return null;
    }

    public void loadDataBase(DataBase ndb){
        _instance[0].setProducts(ndb.getProducts());
        _instance[0].setStack(ndb.getStack());
    }

    public void printAllOrdersOfProduct(String code){
       Product p = findProduct(code);
         if (p == null){
              System.out.println("Product not found");
              return;
         }
         int i=1;
         for(Order o: p.orders){
             System.out.println(i+"."+o);
             System.out.println("profit:"+p.calculateProductProfit()*o.getQuantity());
             i++;
         }

    }
    public void printAllProductsInStore(){
        int i = 1;
        for (Product p : products){
            System.out.print(i + ".");
            p.printProductShort();
            i++;
        }
    }
}
