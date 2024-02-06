package shop.utils;

import shop.model.Product;

class ProductComparatorByName extends ProductComparator {

    @Override
    public int compare(Product o1, Product o2) {
        return o1.getName().compareTo(o2.getName());
    }

}
