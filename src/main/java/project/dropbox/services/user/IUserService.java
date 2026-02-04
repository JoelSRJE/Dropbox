package project.dropbox.services.user;

import project.dropbox.dto.user.GetUserDto;
import project.dropbox.models.user.User;
import project.dropbox.requests.user.LoginUserRequest;
import project.dropbox.requests.user.UpdateUserRequest;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    // Hittar en användare baserat på email
    User findUserByEmail(String email);

    // Registrerar en ny användare/skapar en ny användare.
    User registerUser(User user);

    // Post request som genererar en token som ger tillåtelse till resterande endpoints.
    String loginUser(LoginUserRequest request);

    // Authorized methods

    // Uppdaterar en användare. Går bara att uppdatera email i nuläget.
    User updateUser(UUID userId, UpdateUserRequest request);

    // Raderar en användare baserat på dess id.
    User deleteUser(UUID userId);

    // Hämtar alla användare som finns i databasen.
    List<GetUserDto> getAllUsers();
}
