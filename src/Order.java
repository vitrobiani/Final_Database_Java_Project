public class Order {
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

}
