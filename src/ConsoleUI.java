import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI implements UserInterface {
    private Scanner scanner;
    private Pizzeria pizzeria;
    private boolean running = true;

    public ConsoleUI(Scanner scanner) {
        this.scanner = scanner;
        this.pizzeria = new Pizzeria();
        pizzeria.initSampleData();
    }

    @Override
    public void start() {
        System.out.println("Witaj w systemie obsługi restauracji!");
        while (running) {
            showMainMenu();

            String raw = scanner.nextLine();
            char choice;

            if (raw.length() > 0) {
                choice = raw.charAt(0);
            } else {
                choice = ' ';
            }

            if (choice == '1') {
                pizzeria.showMenuItems();
            } else if (choice == '2') {
                placeOrderFlow();
            } else if (choice == '3') {
                pizzeria.showTables();
            } else if (choice == '4') {
                pizzeria.sortMenuByPrice();
                pizzeria.sortMenuByName();
            } else if (choice == '5') {
                cancelOrderFlow();
            } else if (choice == '0') {
                running = false;
            } else {
                System.out.println("Nieznana opcja. Spróbuj ponownie.");
            }
        }
        System.out.println("Do widzenia!");
    }



    @Override
    public void showMainMenu() {
        System.out.println("--- MENU GŁÓWNE ---");
        System.out.println("1. Pokaż kartę dań");
        System.out.println("2. Złóż zamówienie");
        System.out.println("3. Pokaż stoliki");
        System.out.println("4. Pokaż sortowania karty dań");
        System.out.println("5. Anuluj zamówienie");
        System.out.println("0. Wyjście");
        System.out.print("Wybierz opcję: ");
    }

    private void placeOrderFlow() {
        try {
            System.out.print("Podaj nazwę klienta: ");
            String name = scanner.nextLine();
            if (name == null || name.isEmpty()) name = "Gość";
            Customer customer = new Customer(name, 100);

            pizzeria.showMenuItems();
            System.out.print("Wpisz id pozycji oddzielone przecinkami (np. 1,2) : ");

            String line = scanner.nextLine();
            String[] elementy = splitWithComma(line);
            Utilities.printArray(elementy);

            List<Integer> ids = new ArrayList<>();
            for (String e : elementy) {
                try {
                    ids.add(Integer.parseInt(e));
                } catch (NumberFormatException ex) {
                    System.out.println("Pominieto nieprawidlowy numer: " + e);
                }
            }

            Order order = pizzeria.createOrder(customer, ids);

            pizzeria.showTables();
            System.out.print("Wybierz numer stolika (wciśnij Enter, aby przypisać pierwszy wolny): ");
            String tableInput = scanner.nextLine();

            if (tableInput == null || tableInput.isEmpty()) {
                int free = pizzeria.findFirstFreeTableNumber();
                if (free > 0) {
                    try {
                        pizzeria.assignTableToOrder(order, free);
                        System.out.println("Automatycznie przypisano stolik numer " + free + ".");
                    } catch (InvalidOrderException ioe) {
                        System.out.println("Nie udało się automatycznie przypisać stolika: " + ioe.getMessage());
                    }
                } else {
                    System.out.println("Brak wolnych stolików. Zamówienie będzie bez stolika.");
                }
            } else {
                try {
                    int tableNumber = Integer.parseInt(tableInput);
                    try {
                        pizzeria.assignTableToOrder(order, tableNumber);
                    } catch (InvalidOrderException ioe) {
                        System.out.println("Nie można przypisać stolika: " + ioe.getMessage());
                    }
                } catch (NumberFormatException nfe) {
                    System.out.println("Nieprawidłowy numer stolika. Zamówienie będzie bez stolika.");
                }
            }

            pizzeria.placeOrder(order);
            System.out.println("Zamówienie złożone: " + order);
        } catch (InvalidOrderException ex) {
            System.out.println("Błąd przy składaniu zamówienia: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Nieoczekiwany błąd: " + ex.getMessage());
        }
    }

    private String[] splitWithComma(String tekst) {
        List<String> parts = new ArrayList<>();
        String buf = "";

        for (int i = 0; i < tekst.length(); i++) {
            char znak = tekst.charAt(i);

            if (znak == ',') {
                parts.add(buf);
                buf = "";
            } else {
                buf = buf + znak;
            }
        }

        parts.add(buf);

        String[] wynik = new String[parts.size()];
        for (int i = 0; i < parts.size(); i++) {
            wynik[i] = parts.get(i);
        }

        return wynik;
    }





    private void cancelOrderFlow() {
        try {
            List<Order> current = pizzeria.getOrders();
            if (current.isEmpty()) {
                System.out.println("Brak aktywnych zamówień do anulowania.");
                return;
            }

            System.out.println("Aktywne zamówienia:");
            for (Order o : current) {
                System.out.println(o.getId() + " - " + o);
            }

            System.out.print("Podaj ID zamówienia do anulowania: ");
            String line = scanner.nextLine();
            int id;
            try {
                id = Integer.parseInt(line);
            } catch (NumberFormatException nfe) {
                System.out.println("Nieprawidłowe ID.");
                return;
            }

            Order found = null;
            for (Order o : current) {
                if (o.getId() == id) {
                    found = o;
                    break;
                }
            }

            if (found != null) {
                System.out.print("Czy na pewno anulować zamówienie " + found.getId() + " (T/N)? ");
                String confirm = scanner.nextLine();

                boolean confirmed = !confirm.isEmpty() &&
                        (confirm.charAt(0) == 'T' || confirm.charAt(0) == 't' ||
                                confirm.charAt(0) == 'Y' || confirm.charAt(0) == 'y');


                if (confirmed) {
                    pizzeria.cancelOrder(found);
                } else {
                    System.out.println("Anulowanie przerwane.");
                }

            } else {
                System.out.println("Nie znaleziono zamówienia o ID: " + id);
            }

        } catch (Exception ex) {
            System.out.println("Błąd podczas anulowania zamówienia: " + ex.getMessage());
        }
    }

}