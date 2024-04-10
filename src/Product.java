import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Stack;

public abstract class Product implements Serializable, Comparable<Product> {
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
        orders = new LinkedList<>();
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

    //add order to orders FIFO style
    public void addOrder(Order order){
        DataBase.getInstance().getStack().push(createMemento(order));
        orders.addLast(order);
    }

    public double calculateProductProfit(){
        return (sellPrice - buyPrice);
    }

    public void updateStock(int amount){
        stock = amount;
    }

    public static class Memento implements Serializable{
        protected final Product product;
        protected final Order order;
        private Memento(Product product, Order order){
            this.product = product;
            this.order = order;
        }
    }

    public Memento createMemento(Order order){
        return new Memento(this, order);
    }

    public void setMemento(Memento m){
        orders.removeLast();
        stock = m.product.stock;
    }

    public String toString(){
        return "code: " + code + ", name: " + name + "\nbuy price: " + buyPrice + ",sell price: " + sellPrice + "\nweight: " + weight + ", stock: " + stock
                + "\nCurrent Orders placed: " + orders.size();
    }

    public String toStringAllOrders(){
        StringBuilder sb = new StringBuilder();
        sb.append("Product Orders: \n");
        for (Order o: orders){
            sb.append(o.toString()).append("\n");
        }
        return sb.toString();
    }

    public int compareTo(Product o) {
        return code.compareTo(o.getCode());
    }

    public void printProductShort(){
        System.out.println("Name: " + name +"\nCode: " + code + "\nOrders: "+ orders.size());
    }
}
