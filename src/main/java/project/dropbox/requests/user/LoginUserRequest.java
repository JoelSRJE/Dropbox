package project.dropbox.requests.user;

public record LoginUserRequest(
        String email,
        String password
) {
}
