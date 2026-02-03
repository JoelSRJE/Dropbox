package project.dropbox.services.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.dropbox.dto.user.GetUserDto;
import project.dropbox.exceptions.user.EmailIsBlankException;
import project.dropbox.exceptions.user.PasswordIsEmptyException;
import project.dropbox.exceptions.user.UserAlreadyExistException;
import project.dropbox.exceptions.user.UserDoesntExistsException;
import project.dropbox.models.user.User;
import project.dropbox.repositories.user.UserRepository;
import project.dropbox.requests.user.LoginUserRequest;
import project.dropbox.requests.user.UpdateUserRequest;
import project.dropbox.utils.JWTService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findUserByEmail(String email) {
        if (email.isBlank()) {
            throw new EmailIsBlankException();
        }

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserDoesntExistsException();
        }

        return user;
    }

    @Override
    public User registerUser(User user) {

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new EmailIsBlankException();
        }

        if (user.getPasswordHash() == null || user.getPasswordHash().isBlank()) {
            throw new PasswordIsEmptyException();
        }

        boolean exists = userRepository.existsByEmail(user.getEmail());
        if (exists) {
            throw new UserAlreadyExistException();
        }

        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));

        return userRepository.save(user);

    }

    @Override
    public String loginUser(LoginUserRequest request) {
        User userExists = userRepository.findByEmail(request.email());

        User user = userExists;

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            return null;
        }

        return jwtService.generateToken(user.getUserId());
    }

    @Override
    public User updateUser(UUID userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserDoesntExistsException());

        if (request.email() != null && !request.email().isBlank()) {
            user.setEmail(request.email());
        }

        return userRepository.save(user);
    }

    @Override
    public User deleteUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserDoesntExistsException());

        userRepository.delete(user);

        return user;
    }

    @Override
    public List<GetUserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(GetUserDto::from)
                .toList();
    }


}
