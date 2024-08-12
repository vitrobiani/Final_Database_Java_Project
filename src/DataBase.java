import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.TreeSet;
import org.postgresql.util.PSQLException;

public class DataBase implements Serializable {
    private static DataBase[] _instance = new DataBase[1];
    public TreeSet<Product> products;
    public PairSet countries;
    public ArrayList<ShippingCompany> companies;

    private DataBase(){
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

    public void initDB() throws SQLException, ClassNotFoundException {
        createTable("ShippingCompanies", shippingCompaniesTable());
        createTable("Customers",customersTable());
        createTable("Products", productsTable());
        createTable("Orders", ordersTable());
        createTable("Invoices", invoiceTable());
    }

    public ResultSet QueryDB(String query)  throws ClassNotFoundException, SQLException {
        Connection conecto = null;
        Class.forName("org.postgresql.Driver");

        try {
            String dbURL = "jdbc:postgresql://localhost:5432/DDOFinalProject";
            conecto = DriverManager.getConnection(dbURL, "postgres", "1234");
        } catch (Exception e) {
            System.out.println("Error!: " + e.getMessage());
        }

        Statement stmt = null;
        ResultSet rs = null;
        try {
            assert conecto != null;
            stmt = conecto.createStatement();
            rs = stmt.executeQuery(query);
        } catch (PSQLException esql) {
            System.out.println(esql.getMessage());
        }

        conecto.close();
        return rs;
    }

    public int UpdateDB(String query)  throws ClassNotFoundException, SQLException {
        Connection conecto = null;
        Class.forName("org.postgresql.Driver");

        try {
            String dbURL = "jdbc:postgresql://localhost:5432/DDOFinalProject";
            conecto = DriverManager.getConnection(dbURL, "postgres", "1234");
        } catch (Exception e) {
            System.out.println("Error!: " + e.getMessage());
        }

        Statement stmt = null;
        int rs = 0;
        try {
            assert conecto != null;
            stmt = conecto.createStatement();
            rs = stmt.executeUpdate(query);
        } catch (PSQLException esql) {
            System.out.println(esql.getMessage());
        }

        System.out.println("Lines Changed: " + rs);
        conecto.close();
        return rs;
    }

    public void createTable(String tableName, ArrayList<Pair> cols){
        StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS "+tableName+"(\n");
        int i = 0;
        for (Pair pair : cols){
            query.append("    "+pair.getKey()+" " + pair.getValue() + ((i == cols.size()-1) ? "\n" : (i == 0) ? " PRIMARY KEY,\n" : ",\n"));
            i++;
        }
        query.append(");");
        String q = query.toString();
        try {
            System.out.println(q);
            UpdateDB(q);
        }catch (ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public boolean addToTable(String tableName, ArrayList<Pair> data){
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
        int lines = 0;
        try {
            lines = UpdateDB(q);
        }catch (ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
            return false;
        }

        return lines > 0;
    }

    public <T> boolean removeFromTable(String tableName, String key ,T constraint){
        StringBuilder query = new StringBuilder("DELETE FROM "+tableName+ '\n');
        if (constraint instanceof Integer) {
            query.append("WHERE " + key + " = " + constraint);
        }
        else if (constraint instanceof String) {
            query.append("WHERE "+ key +" LIKE '" + constraint + "'");
        }
        int lines = 0;
        try {
            lines = UpdateDB(query.toString());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return lines>0;
    }

    public ArrayList<Pair> shippingCompaniesTable() {
        ArrayList<Pair> shippingCompanies = new ArrayList<>();
        shippingCompanies.add(new Pair("name","varchar(50)"));
        shippingCompanies.add(new Pair("contactName","varchar(50)"));
        shippingCompanies.add(new Pair("contactNumber","varchar(50)"));
        shippingCompanies.add(new Pair("regularShippingMult","float"));
        shippingCompanies.add(new Pair("expressShippingMult","float"));
        return shippingCompanies;
    }

    public boolean addShippingCompany(String name, Contact contact, double regularShippingMult, double expressShippingMult) {
        ArrayList<Pair> shippingCompanies = new ArrayList<>();
        shippingCompanies.add(new Pair("name",name));
        shippingCompanies.add(new Pair("contactName",contact.name));
        shippingCompanies.add(new Pair("contactNumber",contact.getPhoneNumber()));
        shippingCompanies.add(new Pair("regularShippingMult",regularShippingMult));
        shippingCompanies.add(new Pair("expressShippingMult",expressShippingMult));
        return addToTable("ShippingCompanies", shippingCompanies);
    }

    public boolean removeShippingCompany(String name){
        return removeFromTable("ShippingCompanies", "name", name);
    }

    public ArrayList<Pair> customersTable(){
        ArrayList<Pair> customers = new ArrayList<>();
        customers.add(new Pair("customerID","SERIAL"));
        customers.add(new Pair("name","varchar(50)"));
        customers.add(new Pair("PhoneNumber","varchar(50)"));
        return customers;
    }

    public boolean addCustomer(String name, String phoneNumber){
        ArrayList<Pair> customers = new ArrayList<>();
        customers.add(new Pair("name", name));
        customers.add(new Pair("PhoneNumber", phoneNumber));
        return addToTable("Customers", customers);
    }

    public boolean removeCustomer(int id){
        return removeFromTable("Customers", "customerid", id);
    }

    public ArrayList<Pair> productsTable(){
        ArrayList<Pair> products = new ArrayList<>();
        products.add(new Pair("code","varchar(10) NOT NULL"));
        products.add(new Pair("name","varchar(50) NOT NULL"));
        products.add(new Pair("buyPrice","float NOT NULL"));
        products.add(new Pair("sellPrice","float NOT NULL"));
        products.add(new Pair("weight","integer NOT NULL"));
        products.add(new Pair("stock","integer NOT NULL"));
        products.add(new Pair("ProductType","varchar(10) NOT NULL"));
        products.add(new Pair("sourceCountry","varchar(50)"));
        products.add(new Pair("ShippingType","varchar(50)"));
        products.add(new Pair("CHECK (ShippingType IN","('express', 'standard', 'both'))"));
        products.add(new Pair("CHECK (ProductType IN","('Website', 'Store', 'Wholesalers'))"));
        return products;
    }

    public ArrayList<Pair> ordersTable(){
        ArrayList<Pair> orders = new ArrayList<>();
        orders.add(new Pair("orderID","SERIAL"));
        orders.add(new Pair("customerID","integer NOT NULL"));
        orders.add(new Pair("quantity","integer NOT NULL"));
        orders.add(new Pair("ProductID","varchar(10)"));
        orders.add(new Pair("FOREIGN KEY (ProductID)", "REFERENCES Products(code)"));
        return orders;
    }

    public ArrayList<Pair> invoiceTable(){
        ArrayList<Pair> invoices = new ArrayList<>();
        invoices.add(new Pair("ProductID","varchar(10)"));
        invoices.add(new Pair("Date","timestamp"));
        invoices.add(new Pair("FOREIGN KEY (ProductID)", "REFERENCES Products(code)"));
        return invoices;
    }
}
