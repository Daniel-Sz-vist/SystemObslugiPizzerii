public class FoodItem extends MenuItem {
    private boolean isVegetarian;


    public FoodItem(String name, double price, boolean isVegetarian) {
        super(name, price);
        this.isVegetarian = isVegetarian;
    }


    public boolean isVegetarian() {
        return isVegetarian;
    }


    @Override
    public String getCategory() {
        return "Food";
    }
}