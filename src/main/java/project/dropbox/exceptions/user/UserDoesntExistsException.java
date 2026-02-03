package project.dropbox.exceptions.user;

public class UserDoesntExistsException extends RuntimeException {
    public UserDoesntExistsException() {
        super("User doesn't exist in database!");
    }
}
