import java.io.Serial;
import java.io.Serializable;
import java.util.Stack;

public abstract class Order implements Serializable {
    Customer customer;
    int quantity;
    Product product;

    public Order(Customer customer, int quantity, Product product) {
        this.customer = customer;
        this.quantity = quantity;
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public String toString(){
        return "Order for " + customer.getName() + " of " + quantity + " " + product.getName();
    }

    public double calculateOrderProfit(){
        return product.calculateProductProfit() * quantity;
    }

    public double getOrderPrice(){
        return product.getSellPrice()*quantity;
    }

    public String orderDetails(){
        StringBuilder sb = new StringBuilder();
        sb.append("- Product Name: " + product.getName() + "  Product Code: " + product.getCode() + "  Amount: " + quantity +
                "\nFor: " + customer.toString() + "\nOrder Profit: " + calculateOrderProfit() + "  Order Price: " + getOrderPrice() + "\n");
        return sb.toString();
    }

    public String orderDetailsForInvoice(){
        StringBuilder sb = new StringBuilder();
        sb.append("- Product Name: " + product.getName() + "  Product Code: " + product.getCode() + "  Amount: " + quantity +
                "\nFor: " + customer.toString());
        return sb.toString();
    }

}
