public abstract class MenuItem implements Comparable<MenuItem> {
    private int id;
    private String name;
    private double price;
    private static int nextId = 1;


    public MenuItem(String name, double price) {
        this.id = nextId++;
        this.name = name;
        this.price = price;
    }


    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public double getPrice() {
        return price;
    }


    public static int getNextId() {
        return nextId;
    }


    public void setPrice(double price) {
        this.price = price;
    }


    public abstract String getCategory();


    @Override
    public int compareTo(MenuItem other) {
        return Double.compare(this.price, other.price);
    }


    @Override
    public String toString() {
        return "MenuItem{id=" + id + ", name='" + name + "', price=" + price + "}";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItem)) return false;
        MenuItem menuItem = (MenuItem) o;
        return id == menuItem.id;
    }


    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}