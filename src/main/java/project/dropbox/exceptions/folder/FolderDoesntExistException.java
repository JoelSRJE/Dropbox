package project.dropbox.exceptions.folder;

public class FolderDoesntExistException extends RuntimeException {
    public FolderDoesntExistException() {
        super("Folder doesn't exist in database!");
    }
}
