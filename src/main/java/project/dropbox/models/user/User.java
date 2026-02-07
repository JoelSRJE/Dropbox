package project.dropbox.models.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import project.dropbox.models.file.FileEntity;
import project.dropbox.models.folder.FolderEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private AccountType accountType;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(
            mappedBy = "folderOwner",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private List<FolderEntity> folders;

    @OneToMany(
            mappedBy = "fileOwner",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private List<FileEntity> files;

    protected User() {}

    public User(String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = LocalDateTime.now();
        this.accountType = AccountType.USER;
    }
}
