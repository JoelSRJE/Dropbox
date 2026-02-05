package project.dropbox.exceptions.folder;

public class FolderOwnerIsEmptyException extends RuntimeException {
    public FolderOwnerIsEmptyException() {
        super("FolderOwner is empty!");
    }
}
