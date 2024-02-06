package shop.utils;

import shop.model.Product;

class ProductComparatorByQuantity extends ProductComparator {
    @Override
    public int compare(Product o1, Product o2) {
        return o1.getStock() - o2.getStock();
    }

}
