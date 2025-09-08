import java.util.ArrayList;
import java.util.List;

public class Order {
    private String customerName;
    private List<Pizza> pizzas = new ArrayList<>();

    public Order(String customerName) { this.customerName = customerName; }
    public String getCustomerName() { return customerName; }
    public void addPizza(Pizza p) { pizzas.add(p); }
    public void removePizza(Pizza p) { pizzas.remove(p); }
    public List<Pizza> getPizzas() { return pizzas; }

    public double getTotalPrice() {
        double total = 0;
        for (Pizza p : pizzas) total += p.calculatePrice();
        return Math.round(total * 100.0) / 100.0;
    }

    public int getTotalWaitTime() {
        int total = 0;
        for (Pizza p : pizzas) total += p.getWaitTime();
        return total;
    }
}