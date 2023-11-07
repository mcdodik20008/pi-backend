package pibackend.domain.dataimport.author.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import pibackend.domain.dataimport.author.service.ImportAuthorService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/import", produces = "application/json")
public class ImportAuthorController {
    
    private final ImportAuthorService service;

    @PostMapping("/author")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            service.save(file);
            String message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            String message = "Could not upload the file: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }
    
}
