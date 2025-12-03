public class Order {
    private static int next_order_id = 1;
    private int id;
    private Customer customer;
    private OrderItem[] itemsArray = new OrderItem[20];
    private int itemsCount = 0;

    private Table table;

    public Order(Customer customer) {
        this.id = next_order_id++;
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void addItem(MenuItem item, int qty) {
        if (itemsCount >= itemsArray.length) {
            System.out.println("Nie można dodać więcej pozycji do zamówienia!");
            return;
        }
        itemsArray[itemsCount++] = new OrderItem(item, qty);
    }

    public double getTotal() {
        double sum = 0;
        for (int i = 0; i < itemsCount; i++) {
            sum += itemsArray[i].getTotal();
        }
        return sum;
    }

    public OrderItem[] getItems() {
        OrderItem[] copy = new OrderItem[itemsCount];
        for (int i = 0; i < itemsCount; i++) {
            copy[i] = itemsArray[i];
        }
        return copy;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    @Override
    public String toString() {
        String tableInfo;
        if (table != null) {
            tableInfo = "stolik=" + table.getNumber();
        } else {
            tableInfo = "brak stolika";
        }

        return "Order{id=" + id +
                ", customer=" + customer.getName() +
                ", total=" + getTotal() +
                ", " + tableInfo + "}";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
