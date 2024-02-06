package shop.utils;

import java.util.List;
import java.util.Map;

import shop.model.Order;
import shop.model.Product;

public class UserUtils {
    public static double getProductScore(Product product, List<Order> orders, Map<Product, Integer> cart) {
        if (product.getStock() == 0) { // we cannot recommend products that are out of stock
            return 0;
        }

        if (cart.containsKey(product)) { // we cannot recommend products that are already in the cart
            return 0;
        }

        int[] weights = new int[] { 3, 2, 1 };
        int weightsSum = 0;
        for(int i : weights) {
            weightsSum += i;
        }

        double frequency = getFrequencyFactor(product, orders);
        double timeFactor = getTimeFactor(product, orders);
        double priceFactor = getPriceFactor(product, orders);

        return (((double)weights[0] * frequency) + ((double)weights[1] * timeFactor) + ((double)weights[2] * priceFactor)) / (double)weightsSum;
    }

    private static double getFrequencyFactor(Product product, List<Order> orders) {
        double frequency = 0;
        for (Order order : orders) {
            if (order.getProducts().contains(product)) {
                frequency++;
            }
        }
        return frequency / (double)orders.size();
    }

    private static double getTimeFactor(Product product, List<Order> orders) {
        Order firstOrder = orders.stream()
            .min((o1, o2) -> o1.getOrderDate().compareTo(o2.getOrderDate())).orElse(null);
        Order lastProductOrder = orders.stream()
            .filter(o -> o.getProducts().contains(product)) // filter orders that contain the product
            .min((o1, o2) -> o1.getOrderDate().compareTo(o2.getOrderDate())).orElse(null);

        if (firstOrder != null && lastProductOrder != null) {
            long today = System.currentTimeMillis();
            long timeDelta = today - firstOrder.getOrderDate().getTime();
            long timePos = today - lastProductOrder.getOrderDate().getTime();
            return (double)timePos / (double)timeDelta;
        }
        return 0;

    }

    private static double getPriceFactor(Product product, List<Order> orders) {
        double totalPrice = orders.stream().reduce(null, (acc, order) -> {
            if (order.getProducts().contains(product)) {
                double orderTotal = order.getTotalPrice();
                if (acc == null) {
                    return orderTotal;
                } else {
                    return acc + orderTotal;
                }
            }
            return acc;
        }, (acc1, acc2) -> acc1 + acc2);

        double productPrice = orders.stream().reduce(null, (acc, order) -> {
            if (order.getProducts().contains(product)) {
                double cost = order.getCostFor(product);
                if (acc == null) {
                    return cost;
                } else {
                    return acc + cost;
                }
            }
            return acc;
        }, (acc1, acc2) -> acc1 + acc2);
        return productPrice / totalPrice;
    }
}
