package shop.model.exceptions;

public class InsufficientStockOfProduct extends Exception {
    public InsufficientStockOfProduct(String productName, int productId, int exists, int requested) {
        super(String.format("Insufficient stock of product %s (%d),\n\texists: %d\n\trequested: %d",
            productName, productId, exists, requested));
    }
}
