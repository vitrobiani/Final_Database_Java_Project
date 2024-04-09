public class Order {
    String makat;
    Customer customer;
    int quantity;
    Product product;
    public Order(String makat, Customer customer, int quantity, Product product) {
        this.makat = makat;
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
}
