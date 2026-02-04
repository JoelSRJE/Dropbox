package project.dropbox.controllers.file;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dropbox.dto.file.CreateFileDto;
import project.dropbox.dto.file.DeletedFileDto;
import project.dropbox.dto.file.GetFileDto;
import project.dropbox.dto.file.UpdatedFileDto;
import project.dropbox.models.file.FileEntity;
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
            @RequestBody CreateFileRequest request
            ) {
        FileEntity newFile = new FileEntity(request.fileName(), request.data(), request.folder(), request.fileOwner());

        FileEntity theFile = fileService.createFile(newFile);

        return ResponseEntity.ok(CreateFileDto.from(theFile));
    }

    @GetMapping("/files")
    public ResponseEntity<GetFileDto> findFileByIdAndOwner(
            @RequestBody GetFileRequest request
    ) {
        FileEntity theFile = fileService.findFileByIdAndOwner(request.fileId(), request.fileOwner());

        return ResponseEntity.ok(GetFileDto.from(theFile));
    }

    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<DeletedFileDto> deleteFile(
            @PathVariable UUID fileId,
            @RequestParam UUID ownerId
            ) {
        FileEntity deletedFile = fileService.deleteFile(fileId, ownerId);

        return ResponseEntity.ok(DeletedFileDto.from(deletedFile));

    }

    @GetMapping("/folder/{folderId}")
    public ResponseEntity<List<GetFileDto>> getFilesByfolder(
            @PathVariable UUID folderId,
            @RequestParam UUID ownerId
            ) {
        return ResponseEntity.ok(
                fileService.findFilesByFolder(folderId, ownerId)
                        .stream()
                        .map(GetFileDto::from)
                        .toList()
        );
    }

    @PutMapping("/update/{fileId}")
    public ResponseEntity<UpdatedFileDto> updateFile(
            @PathVariable UUID fileId,
            @RequestBody UpdateFileRequest request
    ) {
        FileEntity theFile = fileService.updateFileName(fileId, request);

        return ResponseEntity.ok(UpdatedFileDto.from(theFile));
    }

}
