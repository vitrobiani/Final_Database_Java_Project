public interface Invoice {
    public static double maam = 0.17;

    public default String formatForCustomer(Order order){
        StringBuffer sb = new StringBuffer();
        sb.append("Order for: ");
        sb.append(order.customer.getName());
        sb.append("\n");
        sb.append("Phone Number: ");
        sb.append(order.customer.getPhoneNumber());
        sb.append("Product: ");
        sb.append(order.product.getName());
        sb.append("\n");
        sb.append("Quantity: ");
        sb.append(order.quantity);
        sb.append("\n");
        sb.append("Total: ");
        sb.append(order.product.sellPrice * order.quantity * (1-maam));
        sb.append("\n");
        sb.append("the maam is " + order.product.sellPrice * order.quantity * (maam));
        sb.append("\n");
        sb.append("Sum Total: " + order.product.sellPrice * order.quantity);
        sb.append("Thank you for shopping with us!");
        return sb.toString();
    }
}
