package project.dropbox.exceptions.user;

public class EmailIsBlankException extends RuntimeException {
    public EmailIsBlankException() {
        super("Email cannot be blank!");
    }
}
