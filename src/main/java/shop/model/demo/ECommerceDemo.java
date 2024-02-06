package shop.model.demo;

import java.util.List;

import shop.model.Product;
import shop.model.User;
import shop.model.ecommerce.ECommercePlatform;
import shop.model.exceptions.InsufficientStockOfProduct;
import shop.utils.ProductComparator;
import shop.utils.ProductComparatorType;

public class ECommerceDemo {
    private Product appleProduct = new Product("Apple", 1.3);
    private Product bananaProduct = new Product("Banana", 2.2);
    private Product orangeProduct = new Product("Orange", 3.3);
    private Product pineappleProduct = new Product("Pineapple", 3.4);
    private Product waterlemonProduct = new Product("Watermelon", 5.2);
    private Product melonProduct = new Product("Melon", 16.3);
    private Product grapeProduct = new Product("Grape", 7.4);
    private Product strawberryProduct = new Product("Strawberry", 2.5);
    private Product cherryProduct = new Product("Cherry", 1.7);
    private Product mangoProduct = new Product("Mango", 6.1);

    private ECommercePlatform platform = new ECommercePlatform();

    public ECommerceDemo() {
        this.initializeStore();
    }

    public void run() throws Exception {
        // add products to users' carts
        makeFirstOrders();
        Thread.sleep(1000);

        // create new one orders for
        makeSecondOrders();
        Thread.sleep(1000);

        // make third attempt to buy products
        makeThirdOrders();
        Thread.sleep(1000);

        printStoreStat();
        printUserStat(this.platform.getUsers().get(0));
    }

    public List<Product> getProductsList(ProductComparatorType orderBy, boolean onlyAvailable){
        return this.platform.getProducts().stream()
            .filter(p -> !onlyAvailable || p.getStock() > 0)
            .sorted(ProductComparator.getComparatorBy(orderBy)).toList();
    }

    private void makeFirstOrders() throws Exception {
        // getting users
        User user1 = this.platform.getUsers().get(0);
        User user2 = this.platform.getUsers().get(1);
        User user3 = this.platform.getUsers().get(2);
        User user4 = this.platform.getUsers().get(3);

        // add products to users' carts
        user1.addToCart(appleProduct, 12);
        user1.addToCart(bananaProduct, 5);

        user2.addToCart(appleProduct, 15);
        user2.addToCart(orangeProduct, 3);

        user3.addToCart(pineappleProduct, 2);
        user3.addToCart(waterlemonProduct, 1);

        user4.addToCart(melonProduct, 4);
        user4.addToCart(grapeProduct, 6);


        // make orders
        platform.makeOrderByUserId(user1.getId());
        platform.makeOrderByUserId(user2.getId());
        platform.makeOrderByUserId(user3.getId());
        platform.makeOrderByUserId(user4.getId());
    }

    private void makeSecondOrders() throws Exception {
        // getting users
        User user1 = this.platform.getUsers().get(0);
        User user2 = this.platform.getUsers().get(1);

        // add products to users' carts
        try {
            user1.addToCart(appleProduct, 11);
        } catch (InsufficientStockOfProduct e) { // should be caused due to insufficient apples on stock
            System.out.println(e.getMessage());
        }

        user1.addToCart(bananaProduct, 3);
        user1.addToCart(cherryProduct, 2);
        this.platform.makeOrderByUserId(user1.getId());

        user2.addToCart(orangeProduct, 3);
        user2.addToCart(mangoProduct, 6);
        this.platform.makeOrderByUserId(user2.getId());
    }

    private void makeThirdOrders() throws Exception {
        // getting users
        User user1 = this.platform.getUsers().get(0);
        User user2 = this.platform.getUsers().get(1);

        // add products to users' carts
        user1.addToCart(bananaProduct, 2);
        user1.addToCart(cherryProduct, 1);
        this.platform.makeOrderByUserId(user1.getId());

        user2.addToCart(orangeProduct, 3);
        user2.addToCart(waterlemonProduct, 2);
        this.platform.makeOrderByUserId(user2.getId());
    }

    private void initializeStore() {
        // add new users to platform
        platform.addUser(new User("John Doe"));
        platform.addUser(new User("Jane Dwane"));
        platform.addUser(new User("Jack Ewans"));
        platform.addUser(new User("Jill Grille"));


        // add new products to platform
        platform.addProduct(appleProduct);
        platform.addProduct(bananaProduct);
        platform.addProduct(orangeProduct);
        platform.addProduct(pineappleProduct);
        platform.addProduct(waterlemonProduct);
        platform.addProduct(melonProduct);
        platform.addProduct(grapeProduct);
        platform.addProduct(strawberryProduct);
        platform.addProduct(cherryProduct);
        platform.addProduct(mangoProduct);

        // add products to stock
        platform.addProductToStock(appleProduct, 27);
        platform.addProductToStock(bananaProduct, 18);
        platform.addProductToStock(orangeProduct, 26);
        platform.addProductToStock(pineappleProduct, 34);
        platform.addProductToStock(waterlemonProduct, 23);
        platform.addProductToStock(melonProduct, 72);
        platform.addProductToStock(grapeProduct, 11);
        platform.addProductToStock(strawberryProduct, 26);
        platform.addProductToStock(cherryProduct, 19);
        platform.addProductToStock(mangoProduct, 30);
    }

    private void printStoreStat() {
        System.out.println("----------------------------------------");
        System.out.println("---------   Store statistics:   --------");
        System.out.println("----------------------------------------");
        System.out.println("Total users: " + this.platform.getUsers().size());
        System.out.println("Total products: " + this.platform.getAvailableProducts().size());
        System.out.println("Total orders: " + this.platform.getOrders().size());
        System.out.println("----------------------------------------\n");
        List<Product> products = this.getProductsList(ProductComparatorType.BY_NAME, false);
        printProductsStat(products);
    }

    private void printProductsStat(List<Product> products) {
        System.out.println("----------------------------------------");
        System.out.println("---------   Products list:   -----------");
        System.out.println("----------------------------------------");
        for (Product product : products) {
            System.out.println(product);
        }
        System.out.println("----------------------------------------\n");
    }

    private void printUserStat(User user) {
        List<Product> products = this.platform.getRecommenededProducts(user);
        System.out.println("----------------------------------------");
        System.out.println("-------- Favorite user products: -------");
        System.out.println("----------------------------------------");
        for (Product product : products) {
            System.out.println(product);
        }
    }
}
