import java.io.Serial;
import java.io.Serializable;
import java.util.Stack;

public class Order implements Serializable {
    Customer customer;
    int quantity;
    Product product;
    public Order(Customer customer, int quantity, Product product) {
        this.customer = customer;
        this.quantity = quantity;
        this.product = product;
        product.addOrder(this);
        product.updateStock(product.getStock() - quantity);

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


}
