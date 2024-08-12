import java.util.ArrayList;

public abstract class ProductSoldInCountry extends Product {
    ArrayList<Invoice> invoices;

    public ProductSoldInCountry(String code, String name, double buyPrice, double sellPrice, int weight, int stock) {
        super(code, name, buyPrice, sellPrice, weight, stock);
        invoices = new ArrayList<>();
    }

    @Override
    public void addOrder(Order order) {
        super.addOrder(order);
        invoices.add(new Invoice(order));
    }

    public ArrayList<Invoice> getInvoices(){
        return invoices;
    }

    public String invoiceToStringForCustomer(int invoiceIndex){
        return invoices.get(invoiceIndex).invoiceFormatForCustomer();
    }

    public String invoiceToStringForAccountant(int invoiceIndex){
        return invoices.get(invoiceIndex).invoiceFormatForAccountant();
    }

    public String toString(){
        return super.toString();
    }

}
