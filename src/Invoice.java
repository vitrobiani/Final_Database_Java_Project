import java.time.LocalDateTime;


public class Invoice {
    public Order order;
    public LocalDateTime dateTime;

    public Invoice(Order order){
        this.order = order;
        this.dateTime = LocalDateTime.now();
    }

    public double calculateProfit(){
        return (order.getProduct().calculateProductProfit()) * order.getQuantity();
    }
    public void invoiceFormatForCustomer(Order order) {
       StringBuffer invoice = new StringBuffer();
       int quantity = order.getQuantity();
       double price = order.getProduct().getSellPrice()*quantity;
       double tax = price*0.17;
        invoice.append(order.toString()).append("\n").append("price without tax:").append(price * 0.83).append("\n").append("total taxes:").append(tax);
        System.out.print(invoice);
    }
    public void invoiceFormatForAccountant(Order order){
        invoiceFormatForCustomer(order);
        System.out.println("profit: " + calculateProfit());
    }
}
