import java.util.LinkedList;
import java.util.Queue;

public abstract class Product {
    public String name;
    public double buyPrice;
    public double sellPrice;
    public int weight;
    public int stock;
    public LinkedList<Order> orders = new LinkedList<>();

    public Product(String name, double buyPrice, double sellPrice, int weight) {
        this.name = name;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.weight = weight;
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
    //add order to orders FIFO style
    public void addOrder(Order order){
        orders.addLast(order);
    }
}
