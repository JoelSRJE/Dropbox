package project.dropbox.exceptions.file;

public class FileFolderIsNullException extends RuntimeException {
    public FileFolderIsNullException() {
        super("FileFolder is null!");
    }
}
