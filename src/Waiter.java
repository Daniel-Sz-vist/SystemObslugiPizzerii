public class Waiter extends Employee {
    public Waiter(String name) {
        super(name, Role.WAITER);
    }


    public void serve(Order order) {
        System.out.println(getName() + " serwuje zamówienie: " + order.getId());
    }
}