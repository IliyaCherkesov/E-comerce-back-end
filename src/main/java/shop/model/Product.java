package shop.model;

import shop.utils.UniqueId;

public class Product implements Comparable<Product>{
    private int id;
    private String name;
    private double price;
    private int stock;

    public Product(String name, double price) {
        this.id = UniqueId.generateUniqueId();
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStockCount(int value) {
        this.stock = value;
    }

    public void decreaseStock(int quantity) {
        this.stock -= quantity;
    }

    public void increaseStock(int quantity) {
        this.stock += quantity;
    }

    @Override
    public int compareTo(Product o) {
        return o.price > this.price ? 1 : o.price < this.price ? -1 : 0;
    }

    @Override
    public String toString() {
        String info = String.format("Product %s info:\n\tprice: %.2f\n\tavailable: %s", name, price, stock);
        return info;
    }
}

