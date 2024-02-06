package shop.utils;
import java.util.Comparator;

import shop.model.Product;

public abstract class ProductComparator implements Comparator<Product> {

    @Override
    public abstract int compare(Product o1, Product o2);

    @Override
    public boolean equals(Object obj) {
        return obj == this;
    }

    public static ProductComparator getComparatorBy(ProductComparatorType type) {
        switch (type) {
            case BY_NAME:
                return new ProductComparatorByName();
            case BY_PRICE:
                return new ProductComparatorByPrice();
            case BY_QUANTITY:
                return new ProductComparatorByQuantity();
            default:
                return null;
        }
    }
}
