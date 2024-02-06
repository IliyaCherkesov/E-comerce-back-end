package shop.utils;

public class UniqueId {
    private static int counter = 0;
    public static int generateUniqueId() {
        counter++;
        return counter;
    }
}