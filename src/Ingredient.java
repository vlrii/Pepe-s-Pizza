public class Ingredient implements Cloneable {
    private String name;
    private double price;
    private String level = "Regular";

    public Ingredient(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public double getPrice() {
        return price * getLevelMultiplier();
    }

    private double getLevelMultiplier() {
        switch (level) {
            case "None": return 0.0;
            case "Light": return 1.0;
            case "Regular": return 1.0;
            case "Extra": return 1.5;
            default: return 1.0;
        }
    }

    @Override
    public Ingredient clone() {
        Ingredient copy = new Ingredient(name, price);
        copy.setLevel(level);
        return copy;
    }

    @Override
    public String toString() {
        return name + " (" + level + ")";
    }
}