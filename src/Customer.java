public class Customer extends Person {
    private int loyaltyPoints;


    public Customer(String name, int loyaltyPoints) {
        super(name);
        this.loyaltyPoints = loyaltyPoints;
    }


    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }


    public void addPoints(int points) {
        this.loyaltyPoints += points;
    }


    @Override
    public String getRole() {
        return "Customer";
    }


    @Override
    public String toString() {
        return "Customer{name='" + getName() + "', points=" + loyaltyPoints + "}";
    }
}