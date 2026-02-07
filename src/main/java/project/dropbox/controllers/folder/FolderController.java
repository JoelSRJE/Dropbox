package project.dropbox.controllers.folder;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.dropbox.dto.folder.DeletedFolderDto;
import project.dropbox.dto.folder.GetFolderDto;
import project.dropbox.dto.folder.NewFolderDto;
import project.dropbox.dto.folder.UpdateFolderDto;
import project.dropbox.models.folder.FolderEntity;
import project.dropbox.models.user.User;
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

    @PostMapping("/create")
    public ResponseEntity<NewFolderDto> createFolder(
            @RequestBody CreateFolderRequest request,
            @AuthenticationPrincipal User authenticatedUser
            ) {
        FolderEntity folder = folderService.createFolder(request, authenticatedUser.getUserId());

        return ResponseEntity.ok(NewFolderDto.from(folder));
    }

    @GetMapping("/folders")
    public ResponseEntity<List<GetFolderDto>> getAllFoldersForUser(
            @AuthenticationPrincipal User authenticatedUser
            ) {
        return ResponseEntity.ok(folderService.getAllFoldersWithFilesByUser(authenticatedUser.getUserId())
                .stream()
                .map(GetFolderDto::from)
                .toList());
    }

    @PutMapping("/update/{folderId}")
    public ResponseEntity<UpdateFolderDto> updateFolder(
            @PathVariable UUID folderId,
            @RequestBody UpdateFolderRequest request,
            @AuthenticationPrincipal User authenticatedUser
    ) {
        FolderEntity updatedFolder = folderService.updateFolderName(folderId, request, authenticatedUser.getUserId());

        return ResponseEntity.ok(UpdateFolderDto.from(updatedFolder));
    }

    @DeleteMapping("/delete/{folderId}")
    public ResponseEntity<DeletedFolderDto> deleteFolder(
            @PathVariable UUID folderId,
            @AuthenticationPrincipal User authenticatedUser
    ) {
        FolderEntity deletedFolder = folderService.deleteFolder(folderId, authenticatedUser.getUserId());

        return ResponseEntity.ok(DeletedFolderDto.from(deletedFolder));
    }
}
