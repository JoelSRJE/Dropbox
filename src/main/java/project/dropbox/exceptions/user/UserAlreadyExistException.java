package project.dropbox.exceptions.user;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
        super("User already exists in database!");
    }
}
