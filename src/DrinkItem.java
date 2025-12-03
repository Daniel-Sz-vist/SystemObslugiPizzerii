public class DrinkItem extends MenuItem {
    private boolean alcoholic;


    public DrinkItem(String name, double price, boolean alcoholic) {
        super(name, price);
        this.alcoholic = alcoholic;
    }


    public boolean isAlcoholic() {
        return alcoholic;
    }


    @Override
    public String getCategory() {
        return "Drink";
    }
}