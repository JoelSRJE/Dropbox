package project.dropbox.controllers.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dropbox.dto.user.DeletedUserDto;
import project.dropbox.dto.user.GetUserDto;
import project.dropbox.dto.user.RegisteredUserDto;
import project.dropbox.dto.user.UpdatedUserDto;
import project.dropbox.models.user.User;
import project.dropbox.requests.user.LoginUserRequest;
import project.dropbox.requests.user.RegisterUserRequest;
import project.dropbox.requests.user.UpdateUserRequest;
import project.dropbox.services.user.UserService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisteredUserDto> registerUser(
            @RequestBody RegisterUserRequest request
    ) {

        User user = new User(request.email(), request.password());

        User newUser = userService.registerUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(RegisteredUserDto.from(newUser));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestBody LoginUserRequest request
            ) {

        String token = userService.loginUser(request);

        return ResponseEntity.ok(
                Map.of("token", token)
        );
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UpdatedUserDto> updateUser(
            @PathVariable UUID userId,
            @RequestBody UpdateUserRequest request
            ) {

        User updatedUser = userService.updateUser(userId, request);

        return ResponseEntity.ok(UpdatedUserDto.from(updatedUser));
    }

    @GetMapping("/users")
    public ResponseEntity<List<GetUserDto>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<DeletedUserDto> deleteUser(
    @PathVariable UUID userId
    ) {
        User deletedUser = userService.deleteUser(userId);

        return ResponseEntity.ok(DeletedUserDto.from(deletedUser));
    }
}
