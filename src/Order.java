import java.util.ArrayList;

public class Order {
    private String customerName;
    private ArrayList<Pizza> pizzas;

    public Order(String customerName) {
        this.customerName = customerName;
        this.pizzas = new ArrayList<>();
    }

    public String getCustomerName() { return customerName; }
    public ArrayList<Pizza> getPizzas() { return pizzas; }
    public void addPizza(Pizza p) { pizzas.add(p); }
    public void removePizza(Pizza p) { pizzas.remove(p); }

    public double getTotalPrice() {
        double total = 0;
        for (Pizza p : pizzas) total += p.calculatePrice();
        return Math.round(total * 100.0) / 100.0;
    }

    public int getTotalWaitTime() {
        if (pizzas.isEmpty()) return 0;
        int total = pizzas.get(0).getWaitTime();
        total += (pizzas.size() - 1) * 3;
        return total;
    }
}