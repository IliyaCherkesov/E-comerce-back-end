package shop.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import shop.utils.UniqueId;

public class Order {
    private int id;
    private int userId;
    private Map<Product, Integer> orderDetails;
    private double totalPrice;
    private Date orderDate;

    public Order(int userId, Map<Product, Integer> orderDetails) {
        this.id = UniqueId.generateUniqueId();
        this.userId = userId;
        this.orderDetails = new HashMap<Product, Integer>(orderDetails);
        this.totalPrice = this.calculateTotalPrice();
        this.orderDate = new Date();
    }

    public int getId() {
        return this.id;
    }

    public int getUserId() {
        return this.userId;
    }

    public Date getOrderDate() {
        return this.orderDate;
    }

    public Map<Product, Integer> getOrderDetails() {
        return this.orderDetails;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

    public double getCostFor(Product product) {
        return product.getPrice() * this.orderDetails.get(product);
    }

    public Set<Product> getProducts() {
        return orderDetails.keySet();
    }

    private double calculateTotalPrice() {
        double totalPrice = 0;
        for (Map.Entry<Product, Integer> entry : this.orderDetails.entrySet()) {
            totalPrice += entry.getKey().getPrice() * entry.getValue();
        }
        return totalPrice;
    }
}
