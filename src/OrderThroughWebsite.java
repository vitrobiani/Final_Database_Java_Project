public class OrderThroughWebsite extends Order{
    DataBase db = DataBase.getInstance();
    ShippingType shippingType;
    ShippingCompany company;
    public OrderThroughWebsite(Customer customer, int quantity, Product product, ShippingType shippingType) {
        super(customer, quantity, product);
        this.shippingType = shippingType;
        ((ProductSoldThroughWebsite) product).addOrder(this);
        product.updateStock(product.getStock() - quantity);
        this.company = setShippingCompany();
    }

    public ShippingCompany setShippingCompany(){
        switch(shippingType){
            case EXPRESS: {
                return bestExpressShipping();
            }
            case STANDARD:{
                return bestStandardShipping();
            }
        }
        return null;
    }

    public ShippingCompany bestExpressShipping(){
        double bestPrice = db.companies.getFirst().calculateExpressShippingCost(this);
        ShippingCompany bestCompany = db.companies.getFirst();
        for (ShippingCompany company: db.companies){
            double i = company.calculateExpressShippingCost(this);
            if (i <= bestPrice) {
                bestCompany = company;
                bestPrice = i;
            }
        }
        return bestCompany;
    }

    public ShippingCompany bestStandardShipping() {
        double bestPrice = db.companies.getFirst().calculateRegularShippingCost(this);
        ShippingCompany bestCompany = db.companies.getFirst();
        for (ShippingCompany company : db.companies) {
            double i = company.calculateRegularShippingCost(this);
            if (i <= bestPrice) {
                bestCompany = company;
                bestPrice = i;
            }
        }
        return bestCompany;
    }

    public String orderDetails(){
        StringBuilder sb = new StringBuilder();
        sb.append(super.orderDetails());
        sb.append("  Shipping Fees: " );
        if (shippingType == ShippingType.STANDARD)
            sb.append(company.calculateRegularShippingCost(this));
        else
            sb.append(company.calculateExpressShippingCost(this));
        sb.append("\nShipping Company: " + company.getClass().getSimpleName());

        return sb.toString();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString() + "Shipping Fees: " );
        if (shippingType == ShippingType.STANDARD)
            sb.append(company.calculateRegularShippingCost(this));
        else
            sb.append(company.calculateExpressShippingCost(this));

        return sb.toString();
    }
}
