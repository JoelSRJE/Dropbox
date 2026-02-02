package project.dropbox.models.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(unique = true)
    private String username;

    private String passwordHash;

    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }
}
