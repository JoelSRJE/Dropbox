package project.dropbox.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import project.dropbox.models.user.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
