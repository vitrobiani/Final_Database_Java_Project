import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Invoice implements Serializable {
    public final double MAAM = 0.17;
    public Order order;
    public LocalDateTime dateTime;
    public double sumTotal;

    public Invoice(Order order){
        this.order = order;
        this.dateTime = LocalDateTime.now();
        sumTotal = order.getProduct().getSellPrice()*order.quantity;
    }

    public double calculateProfit(){
        return order.calculateOrderProfit();
    }

    public String invoiceFormatForCustomer() {
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm");
        String rightNow = dateTime.format(timeFormat);
        StringBuilder invoice = new StringBuilder();
        int quantity = order.getQuantity();
        double tax = sumTotal*MAAM;
        invoice.append("time of sale: " + rightNow + "\n");
        invoice.append(order.orderDetailsForInvoice());
        invoice.append("\nprice without tax:").append(sumTotal* (1-MAAM)).append("\n").append("total taxes: ").append(String.format("%.2f", tax));
        invoice.append("\nTotal Price: " + sumTotal);
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
