public class Chef extends Employee {
    public Chef(String name) {
        super(name, Role.CHEF);
    }


    public void cook(Order order) {
        System.out.println(getName() + " przygotowuje zamówienie: " + order.getId());
    }
}