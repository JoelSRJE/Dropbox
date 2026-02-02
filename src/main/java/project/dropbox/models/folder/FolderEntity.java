package project.dropbox.models.folder;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import project.dropbox.models.user.User;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "folders")
@Getter
@Setter
public class FolderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID folderId;

    @Column(nullable = false)
    private String folderName;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User folderOwner;

    @ManyToOne
    @JoinColumn(name = "parent_folder_id")
    private FolderEntity parentFolder;

    @OneToMany(mappedBy = "parentFolder")
    private List<FolderEntity> subFolders;

    protected FolderEntity() {}

    public FolderEntity(String folderName, User folderOwner) {
        this.folderName = folderName;
        this.folderOwner = folderOwner;
    }

    public FolderEntity(String folderName, User folderOwner, FolderEntity parentFolder) {
        this.folderName = folderName;
        this.folderOwner = folderOwner;
        this.parentFolder = parentFolder;
    }
}
