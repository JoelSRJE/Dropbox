package project.dropbox.exceptions.folder;

public class FolderIdIsNullException extends RuntimeException {
    public FolderIdIsNullException() {
        super("Folder ID is null!");
    }
}
