package shop.model.exceptions;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(int userId) {
        super(String.format("User with id '%s' not found", userId));
    }
}
