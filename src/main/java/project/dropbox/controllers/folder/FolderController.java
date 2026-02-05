package project.dropbox.controllers.folder;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dropbox.dto.folder.DeletedFolderDto;
import project.dropbox.dto.folder.NewFolderDto;
import project.dropbox.dto.folder.UpdateFolderDto;
import project.dropbox.models.folder.FolderEntity;
import project.dropbox.repositories.folder.FolderRepository;
import project.dropbox.requests.folder.CreateFolderRequest;
import project.dropbox.requests.folder.UpdateFolderRequest;
import project.dropbox.services.folder.FolderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/folder")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;
    private final FolderRepository folderRepository;

    @PostMapping("/create")
    public ResponseEntity<NewFolderDto> createFolder(
            @RequestBody CreateFolderRequest request
            ) {
        FolderEntity folder = folderService.createFolder(request);

        return ResponseEntity.ok(NewFolderDto.from(folder));
    }

    @GetMapping("/folders/{ownerId}")
    public ResponseEntity<List<NewFolderDto>> getAllFoldersForUser(
            @PathVariable UUID ownerId
            ) {
        return ResponseEntity.ok(folderService.getAllFoldersByUser(ownerId)
                .stream()
                .map(NewFolderDto::from)
                .toList());
    }

    @PutMapping("/update/{folderId}")
    public ResponseEntity<UpdateFolderDto> updateFolder(
            @PathVariable UUID folderId,
            @RequestBody UpdateFolderRequest request
    ) {
        FolderEntity updatedFolder = folderService.updateFolderName(folderId, request);

        return ResponseEntity.ok(UpdateFolderDto.from(updatedFolder));
    }

    @DeleteMapping("/delete/{folderId}")
    public ResponseEntity<DeletedFolderDto> deleteFolder(
            @PathVariable UUID folderId,
            @RequestParam UUID ownerId
    ) {
        FolderEntity deletedFolder = folderService.deleteFolder(folderId, ownerId);

        return ResponseEntity.ok(DeletedFolderDto.from(deletedFolder));
    }
}
