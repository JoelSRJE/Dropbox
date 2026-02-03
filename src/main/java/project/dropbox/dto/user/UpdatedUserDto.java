package project.dropbox.dto.user;

import project.dropbox.models.user.User;

public record UpdatedUserDto(
        String email
) {
    public static UpdatedUserDto from(User user) {
        return new UpdatedUserDto(user.getEmail());
    }
}
