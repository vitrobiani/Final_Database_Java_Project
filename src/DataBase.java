import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.TreeSet;
import org.postgresql.util.PSQLException;

public class DataBase implements Serializable {
    private Stack<Product.Memento> stack;
    private static DataBase[] _instance = new DataBase[1];
    public TreeSet<Product> products;
    public PairSet countries;
    public ArrayList<ShippingCompany> companies;

    private DataBase(){
        stack = new Stack<>();

        products = new TreeSet<>();
        companies = new ArrayList<>();
        countries = new PairSet();
    }
    public static DataBase getInstance() {
        if (_instance[0] == null) {
            _instance[0] = new DataBase();
        }
        return _instance[0];
    }

    public void addCompanies() throws SQLException, ClassNotFoundException {
        initDB();
    }

    public void addCountries(){
        countries.addPair("USA", 20);
        countries.addPair("Israel", 20);
        countries.addPair("Omerland", 20);
        countries.addPair("France", 20);
        countries.addPair("Spain", 20);
        countries.addPair("Thailand", 20);
        countries.addPair("Canada", 20);
        countries.addPair("Mexico", 20);
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

    public void AllOrdersOfProduct(String code){
       Product p = findProduct(code);
         int i=1;
         for(Order o: p.orders){
             System.out.println(i+"."+o);
             System.out.println("profit:"+p.calculateProductProfit()*o.getQuantity());
             i++;
         }

    }

    public String AllProductsInStoreToString(){
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Product p : products){
            sb.append(i + ". " + p.ProductShortToString() + "\n");
            i++;
        }
        return sb.toString();
    }

    Boolean QueryDB(String query)  throws ClassNotFoundException, SQLException {
        Connection conecto = null;
        Class.forName("org.postgresql.Driver");

        try {
            String dbURL = "jdbc:postgresql://localhost:5432/DDOFinalProject";
            conecto = DriverManager.getConnection(dbURL, "postgres", "123123");
        } catch (Exception e) {
            System.out.println("Error!: " + e.getMessage());
            return false;
        }

        Statement stmt = null;

        try {
            assert conecto != null;
            stmt = conecto.createStatement();
            ResultSet rs = stmt.executeQuery(query);

        } catch (PSQLException esql) {
            System.out.println(esql.getMessage());
            System.out.println(query);
            return false;
        }

        conecto.close();
        return true;
    }

    public void initDB() throws SQLException, ClassNotFoundException {
        createTable("ShippingCompanies", shippingCompaniesTable());
        addShippingCompany("\'UPS\'", new Contact("omer", "555666", 1),1.2, 1.5);
    }

    public void createTable(String tableName, ArrayList<Pair> cols){
        StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS "+tableName+"(\n");
        int i = 0;
        for (Pair pair : cols){
            query.append("    "+pair.getKey()+" " + pair.getValue() + ((i == cols.size()-1) ? "\n" : ",\n"));
            i++;
        }
        query.append(");");
        String q = query.toString();
        try {
            QueryDB(q);
        }catch (ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void addToTable(String tableName, ArrayList<Pair> data){
        StringBuilder query = new StringBuilder("INSERT INTO "+tableName+" (");

        for (int i = 0; i < data.size(); i++){
            query.append(data.get(i).getKey()+ ((i == data.size()-1) ? "" : ", "));
        }
        query.append(")\nVALUES(");
        for (int i = 0; i < data.size(); i++){
            query.append(data.get(i).getValue()+ ((i == data.size()-1) ? "" : ", "));
        }
        query.append(");");
        String q = query.toString();
        try {
            QueryDB(q);
        }catch (ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Pair> shippingCompaniesTable() {
        ArrayList<Pair> shippingCompanies = new ArrayList<>();
        shippingCompanies.add(new Pair("name","varchar(50)"));
        shippingCompanies.add(new Pair("contact","integer"));
        shippingCompanies.add(new Pair("regularShippingMult","float"));
        shippingCompanies.add(new Pair("expressShippingMult","float"));
        return shippingCompanies;
    }

    public ArrayList<Pair> ShippingCompaniesTable(String name, Contact contact, double regularShippingMult, double expressShippingMult){
        ArrayList<Pair> shippingCompanies = new ArrayList<>();
        shippingCompanies.add(new Pair("name",name));
        shippingCompanies.add(new Pair("contact",""+contact.EmployeeNumber));
        shippingCompanies.add(new Pair("regularShippingMult",""+regularShippingMult));
        shippingCompanies.add(new Pair("expressShippingMult",""+expressShippingMult));
        return shippingCompanies;
    }

    public void addShippingCompany(String name, Contact contact, double regularShippingMult, double expressShippingMult) {
        ArrayList<Pair> shippingCompanies = new ArrayList<>();
        shippingCompanies.add(new Pair("name",name));
        shippingCompanies.add(new Pair("contact",contact.EmployeeNumber));
        shippingCompanies.add(new Pair("regularShippingMult",regularShippingMult));
        shippingCompanies.add(new Pair("expressShippingMult",expressShippingMult));
        addToTable("ShippingCompanies", shippingCompanies);
    }
}
