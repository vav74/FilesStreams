public class Product {
    private final String name;
    private final int price;
    private int amount;

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Product(String name, int price, int amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }
}