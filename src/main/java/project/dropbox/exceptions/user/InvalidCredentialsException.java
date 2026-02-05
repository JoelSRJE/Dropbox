package project.dropbox.exceptions.user;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Wrong credentials, please try again!");
    }
}
