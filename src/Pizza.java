import java.util.ArrayList;
import java.util.List;

public class Pizza implements Cloneable {
    private String name;
    private String size = "Medium";
    private String crust = "Thick";
    private List<Ingredient> ingredients = new ArrayList<>();

    public Pizza(String name) { this.name = name; }

    public String getName() { return name; }
    public String getSize() { return size; }
    public String getCrust() { return crust; }
    public List<Ingredient> getIngredients() { return ingredients; }

    public void setSize(String size) { this.size = size; }
    public void setCrust(String crust) { this.crust = crust; }
    public void addIngredient(Ingredient ing) { ingredients.add(ing); }

    public double calculatePrice() {
        double price = 4.0;
        for (Ingredient ing : ingredients) {
            price += ing.getPrice();
        }

        switch (size) {
            case "Small": price *= 0.9; break;
            case "Medium": break;
            case "Large": price *= 1.3; break;
        }

        if (crust.equals("Thick")) price += 2.0;
        return Math.round(price * 100.0) / 100.0;
    }

    public int getWaitTime() {
        int time = 10;
        switch (size) {
            case "Small": break;
            case "Medium": time += 5; break;
            case "Large": time += 10; break;
        }
        for (Ingredient ing : ingredients) {
            if (!ing.getLevel().equals("None")) time += 2;
        }
        if (crust.equals("Thick")) time += 3;
        return time;
    }

    @Override
    public Pizza clone() {
        Pizza copy = new Pizza(name);
        copy.setSize(size);
        copy.setCrust(crust);
        for (Ingredient ing : ingredients) copy.addIngredient(ing.clone());
        return copy;
    }

    @Override
    public String toString() { return name; }
}