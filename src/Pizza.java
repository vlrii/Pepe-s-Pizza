import java.util.ArrayList;
import java.util.List;

public class Pizza implements Cloneable {
    private String name;
    private String size = "Medium";
    private String crust = "Thick";
    private int quantity = 1;
    private Ingredient sauce; // NEW
    private List<Ingredient> ingredients = new ArrayList<>();

    public Pizza(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public String getSize() { return size; }
    public String getCrust() { return crust; }
    public Ingredient getSauce() { return sauce; }
    public List<Ingredient> getIngredients() { return ingredients; }
    public int getQuantity() { return quantity; }

    public void setSize(String size) { this.size = size; }
    public void setCrust(String crust) { this.crust = crust; }
    public void setSauce(Ingredient sauce) { this.sauce = sauce; }
    public void setQuantity(int q) { this.quantity = q; }

    public void addIngredient(Ingredient ing) { ingredients.add(ing); }

    public double calculatePrice() {
        double price = 4.0;
        if (sauce != null) price += sauce.getPrice();
        for (Ingredient ing : ingredients) price += ing.getPrice();

        switch (size) {
            case "Small": price *= 0.9; break;
            case "Medium": break;
            case "Large": price *= 1.3; break;
        }
        if (crust.equals("Thick")) price += 2.0;
        return Math.round(price * quantity * 100.0) / 100.0;
    }

    public int getWaitTime() {
        int time = 10;
        switch (size) {
            case "Small": break;
            case "Medium": time += 5; break;
            case "Large": time += 10; break;
        }
        if (sauce != null && !sauce.getLevel().equals("None")) time += 2;
        for (Ingredient ing : ingredients) {
            if (!ing.getLevel().equals("None")) time += 2;
        }
        if (crust.equals("Thick")) time += 3;

        // Additional pizzas only +3 min
        if (quantity > 1) time += (quantity - 1) * 3;

        return time;
    }

    @Override
    public Pizza clone() {
        Pizza copy = new Pizza(name);
        copy.setSize(size);
        copy.setCrust(crust);
        copy.setQuantity(quantity);
        if (sauce != null) copy.setSauce(sauce.clone());
        for (Ingredient ing : ingredients) copy.addIngredient(ing.clone());
        return copy;
    }

    @Override
    public String toString() { return name; }
}