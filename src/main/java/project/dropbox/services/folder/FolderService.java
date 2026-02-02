package project.dropbox.services.folder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.dropbox.repositories.folder.FolderRepository;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;

}
