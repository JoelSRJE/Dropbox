package project.dropbox.controllers.file;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.dropbox.dto.file.CreateFileDto;
import project.dropbox.dto.file.DeletedFileDto;
import project.dropbox.dto.file.GetFileDto;
import project.dropbox.dto.file.UpdatedFileDto;
import project.dropbox.models.file.FileEntity;
import project.dropbox.models.user.User;
import project.dropbox.requests.file.*;
import project.dropbox.services.file.FileService;

import java.util.List;
import java.util.UUID;

// Kräver Authorization för att få åtkomst
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/create")
    public ResponseEntity<CreateFileDto> createFile(
            @RequestBody CreateFileRequest request,
            @AuthenticationPrincipal User authenticatedUser
            ) {

        FileEntity theFile = fileService.createFile(request, authenticatedUser.getUserId());

        return ResponseEntity.ok(CreateFileDto.from(theFile));
    }

    @GetMapping("/files")
    public ResponseEntity<List<GetFileDto>> getFiles(
            @AuthenticationPrincipal User authenticatedUser
    ) {
        List<FileEntity> files = fileService.findFilesByOwner(authenticatedUser.getUserId());

       return ResponseEntity.ok(
               files.stream()
                       .map(GetFileDto::from)
                       .toList()
       );
    }

    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<DeletedFileDto> deleteFile(
            @PathVariable UUID fileId,
            @AuthenticationPrincipal User authenticatedUser
            ) {
        FileEntity deletedFile = fileService.deleteFile(fileId, authenticatedUser.getUserId());

        return ResponseEntity.ok(DeletedFileDto.from(deletedFile));

    }

    @GetMapping("/folder/{folderId}")
    public ResponseEntity<List<GetFileDto>> getFilesByfolder(
            @PathVariable UUID folderId,
            @AuthenticationPrincipal User authenticatedUser
            ) {
        return ResponseEntity.ok(
                fileService.findFilesByFolder(folderId, authenticatedUser.getUserId())
                        .stream()
                        .map(GetFileDto::from)
                        .toList()
        );
    }

    @PutMapping("/update/{fileId}")
    public ResponseEntity<UpdatedFileDto> updateFile(
            @PathVariable UUID fileId,
            @RequestBody UpdateFileRequest request,
            @AuthenticationPrincipal User authenticatedUser
    ) {
        FileEntity theFile = fileService.updateFileName(fileId, request, authenticatedUser.getUserId());

        return ResponseEntity.ok(UpdatedFileDto.from(theFile));
    }

}
