package shop.utils;

import shop.model.Product;

class ProductComparatorByPrice extends ProductComparator {

    @Override
    public int compare(Product o1, Product o2) {
        return o1.compareTo(o2);
    }

}
