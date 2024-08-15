public enum TN {
    SHIPPING_COMPANY("shippingcompanies",t.STR.nn()),
    SHIPPING_COMPANY_NAME("shippingcompanyname", t.STR.nn()),
    CONTACT_NAME("contactname", t.STR.nn()),
    CONTACT_NUMBER("contactnumber",t.STR.nn()),
    REGULAR_SHIPPING_MULT("regularShippingMult", t.FL.nn()),
    EXPRESS_SHIPPING_MULT("expressshippingmult", t.FL.nn()),

    COUNTRY("countries", t.STR.n),
    COUNTRY_CODE("countrycode", t.STR.nn()),
    COUNTRY_NAME("countryname", t.STR.nn()),
    COUNTRY_TAX("COUNTRY_TAX", t.FL.nn()),

    CUSTOMER("customers", t.STR.nn()),
    CUSTOMER_NAME("customername", t.STR.nn()),
    CUSTOMER_PHONE("customerphone", t.STR.nn()),

    PRODUCT("products", t.STR.nn()),
    PRODUCT_NAME("productname", t.STR.nn()),
    PRODUCT_CODE("productcode", t.STR.nn()),
    PRODUCT_BUY_PRICE("productbuyprice", t.FL.nn()),
    PRODUCT_SELL_PRICE("productsellprice", t.FL.nn()),
    PRODUCT_WEIGHT("productweight", t.FL.nn()),
    PRODUCT_STOCK("productstock", t.INT.nn()),
    PRODUCT_TYPE("producttype", t.STR.nn()),
    PRODUCT_COUNTRY("sourcecountrycode", t.STR.n),
    PRODUCT_SHIPPING_TYPE("shippingtype", t.STR.n),

    ORDER("orders", t.STR.n),
    ORDER_ID("orderid", t.SER.n),
    ORDER_QUANTITY("orderquantity", t.INT.nn()),
    ORDER_CUSTOMER("ordercustomerphonenumber", t.STR.nn()),
    ORDER_PRODUCT("orderproductcode", t.STR.nn()),
    ORDER_SHIPPING_TYPE("ordershippingtype", t.STR.n),
    ORDER_SHIPPING_COMPANY("ordershippingcompany",t.STR.n),

    INVOICE("invoices", t.SER.n),
    INVOICE_ID("invoiceid", t.SER.n),
    INVOICE_ORDER("invoiceorderid", t.INT.nn()),
    INVOICE_DATE("invoicedate", t.time.n);

    public final String name;
    public final String type;

    TN (String name, String type){
        this.name = name;
        this.type = type;
    }

    public String tname(){
        return name;
    }

    public String type(){
        return type;
    }
    protected enum t{
        STR("varchar(150)"), INT("integer"), FL("float"), SER("serial"), time("timestamp");
        public final String n;
        t(String n){
            this.n = n;
        }
        public String nn(){
            return n+" not null";
        }
    }
}
