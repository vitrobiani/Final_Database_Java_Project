import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;


public class Invoice implements Serializable {
    public Order order;
    public LocalDateTime dateTime;

    public Invoice(Order order){
        this.order = order;
        this.dateTime = LocalDateTime.now();
    }

    public double calculateProfit(){
        return (order.getProduct().calculateProductProfit()) * order.getQuantity();
    }

    public String invoiceFormatForCustomer() {
        StringBuilder invoice = new StringBuilder();
        int quantity = order.getQuantity();
        double price = order.getProduct().getSellPrice()*quantity;
        double tax = price*0.17;
        invoice.append("time of sale: " + dateTime + "\n");
        invoice.append(order.toString()).append("\n").append("price without tax:").append(price * 0.83).append("\n").append("total taxes:").append(tax);
        return invoice.toString();
    }

    public String invoiceFormatForAccountant(){
        StringBuilder sb = new StringBuilder();
        sb.append(invoiceFormatForCustomer());
        sb.append("\nprofit: ").append(calculateProfit());
        return sb.toString();
    }

    public String invoiceReducedToString(){
        return "Customer Name: " + order.customer.getName() + ", Product Name: " + order.product.getName() +
                ", Quantity: " + order.getQuantity();
    }
}
