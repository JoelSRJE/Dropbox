package project.dropbox.dto.user;

import project.dropbox.models.user.AccountType;
import project.dropbox.models.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetUserDto(
        UUID userId,
        String email,
        AccountType accountType,
        LocalDateTime createdAt
) {
    public static GetUserDto from(User user) {
        return new GetUserDto(user.getUserId(), user.getEmail(), user.getAccountType(), user.getCreatedAt());
    }
}
