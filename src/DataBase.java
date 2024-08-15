import java.io.Serializable;
import java.sql.*;
import java.util.*;

import org.postgresql.util.PSQLException;

public class DataBase implements Serializable {
    String[] passwords = {"123123", "1234"};
    public static Statement stmt = null;
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
        if (code.length() > 4){
            System.out.println("code must be 4 characters max");
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
        createTable(TN.COUNTRY.tname(), countriesTable());
        createTable(TN.SHIPPING_COMPANY.tname(), shippingCompaniesTable());
        createTable(TN.CUSTOMER.tname(), customersTable());
        createTable(TN.PRODUCT.tname(), productsTable());
        createTable(TN.ORDER.tname(), ordersTable());
        createTable(TN.INVOICE.tname(), invoiceTable());
    }

    public ResultSet QueryDB(String query)  throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection conecto = null;
        ResultSet rs = null;

        for (int i = 0; i < passwords.length; i++) {
            String password = passwords[i];
            try {
                String dbURL = "jdbc:postgresql://localhost:5432/DDOFinalProject";
                conecto = DriverManager.getConnection(dbURL, "postgres", password);

                assert conecto != null;
                stmt = conecto.createStatement();
                rs = stmt.executeQuery(query);
            } catch (PSQLException esql) {
                sqlExceptionMachine(esql);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        assert conecto != null;
        conecto.close();
        return rs;
    }

    public int UpdateDB(String query)  throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection conecto = null;
        int rs = 0;
        for (int i = 0; i < passwords.length; i++) {
            String password = passwords[i];
            try {
                String dbURL = "jdbc:postgresql://localhost:5432/DDOFinalProject";
                conecto = DriverManager.getConnection(dbURL, "postgres", password);

                stmt = conecto.createStatement();
                rs = stmt.executeUpdate(query);
                ResultSet rsn = stmt.getGeneratedKeys();
            } catch (PSQLException esql) {
                sqlExceptionMachine(esql);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        assert conecto != null;
        conecto.close();
        return rs;
    }

    public void sqlExceptionMachine(PSQLException esql){
        String sqlState = esql.getSQLState();
        if (sqlState != null) {
            switch (sqlState) {
                case "23505": { // Unique constraint violation
                    System.out.println("Constraint Violation");
                    break;
                }case "08001": { // SQLClient unable to establish SQLConnection
                    System.out.println("Connection Problems");
                    break;
                }case "28P01":{
                    break;
                }case "42703":{
                    System.out.println(esql.getMessage());
                    break;
//                } case "23503": {
//                    System.out.println("Object you are to remove still has uses in the system, please remove them first!");
//                    break;
                }default:
                    // Optionally log other errors or rethrow
                    System.err.println("Unhandled SQL State: " + sqlState + " Error: " + esql.getMessage());
            }
        } else {
            // For exceptions without a SQLState
            System.err.println("SQLState not available, error: " + esql.getMessage());
        }
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
        shippingCompanies.add(new Pair(TN.SHIPPING_COMPANY_NAME.tname(), TN.SHIPPING_COMPANY_NAME.type()));
        shippingCompanies.add(new Pair(TN.CONTACT_NAME.tname(), TN.CONTACT_NAME.type()));
        shippingCompanies.add(new Pair(TN.CONTACT_NUMBER.tname(), TN.CONTACT_NUMBER.type()));
        shippingCompanies.add(new Pair(TN.REGULAR_SHIPPING_MULT.tname(), TN.REGULAR_SHIPPING_MULT.type()));
        shippingCompanies.add(new Pair(TN.EXPRESS_SHIPPING_MULT.tname(), TN.EXPRESS_SHIPPING_MULT.type()));
        return shippingCompanies;
    }

    public boolean addShippingCompany(String name, Contact contact, double regularShippingMult, double expressShippingMult) {
        ArrayList<Pair> shippingCompanies = new ArrayList<>();
        shippingCompanies.add(new Pair(TN.SHIPPING_COMPANY_NAME.tname(), name));
        shippingCompanies.add(new Pair(TN.CONTACT_NAME.tname(), contact.getName()));
        shippingCompanies.add(new Pair(TN.CONTACT_NUMBER.tname(), contact.getPhoneNumber()));
        shippingCompanies.add(new Pair(TN.REGULAR_SHIPPING_MULT.tname(), regularShippingMult));
        shippingCompanies.add(new Pair(TN.EXPRESS_SHIPPING_MULT.tname(), expressShippingMult));
        return addToTable(TN.SHIPPING_COMPANY.tname(), shippingCompanies);
    }

    public boolean removeShippingCompany(String name){
        return removeFromTable(TN.SHIPPING_COMPANY.tname(), TN.SHIPPING_COMPANY_NAME.tname(), name);
    }

    public ShippingCompany getShippingCompany(String name){
        try{
            ResultSet rs = QueryDB("SELECT * FROM " + TN.SHIPPING_COMPANY.tname());
            while(rs.next()){
                if (rs.getString(TN.SHIPPING_COMPANY_NAME.tname()).equals(name)){
                    return makeRSShippingCompany(rs);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ShippingCompany makeRSShippingCompany(ResultSet rs) throws SQLException {
        String name = rs.getString(TN.SHIPPING_COMPANY_NAME.tname());
        Contact c = new Contact(rs.getString(TN.CONTACT_NAME.tname()), rs.getString(TN.CONTACT_NUMBER.tname()));
        double rsm = Double.parseDouble(rs.getString(TN.REGULAR_SHIPPING_MULT.tname()));
        double esm = Double.parseDouble(rs.getString(TN.EXPRESS_SHIPPING_MULT.tname()));

        return new ShippingCompany(name, c, rsm, esm);
    }

    public Set<ShippingCompany> getAllShippingCompanies(){
        Set<ShippingCompany> set = new HashSet<>();
        try{
            ResultSet rs = QueryDB("SELECT * FROM " + TN.SHIPPING_COMPANY.tname());
            while(rs.next()){
                set.add(makeRSShippingCompany(rs));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return set;
    }

    public boolean isThereAShippingCompany(){
        try{
            ResultSet rs = QueryDB("SELECT * FROM " + TN.SHIPPING_COMPANY.tname());
            int i = 0;
            while (rs.next()) i++;
            if (i > 0) return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public ArrayList<Pair> countriesTable(){
        ArrayList<Pair> countries = new ArrayList<>();
        countries.add(new Pair(TN.COUNTRY_CODE.tname(), TN.COUNTRY_CODE.type()));
        countries.add(new Pair(TN.COUNTRY_NAME.tname(), TN.COUNTRY_NAME.type()));
        countries.add(new Pair(TN.COUNTRY_TAX.tname(), TN.COUNTRY_TAX.type()));
        return countries;
    }

    public boolean addCountry(String code, String name, double tax){
        ArrayList<Pair> country = new ArrayList<>();
        country.add(new Pair(TN.COUNTRY_CODE.tname(), code));
        country.add(new Pair(TN.COUNTRY_NAME.tname(), name ));
        country.add(new Pair(TN.COUNTRY_TAX.tname(), tax));
        return addToTable(TN.COUNTRY.tname(), country);
    }

    public Country makeRSCountry(ResultSet rs) throws SQLException {
        String code = rs.getString(TN.COUNTRY_CODE.tname());
        String name = rs.getString(TN.COUNTRY_NAME.tname());
        double tax = rs.getDouble(TN.COUNTRY_TAX.tname());

        return new Country(code, name, tax);
    }

    public Set<Country> getAllCountries(){
        Set<Country> countries = new HashSet<>();
        try{
            ResultSet rs = QueryDB("SELECT * FROM " + TN.COUNTRY.tname());
            while (rs.next()){
                countries.add(makeRSCountry(rs));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return countries;
    }

    public Country getCountry(String code){
        Country c = null;
        try{
            ResultSet rs = QueryDB("SELECT * FROM " + TN.COUNTRY.tname() + " WHERE " + TN.COUNTRY_CODE.tname() + " LIKE '" + code + "'");
            while (rs.next()){
                c = makeRSCountry(rs);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }

    public ArrayList<Pair> customersTable(){
        ArrayList<Pair> customers = new ArrayList<>();
        customers.add(new Pair(TN.CUSTOMER_PHONE.tname(), TN.CUSTOMER_PHONE.type()));
        customers.add(new Pair(TN.CUSTOMER_NAME.tname(), TN.CUSTOMER_NAME.type()));
        return customers;
    }

    public boolean addCustomer(String name, String phoneNumber){
        ArrayList<Pair> customers = new ArrayList<>();
        customers.add(new Pair(TN.CUSTOMER_NAME.tname(), name));
        customers.add(new Pair(TN.CUSTOMER_PHONE.tname(), phoneNumber));
        return addToTable("Customers", customers);
    }

    public boolean removeCustomer(int id){
        return removeFromTable(TN.CUSTOMER.tname(), TN.CUSTOMER_PHONE.tname(), id);
    }

    public Customer getCustomer(String phoneNumber){
        Customer c = null;
        try{
            ResultSet rs = QueryDB("SELECT * FROM Customers");
            while (rs.next()){
                if (rs.getString(TN.CUSTOMER_PHONE.tname()).equals(phoneNumber)){
                    c = makeRSCustomer(rs);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return c;
    }

    public Customer makeRSCustomer(ResultSet rs) throws SQLException {
        String name = rs.getString(TN.CUSTOMER_NAME.tname());
        String phoneNumber = rs.getString(TN.CUSTOMER_PHONE.tname());
        return new Customer(name, phoneNumber);
    }

    public ArrayList<Pair> productsTable(){
        ArrayList<Pair> products = new ArrayList<>();
        products.add(new Pair(TN.PRODUCT_CODE.tname(), TN.PRODUCT_CODE.type()));
        products.add(new Pair(TN.PRODUCT_NAME.tname(), TN.PRODUCT_NAME.type()));
        products.add(new Pair(TN.PRODUCT_BUY_PRICE.tname(), TN.PRODUCT_BUY_PRICE.type()));
        products.add(new Pair(TN.PRODUCT_SELL_PRICE.tname(), TN.PRODUCT_SELL_PRICE.type()));
        products.add(new Pair(TN.PRODUCT_WEIGHT.tname(), TN.PRODUCT_WEIGHT.type()));
        products.add(new Pair(TN.PRODUCT_STOCK.tname(), TN.PRODUCT_STOCK.type()));
        products.add(new Pair(TN.PRODUCT_TYPE.tname(), TN.PRODUCT_TYPE.type()));
        products.add(new Pair(TN.PRODUCT_COUNTRY.tname(), TN.PRODUCT_COUNTRY.type()));
        products.add(new Pair(TN.PRODUCT_SHIPPING_TYPE.tname(), TN.PRODUCT_SHIPPING_TYPE.type()));
        products.add(new Pair("CHECK ("+TN.PRODUCT_TYPE.tname()+" IN","('Website', 'Store', 'Wholesalers'))"));
        products.add(new Pair("FOREIGN KEY ("+TN.PRODUCT_COUNTRY.tname()+")", "REFERENCES "+TN.COUNTRY.tname()+"("+TN.COUNTRY_CODE.tname()+")"));
//        TODO
//        products.add(new Pair("CHECK (ShippingType IN","('express', 'standard', 'both'))"));

        return products;
    }

    public boolean addProduct(String code, String name, double buyPrice, double sellPrice, int weight, int stock, String productType, String sourceCountry, String shippingType){
        ArrayList<Pair> products = new ArrayList<>();
        products.add(new Pair(TN.PRODUCT_CODE.tname(), code));
        products.add(new Pair(TN.PRODUCT_NAME.tname(), name));
        products.add(new Pair(TN.PRODUCT_BUY_PRICE.tname(), buyPrice));
        products.add(new Pair(TN.PRODUCT_SELL_PRICE.tname(), sellPrice));
        products.add(new Pair(TN.PRODUCT_WEIGHT.tname(), weight));
        products.add(new Pair(TN.PRODUCT_STOCK.tname(), stock));
        products.add(new Pair(TN.PRODUCT_TYPE.tname(), productType));
        products.add(new Pair(TN.PRODUCT_COUNTRY.tname(), sourceCountry));
        products.add(new Pair(TN.PRODUCT_SHIPPING_TYPE.tname(), shippingType));
        return addToTable(TN.PRODUCT.tname(), products);
    }

    public boolean removeProduct(String code){
        return removeFromTable(TN.PRODUCT.tname(), TN.PRODUCT_CODE.tname(), code);
    }

    public boolean updateProductStock(String code, int newStock){
        try{
            UpdateDB("UPDATE "+ TN.PRODUCT.tname() + "\n" +
                    "SET " + TN.PRODUCT_STOCK.tname() +" = " + newStock +"\n" +
                    "WHERE " + TN.PRODUCT_CODE.tname() +" LIKE '" + code + "';");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public Product getProduct(String code){
        Product p = null;
        if (code.length() > 4) return null;
        ResultSet rs = null;
        try {
            rs = QueryDB("SELECT * FROM " + TN.PRODUCT.tname() + " LEFT JOIN " + TN.COUNTRY.tname() + " ON (" +  TN.PRODUCT_COUNTRY.tname() + " = " + TN.COUNTRY_CODE.tname()+")" +
                    "WHERE "+ TN.PRODUCT_CODE.tname() + " LIKE '" + code +"';");
            while (rs.next()){
                p = makeRSProduct(rs);
                return p;
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return p;
    }

    public Product makeRSProduct(ResultSet rs) throws SQLException {
        PairSet set = new PairSet();
        set.addPair("name",rs.getString(TN.PRODUCT_NAME.tname()));
        set.addPair("code", rs.getString(TN.PRODUCT_CODE.tname()));
        set.addPair("buyPrice",Double.parseDouble(rs.getString(TN.PRODUCT_BUY_PRICE.tname())));
        set.addPair("sellPrice",Double.parseDouble(rs.getString(TN.PRODUCT_SELL_PRICE.tname())));
        set.addPair("weight", Integer.parseInt(rs.getString(TN.PRODUCT_WEIGHT.tname())));
        set.addPair("stock", Integer.parseInt(rs.getString(TN.PRODUCT_STOCK.tname())));
        if (rs.getString(TN.PRODUCT_SHIPPING_TYPE.tname()) != null) {
            ShippingType st;
            if (rs.getString(TN.PRODUCT_SHIPPING_TYPE.tname()).equals("Standard")) {
                st = ShippingType.STANDARD;
            } else if (rs.getString(TN.PRODUCT_SHIPPING_TYPE.tname()).equals("Express")) {
                st = ShippingType.EXPRESS;
            } else {
                st = ShippingType.ALL_SHIPPING;
            }
            set.addPair("ShippingType", st);
        }
        if (rs.getString(TN.PRODUCT_TYPE.tname()) != null) {
            ProductType pt;
            if (rs.getString(TN.PRODUCT_TYPE.tname()).equals("Store")) {
                pt = ProductType.SOLD_IN_STORE;
            } else if (rs.getString(TN.PRODUCT_TYPE.tname()).equals("Website")) {
                pt = ProductType.SOLD_THROUGH_WEBSITE;
            } else {
                pt = ProductType.SOLD_TO_WHOLESALERS;
            }
            set.addPair("ProductType", pt);
        }
        if (rs.getString(TN.PRODUCT_COUNTRY.tname()) != null){
            set.addPair("srcCountry", makeRSCountry(rs));
        }
        Creator<Product> creator = new ProductCreator();

        return creator.create(set);
    }

    public Set<Product> getAllProducts(){
        Set<Product> productsSet = new HashSet<>();
        ResultSet rs = null;
        try {
            rs = QueryDB("SELECT * FROM " + TN.PRODUCT.tname() + " LEFT JOIN " + TN.COUNTRY.tname() + " ON (" + TN.COUNTRY_CODE.tname() + " = " + TN.PRODUCT_COUNTRY.tname() +");");
            while (rs.next()) productsSet.add(makeRSProduct(rs));
        } catch (ClassNotFoundException | SQLException esql){
            System.out.println(esql.getMessage());
        }
        return productsSet;
    }

    public ArrayList<Pair> ordersTable(){
        ArrayList<Pair> orders = new ArrayList<>();
        orders.add(new Pair(TN.ORDER_ID.tname(), TN.ORDER_ID.type()));
        orders.add(new Pair(TN.ORDER_CUSTOMER.tname(), TN.ORDER_CUSTOMER.type()));
        orders.add(new Pair(TN.ORDER_QUANTITY.tname(), TN.ORDER_QUANTITY.type()));
        orders.add(new Pair(TN.ORDER_PRODUCT.tname(), TN.ORDER_PRODUCT.type()));
        orders.add(new Pair(TN.ORDER_SHIPPING_TYPE.tname(), TN.ORDER_SHIPPING_COMPANY.type()));
        orders.add(new Pair(TN.ORDER_SHIPPING_COMPANY.tname(), TN.ORDER_SHIPPING_COMPANY.type()));
        orders.add(new Pair("FOREIGN KEY ("+TN.ORDER_CUSTOMER.tname()+")", "REFERENCES "+TN.CUSTOMER.tname()+"("+TN.CUSTOMER_PHONE.tname()+")"));
        orders.add(new Pair("FOREIGN KEY ("+TN.ORDER_PRODUCT.tname()+")", "REFERENCES "+TN.PRODUCT.tname()+"("+TN.PRODUCT_CODE.tname()+")"));
        orders.add(new Pair("FOREIGN KEY ("+TN.ORDER_SHIPPING_COMPANY.tname()+")", "REFERENCES "+TN.SHIPPING_COMPANY.tname()+"("+TN.SHIPPING_COMPANY_NAME.tname()+")"));
        return orders;
    }

    public boolean addOrder(Customer customer, int quantity, Product p,  String shippingType, String shippingCompany){
        ArrayList<Pair> orders = new ArrayList<>();
        orders.add(new Pair(TN.ORDER_CUSTOMER.tname(), customer.getPhoneNumber()));
        orders.add(new Pair(TN.ORDER_QUANTITY.tname(), quantity));
        orders.add(new Pair(TN.ORDER_PRODUCT.tname(), "'"+p.code+"'"));
        orders.add(new Pair(TN.ORDER_SHIPPING_TYPE.tname(),shippingType ));
        orders.add(new Pair(TN.ORDER_SHIPPING_COMPANY.tname(),shippingCompany));
        updateProductStock(p.code, p.stock);
        return addToTable(TN.ORDER.tname(), orders);
    }

    public boolean addOrder(Order order){
        ArrayList<Pair> orders = new ArrayList<>();
        orders.add(new Pair(TN.ORDER_CUSTOMER.tname(), order.customer.getPhoneNumber()));
        orders.add(new Pair(TN.ORDER_QUANTITY.tname(), order.quantity));
        orders.add(new Pair(TN.ORDER_PRODUCT.tname(), "'"+order.product.code+"'"));
        if (order.getClass().equals(OrderThroughWebsite.class)) {
            OrderThroughWebsite ord = (OrderThroughWebsite) order;
            orders.add(new Pair(TN.ORDER_SHIPPING_TYPE.tname(), ord.shippingType.sqlOpt));
            orders.add(new Pair(TN.ORDER_SHIPPING_COMPANY.tname(), "'"+ord.setShippingCompany().toString()+"'"));
        }else {
            orders.add(new Pair(TN.ORDER_SHIPPING_TYPE.tname(), null));
            orders.add(new Pair(TN.ORDER_SHIPPING_COMPANY.tname(), null));
        }
        updateProductStock(order.product.code, order.product.stock);
        return addToTable(TN.ORDER.tname(), orders);
    }

    public boolean removeOrder(int id){
        removeInvoice(id);
        return removeFromTable(TN.ORDER.tname(), TN.ORDER_ID.tname(), id);
    }

    public void printOrders(){
        try{
            ResultSet rs = QueryDB("SELECT * FROM " + TN.ORDER.tname());
            while (rs.next()){
                StringBuilder sb = new StringBuilder();
                sb.append("Order ID: " + rs.getInt(TN.ORDER_ID.tname()) + "  Customer's phone number: " +
                        rs.getString(TN.ORDER_CUSTOMER.tname()) + "\nQuantity: " + rs.getString(TN.ORDER_QUANTITY.tname()) +
                        "  Product's code: " + rs.getString(TN.ORDER_PRODUCT.tname()) + "\n");
                if (rs.getString(TN.ORDER_SHIPPING_TYPE.tname()) != null){
                    sb.append("Shipping Type: " + rs.getString(TN.ORDER_SHIPPING_TYPE.tname()) +
                            "  Shipping Company: " + rs.getString(TN.ORDER_SHIPPING_COMPANY.tname())+"\n");
                }
                System.out.println(sb.toString());
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getLastOrderID(){
        int id = -1;
        try {
            ResultSet rs = QueryDB("SELECT MAX("+TN.ORDER_ID.tname()+") AS max_id FROM " + TN.ORDER.tname());
            rs.next();
            id = rs.getInt("max_id");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    public Order getOrder(int id){
        Order order = null;
        try{
            ResultSet rs = QueryDB("SELECT * FROM ("+ TN.ORDER.tname() + " JOIN "+ TN.PRODUCT.tname() +" ON (" + TN.ORDER_PRODUCT.tname() + " = " + TN.PRODUCT_CODE.tname() + ")) JOIN "+ TN.CUSTOMER.tname() +" ON "+ TN.CUSTOMER_PHONE.tname() + " = " + TN.ORDER_CUSTOMER.tname() +";");
            while (rs.next()){
                if (rs.getInt(TN.ORDER_ID.tname()) == id){
                    PairSet set = new PairSet();
                    Product p = makeRSProduct(rs);
                    set.addPair("Product", p);
                    set.addPair("Customer", makeRSCustomer(rs));
                    set.addPair("Quantity", rs.getInt(TN.ORDER_QUANTITY.tname()));
                    set.addPair("ProductClass", p.getClass().getSimpleName());
                    Creator<Order> orderCreator = new OrderCreator();
                    order = orderCreator.create(set);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return order;
    }

    public Set<Order> getAllOrdersProducts(){
        Set<Order> orders = new HashSet<>();
        try{
            ResultSet rs = QueryDB("SELECT * FROM ("+ TN.ORDER.tname() + " JOIN "+ TN.PRODUCT.tname() +" ON (" + TN.ORDER_PRODUCT.tname() + " = " + TN.PRODUCT_CODE.tname() + ")) JOIN "+ TN.CUSTOMER.tname() +" ON "+ TN.CUSTOMER_PHONE.tname() + " = " + TN.ORDER_CUSTOMER.tname() +";");
            while (rs.next()){
                Customer c = makeRSCustomer(rs);
                Product p = makeRSProduct(rs);
                PairSet set = new PairSet();
                set.addPair("Product", p);
                set.addPair("Customer", c);
                set.addPair("Quantity", rs.getInt(TN.ORDER_QUANTITY.tname()));
                set.addPair("ProductClass", p.getClass().getSimpleName());
                Creator<Order> orderCreator = new OrderCreator();
                orders.add(orderCreator.create(set));
            }

        } catch (SQLException | ClassNotFoundException  e) {
            System.out.println(e.getMessage());
        }
        return orders;
    }

    public Set<Order> getAllProductsOrders(String pCode){
        Set<Order> orders = new HashSet<>();
        try{
            ResultSet rs = QueryDB("SELECT * FROM ("+ TN.ORDER.tname() + " JOIN "+ TN.PRODUCT.tname() +" ON (" + TN.ORDER_PRODUCT.tname() + " = " + TN.PRODUCT_CODE.tname() + ")) JOIN "+ TN.CUSTOMER.tname() +" ON "+ TN.CUSTOMER_PHONE.tname() + " = " + TN.ORDER_CUSTOMER.tname() +"" +
                    "\nWHERE "+ TN.ORDER_PRODUCT.tname() +" LIKE '"+ pCode + "';");
            while (rs.next()){
                Customer c = makeRSCustomer(rs);
                Product p = makeRSProduct(rs);
                PairSet set = new PairSet();
                set.addPair("Product", p);
                set.addPair("Customer", c);
                set.addPair("Quantity", rs.getInt(TN.ORDER_QUANTITY.tname()));
                set.addPair("ProductClass", p.getClass().getSimpleName());
                if (rs.getString(TN.ORDER_SHIPPING_TYPE.tname()) != null && rs.getString(TN.ORDER_SHIPPING_TYPE.tname()).equals("standard"))
                    set.addPair("ShippingType", ShippingType.STANDARD);
                else if (rs.getString(TN.ORDER_SHIPPING_TYPE.tname()) != null && rs.getString(TN.ORDER_SHIPPING_TYPE.tname()).equals("express"))
                    set.addPair("ShippingType", ShippingType.EXPRESS);

                Creator<Order> orderCreator = new OrderCreator();
                orders.add(orderCreator.create(set));
            }

        } catch (SQLException | ClassNotFoundException  e) {
            System.out.println(e.getMessage());
        }
        return orders;
    }

    public ArrayList<Pair> invoiceTable(){
        ArrayList<Pair> invoices = new ArrayList<>();
        invoices.add(new Pair(TN.INVOICE_ID.tname(), TN.INVOICE_ID.type()));
        invoices.add(new Pair(TN.INVOICE_ORDER.tname(), TN.INVOICE_ORDER.type()));
        invoices.add(new Pair(TN.INVOICE_DATE.tname(), TN.INVOICE_DATE.type()));
        invoices.add(new Pair("FOREIGN KEY ("+TN.INVOICE_ORDER.tname()+")", "REFERENCES "+TN.ORDER.tname()+"("+TN.ORDER_ID.tname()+")"));
        return invoices;
    }

    public boolean addInvoice(int OrderID){
        ArrayList<Pair> invoices = new ArrayList<>();
        invoices.add(new Pair(TN.INVOICE_ORDER.tname(), OrderID));
        invoices.add(new Pair(TN.INVOICE_DATE.tname(), "CURRENT_TIMESTAMP"));
        return addToTable(TN.INVOICE.tname(), invoices);
    }

    public boolean removeInvoice(int id){
        return removeFromTable(TN.INVOICE.tname(), TN.INVOICE_ORDER.tname(), id);
    }
}
