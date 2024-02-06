package shop.model;

import java.util.HashMap;
import java.util.Map;

import shop.model.exceptions.InsufficientStockOfProduct;
import shop.utils.UniqueId;

public class User {
    private int id;
    private String name;
    private Map<Product, Integer> cart;

    public User(String name) {
        this.id = UniqueId.generateUniqueId();
        this.name = name;
        this.cart = new HashMap<Product, Integer>();
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Map<Product, Integer> getCart() {
        return this.cart;
    }

    public void addToCart(Product product, int requestedQuantity) throws InsufficientStockOfProduct {
        if (product.getStock() >= requestedQuantity) {
            product.decreaseStock(requestedQuantity);
            if (this.cart.containsKey(product)) {
                this.cart.put(product, this.cart.get(product) + requestedQuantity);
            } else {
                this.cart.put(product, requestedQuantity);
            }
        } else {
            throw new InsufficientStockOfProduct(product.getName(), product.getId(), product.getStock(), requestedQuantity);
        }
    }

    public void removeFromCart(Product product, int quantity) {
        if (this.cart.containsKey(product)) {
            if (this.cart.get(product) > quantity) {
                this.cart.put(product, this.cart.get(product) - quantity);
            } else {
                this.cart.remove(product);
            }
        }
    }

    public void removeProductFromCart(Product product) {
        this.cart.remove(product);
    }
}
