package project.dropbox.services.user;

import project.dropbox.dto.user.GetUserDto;
import project.dropbox.models.user.User;
import project.dropbox.requests.user.LoginUserRequest;
import project.dropbox.requests.user.UpdateUserRequest;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    User findUserByEmail(String email);
    User registerUser(User user);
    String loginUser(LoginUserRequest request);

    // Authorized methods
    User updateUser(UUID userId, UpdateUserRequest request);
    User deleteUser(UUID userId);
    List<GetUserDto> getAllUsers();
}
