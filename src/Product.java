import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Stack;

public abstract class Product implements Serializable, Comparable<Product> {
    private DataBase db = DataBase.getInstance();
    public String code;
    public String name;
    public double buyPrice;
    public double sellPrice;
    public int weight;
    public int stock;
    public LinkedList<Order> orders;

    public Product(String code, String name, double buyPrice, double sellPrice, int weight) {
        this.code = code;
        this.name = name;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.weight = weight;
        this.stock = 0;
    }

    public Product(String code, String name, double buyPrice, double sellPrice, int weight, int stock) {
        this.code = code;
        this.name = name;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.weight = weight;
        this.stock = stock;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getStock() {
        return stock;
    }

    public double calculateProductProfit(){
        return (sellPrice - buyPrice);
    }

    public void updateStock(int amount){
        stock = amount;
    }

    public double calculateTotalProductProfit(){
        double sum = 0;
//        TODO after we implement order
        return sum;
    }

    public String toString(){
        return "code: " + code + ", name: " + name + "\nbuy price: " + buyPrice + " ,sell price: " + sellPrice + "\nweight: " + weight + ", stock: " + stock
                + "\nCurrent Orders placed: " + orders.size();
    }

    public String toStringAllOrders(){
        StringBuilder sb = new StringBuilder();
        sb.append("Product Orders: \n");
        for (Order o: orders){
            sb.append(o.orderDetails()).append("\n");
        }
        return sb.toString();
    }

    public int compareTo(Product o) {
        return code.compareTo(o.getCode());
    }

    public String ProductShortToString(){
        return "Name: " + name +"  Code: " + code + "  Stock: " + stock + "  Orders: " + orders.size() ;
    }
}
