package pibackend.domain.bookcovers.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pibackend.domain.auth.role.model.entity.Level;
import pibackend.domain.auth.role.model.entity.Registry;
import pibackend.domain.book.service.BookService;
import pibackend.domain.bookcovers.model.entity.BookCover;
import pibackend.domain.bookcovers.model.mapper.BookCoverMapper;
import pibackend.domain.bookcovers.model.view.BookCoverViewReadList;
import pibackend.domain.bookcovers.model.view.BookCoverViewReadOne;
import pibackend.domain.bookcovers.repository.BookCoverRepository;
import pibackend.infrastructure.PrivilegeService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BookCoverService {

    private final BookCoverRepository repository;

    private final BookCoverMapper mapper;

    private final BookService bookService;

    public Page<BookCoverViewReadList> getPage(String bookId, Pageable pageable) {
        PrivilegeService.checkPrivilege(Registry.BOOK_COVERS, Level.SELECT);
        return repository.findByBook_Uuid(bookId, pageable).map(mapper::toViewReadList);
    }

    public ResponseEntity<InputStreamResource> getOne(Long id) throws IOException {
        PrivilegeService.checkPrivilege(Registry.BOOK_COVERS, Level.SELECT);

        var entity = getObject(id);
        var path = "images/" + entity.getCoverFile();
        var contentType = path.split("\\.")[1].equals("jpg") ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG;
        var resource = new ClassPathResource(path);

        InputStream in = resource.getInputStream();

        return ResponseEntity.ok()
                .contentType(contentType)
                .body(new InputStreamResource(in));
    }

    private BookCover getObject(Long id) {
        PrivilegeService.checkPrivilege(Registry.BOOK_COVERS, Level.SELECT);
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Не найдена обложка с идентификатором: " + id));
    }

    public String create(String bookId, MultipartFile file) throws IOException {
        var internalPath = System.getProperty("user.dir") +
                "/src/main/resources/images/";

        var newFileName = UUID.randomUUID() +
                "." +
                FilenameUtils.getExtension(file.getOriginalFilename());

        var path = Paths.get(internalPath + newFileName);

        try {
            Files.write(path, file.getBytes());
            var entity = new BookCover();
            entity.setCoverFile(newFileName);
            entity.setBook(bookService.getObject(bookId));
            repository.save(entity);
            return "success";
        } catch (IOException e) {
            return "vse propalo: " + e.getCause() + ". " + e.getMessage();
        }
    }

    public void update(Long id, BookCoverViewReadOne view) {
        PrivilegeService.checkPrivilege(Registry.BOOK_COVERS, Level.CUD);
        BookCover entity = mapper.toEntity(getObject(id), view);
        entity.setId(id);
        repository.save(entity);
    }

    public void delete(Long id) {
        PrivilegeService.checkPrivilege(Registry.BOOK_COVERS, Level.CUD);
        getObject(id);
        repository.deleteById(id);
    }
}
