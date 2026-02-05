package project.dropbox.exceptions.folder;

public class FolderOwnerIsntSameException extends RuntimeException {
    public FolderOwnerIsntSameException() {
        super("FolderOwner id isn't the same, verify that its the correct ID!");
    }
}
