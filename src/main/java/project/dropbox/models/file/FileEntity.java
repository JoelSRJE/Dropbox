package project.dropbox.models.file;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.dropbox.models.folder.FolderEntity;
import project.dropbox.models.user.User;

import java.util.UUID;

@Entity
@Table(name = "files")
@Getter
@Setter
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID fileId;

    @Column(nullable = false)
    private String fileName;

    @Lob
    @Column(nullable = false)
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "folder_id", nullable = false)
    private FolderEntity folder;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User fileOwner;

    protected FileEntity() {}

    public FileEntity(String fileName, byte[] data, FolderEntity folder, User fileOwner) {
        this.fileName = fileName;
        this.data = data;
        this.folder = folder;
        this.fileOwner = fileOwner;
    }

}
