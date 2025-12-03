public class OrderItem {
    private MenuItem item;
    private int quantity;


    public OrderItem(MenuItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }


    public MenuItem getItem() {
        return item;
    }


    public int getQuantity() {
        return quantity;
    }


    public double getTotal() {
        return item.getPrice() * quantity;
    }


    @Override
    public String toString() {
        return quantity + " x " + item.getName() + " (" + item.getPrice() + ")";
    }
}