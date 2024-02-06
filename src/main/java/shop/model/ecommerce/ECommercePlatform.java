package shop.model.ecommerce;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import shop.model.Order;
import shop.model.Product;
import shop.model.User;
import shop.model.exceptions.UserNotFoundException;
import shop.utils.UserUtils;

public class ECommercePlatform {
    private List<User> users = new ArrayList<User>();
    private List<Product> products = new ArrayList<Product>();
    private List<Order> orders = new ArrayList<Order>();

    public ECommercePlatform() {
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void makeOrderByUserId(int userId) throws UserNotFoundException{
        User user = this.users.stream().filter(u -> u.getId() == userId).findFirst().orElse(null);
        if (user != null) {
            Order order = new Order(userId, user.getCart());
            this.orders.add(order);
            user.getCart().clear();
        } else {
            throw new UserNotFoundException(userId);
        }
    }

    public List<Product> getAvailableProducts() {
        return this.products.stream().filter(p -> p.getStock() > 0).toList();
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public List<Order> getOrders() {
        return this.orders;
    }

    public void addProductToStock(Product product, int quantity) {
        product.increaseStock(quantity);
    }

    public List<Product> getRecommenededProducts(User user) {
        // we have to recommend products by
        // a) frequency of purchase
        // b) time of purchase (most recent should have less priority, so products in cart sould have less priority ever)
        // c) total amount of money spent

        List<Order> userOrders = this.orders.stream().filter(o -> o.getUserId() == user.getId()).toList();
        Map<Product, Integer> userCart = user.getCart();
        Set<Product> productsSet = new HashSet<Product>();
        userOrders.forEach(o -> {
            o.getProducts().forEach(product -> {
                productsSet.add(product);
            });
        });
        userCart.keySet().forEach(product -> {
            productsSet.add(product);
        });

        return productsSet.stream()
            .filter(p -> p.getStock() > 0)
            .sorted((p1, p2) -> {
                double s1 = UserUtils.getProductScore(p1, userOrders, userCart);
                double s2 = UserUtils.getProductScore(p2, userOrders, userCart);
                return Double.compare(s2, s1);
            })
            .toList();
    }
}
