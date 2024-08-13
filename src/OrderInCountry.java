public class OrderInCountry extends Order{
    public OrderInCountry(Customer customer, int quantity, Product product) {
        super(customer, quantity, product);
        product.updateStock(product.getStock() - quantity);
    }
}
