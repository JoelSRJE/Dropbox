package project.dropbox.services.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.dropbox.dto.user.GetUserDto;
import project.dropbox.exceptions.user.*;
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

    // Dependencies för servicen
    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Hittar en användare baserat på email.
     *
     * @param email - Strängen för emailen som ska hittas.
     * @return - ett User-objekt om användaren finns.
     * @throws EmailIsBlankException - OM email strängen är null/blank.
     * @throws UserDoesntExistsException - OM användaren inte existerar i databasen.
     */
    @Override
    public User findUserByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new EmailIsBlankException();
        }

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserDoesntExistsException();
        }

        return user;
    }

    /**
     * Registrerar ett nytt User-objekt OM allt går igenom.
     *
     * @param user - User-objektet som tas emot från controllern.
     * @return - Det sparade User-objektet.
     * @throws EmailIsBlankException - OM email är null/blank.
     * @throws PasswordIsEmptyException - OM passwordHash är null/blank.
     * @throws UserAlreadyExistException - OM användaren redan existerar.
     */
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

    /**
     * Skapar en token om all information i LoginUserRequest stämmer, vilket get åtkomst till resterande endpoints.
     *
     * @param request - innehåller string email och string password.
     * @return - en jwt-token vid lyckad inloggning.
     * @throws EmailIsBlankException - OM email är null/blank.
     * @throws UserDoesntExistsException - OM användaren är null.
     * @throws InvalidCredentialsException - OM password inte stämmer.
     */
    @Override
    public String loginUser(LoginUserRequest request) {

        if (request.email() == null || request.email().isBlank()) {
            throw new EmailIsBlankException();
        }

        User userExists = userRepository.findByEmail(request.email());

        if (userExists == null) {
            throw new UserDoesntExistsException();
        }

        if (request.password() == null || request.password().isBlank()
                || !passwordEncoder.matches(request.password(), userExists.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        return jwtService.generateToken(userExists.getUserId());
    }

    /**
     * Uppdaterar ett befintligt konto i databasen baserat på id och request.
     *
     * @param userId - id:et för användaren som ska uppdateras.
     * @param request - innehåller string email.
     * @return - det uppdaterade User-objektet.
     * @throws UserDoesntExistsException - OM användaren inte existerar.
     */
    @Override
    public User updateUser(UUID userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserDoesntExistsException());

        if (request.email() != null && !request.email().isBlank()) {
            user.setEmail(request.email());
        }

        return userRepository.save(user);
    }

    /**
     * Raderar ett User-objekt från databasen OM det finns.
     *
     * @param userId - id:et för användaren.
     * @return - det raderade User-objektet.
     * @throws UserDoesntExistsException - OM användaren inte existerar.
     */
    @Override
    public User deleteUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserDoesntExistsException());

        userRepository.delete(user);

        return user;
    }

    /**
     * Hämtar en lista med alla User-objekt.
     *
     * @return - En lista med alla User-objekt.
     */
    @Override
    public List<GetUserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(GetUserDto::from)
                .toList();
    }
}
