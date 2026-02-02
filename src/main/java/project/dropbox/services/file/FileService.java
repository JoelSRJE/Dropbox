package project.dropbox.services.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.dropbox.repositories.file.FileRepository;

@Service
@RequiredArgsConstructor
public class FileService  {

    private final FileRepository fileRepository;

}
