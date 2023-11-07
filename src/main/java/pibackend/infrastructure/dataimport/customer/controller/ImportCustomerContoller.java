package pibackend.infrastructure.dataimport.customer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pibackend.infrastructure.dataimport.customer.service.ImportCustomerService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/import", produces = "application/json")
public class ImportCustomerContoller {

    private final ImportCustomerService service;

    @PostMapping("/customer")
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
