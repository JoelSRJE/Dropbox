package project.dropbox.requests.user;

public record RegisterUserRequest(
        String email,
        String password
) {
}
