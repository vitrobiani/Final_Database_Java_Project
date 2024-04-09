public enum ProductType {
    SOLD_THROUGH_WEBSITE("Website"),
    SOLD_IN_STORE("Store"),
    SOLD_TO_WHOLESALERS("Wholesalers");

    private String description;

    ProductType(String description) {
        this.description = description;
    }

    public String toString() {
        return description;
    }
}
