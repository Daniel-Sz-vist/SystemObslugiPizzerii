import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pizzeria {
    private MenuItem[] menuArray = new MenuItem[10];
    private Table[] tablesArray = new Table[5];
    private Order[] ordersArray = new Order[20];

    private List<MenuItem> menu = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private Map<Integer, Table> tableMap = new HashMap<>();

    private List<Person> staff = new ArrayList<>();

    public static String name = "Pizzeria Senza Ananas";

    public Pizzeria() {
    }

    public void initSampleData() {
        menu.add(new FoodItem("Margherita", 15.0, true));
        menu.add(new FoodItem("Marinara", 25.0, false));
        menu.add(new DrinkItem("Water", 5.0, false));
        menu.add(new DrinkItem("Beer", 12.0, true));

        for (int i = 0; i < menu.size(); i++) {
            menuArray[i] = menu.get(i);
        }

        for (int i = 0; i < tablesArray.length; i++) {
            tablesArray[i] = new Table(i + 1);
            tableMap.put(i + 1, tablesArray[i]);
        }

        Waiter w1 = new Waiter("Jan");
        Waiter w2 = new Waiter("Anna");
        Chef c1 = new Chef("Marek");
        staff.add(w1);
        staff.add(w2);
        staff.add(c1);

        Customer cust1 = new Customer("Ala", 10);
        Customer cust2 = new Customer("Ola", 5);

        Order o1 = new Order(cust1);
        o1.addItem(menu.get(0), 2);
        o1.addItem(menu.get(2), 1);

        Order o2 = new Order(cust2);
        o2.addItem(menu.get(1), 1);
        o2.addItem(menu.get(3), 2);

        orders.add(o1);
        orders.add(o2);

        for (int i = 0; i < orders.size(); i++) {
            ordersArray[i] = orders.get(i);
        }

        Person p = w1;
        if (p instanceof Waiter) {
            Waiter downcasted = (Waiter) p;
            downcasted.serve(o1);
        }
    }

    public void showMenuItems() {
        System.out.println("Karta pizzy:");
        for (int i = 0; i < menuArray.length; i++) {
            if (menuArray[i] != null) System.out.println(menuArray[i].getId() + ". " + menuArray[i]);
        }
    }

    public void showTables() {
        System.out.println("Stoliki:");
        for (Table t : tablesArray) {
            System.out.println(t);
        }
    }

    public Order createOrder(Customer customer, List<Integer> menuIds) throws InvalidOrderException {
        if (menuIds == null || menuIds.isEmpty()) throw new InvalidOrderException("Brak pozycji w zamówieniu");
        Order order = new Order(customer);
        for (int id : menuIds) {
            MenuItem found = findMenuItemById(id);
            if (found == null) throw new InvalidOrderException("Nie znaleziono pozycji: " + id);
            order.addItem(found, 1);
        }
        return order;
    }

    private MenuItem findMenuItemById(int id) {
        for (MenuItem m : menu) {
            if (m.getId() == id) return m;
        }
        return null;
    }

    public void placeOrder(Order order) {
        orders.add(order);
        for (int i = 0; i < ordersArray.length; i++) {
            if (ordersArray[i] == null) {
                ordersArray[i] = order;
                break;
            }
        }
        Customer cust = order.getCustomer();
        if (cust != null) {
            cust.addPoints((int) order.getTotal());
        }
        for (Person p : staff) {
            if (p instanceof Waiter) {
                ((Waiter) p).serve(order);
                break;
            }
        }
    }

    public void assignTableToOrder(Order order, int tableNumber) throws InvalidOrderException {
        Table table = tableMap.get(tableNumber);
        if (table == null) throw new InvalidOrderException("Nie istnieje stolik: " + tableNumber);
        if (table.getStatus() != TableStatus.FREE) throw new InvalidOrderException("Stolik zajęty lub zarezerwowany");
        table.setStatus(TableStatus.OCCUPIED);
        order.setTable(table);
        System.out.println("Stolik " + tableNumber + " przypisany do zamówienia " + order.getId());
    }

    public void cancelOrder(Order order) {
        orders.remove(order);
        for (int i = 0; i < ordersArray.length; i++) {
            if (ordersArray[i] != null && ordersArray[i].equals(order)) {
                ordersArray[i] = null;
            }
        }
        Table table = order.getTable();
        if (table != null) table.setStatus(TableStatus.FREE);
        System.out.println("Zamówienie " + order.getId() + " zostało anulowane.");
    }

    public List<Order> getOrders() {
        return orders;
    }

    public int findFirstFreeTableNumber() {
        for (Table t : tablesArray) {
            if (t != null && t.getStatus() == TableStatus.FREE) {
                return t.getNumber();
            }
        }
        return -1;
    }

    public void sortMenuByPrice() {
        MenuItem[] copy = new MenuItem[menu.size()];
        for (int i = 0; i < menu.size(); i++) copy[i] = menu.get(i);

        for (int i = 0; i < copy.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < copy.length; j++) {
                if (copy[j].getPrice() < copy[minIndex].getPrice()) {
                    minIndex = j;
                }
            }
            MenuItem tmp = copy[i];
            copy[i] = copy[minIndex];
            copy[minIndex] = tmp;
        }

        System.out.println("Karta posortowana wg ceny:");
        for (MenuItem m : copy) System.out.println(m);
    }

    public void sortMenuByName() {
        MenuItem[] copy = new MenuItem[menu.size()];
        for (int i = 0; i < menu.size(); i++) copy[i] = menu.get(i);

        for (int i = 0; i < copy.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < copy.length; j++) {
                if (compareIgnoreCase(copy[j].getName(), copy[minIndex].getName()) < 0) {
                    minIndex = j;
                }
            }
            MenuItem tmp = copy[i];
            copy[i] = copy[minIndex];
            copy[minIndex] = tmp;
        }

        System.out.println("Karta posortowana wg nazwy:");
        for (MenuItem m : copy) System.out.println(m);
    }

    private int compareIgnoreCase(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        int minLen = Math.min(len1, len2);

        for (int i = 0; i < minLen; i++) {
            char c1 = normalize(s1.charAt(i));
            char c2 = normalize(s2.charAt(i));
            if (c1 != c2) {
                return c1 - c2;
            }
        }
        return len1 - len2;
    }

    private char normalize(char c) {
        if (c >= 'A' && c <= 'Z') {
            return (char)(c + 32);
        }
        return c;
    }


}
