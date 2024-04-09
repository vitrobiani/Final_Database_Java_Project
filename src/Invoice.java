import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//for the date time
//LocalDateTime time = LocalDateTime.now();
//DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
//String rightNow = time.format(timeFormat);

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
}
