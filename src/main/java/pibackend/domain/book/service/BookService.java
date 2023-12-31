package pibackend.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pibackend.domain.auth.role.model.entity.Level;
import pibackend.domain.auth.role.model.entity.Registry;
import pibackend.domain.author.model.entity.Author;
import pibackend.domain.author.repository.AuthorRepository;
import pibackend.domain.book.model.entity.Book;
import pibackend.domain.book.model.mapper.BookMapper;
import pibackend.domain.book.model.view.BookViewCreate;
import pibackend.domain.book.model.view.BookViewReadList;
import pibackend.domain.book.model.view.BookViewReadOne;
import pibackend.domain.book.repository.BookRepository;
import pibackend.domain.booksubject.service.BookSubjectService;
import pibackend.infrastructure.PrivilegeService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookSubjectService bookSubjectService;

    private final AuthorRepository authorRepository;
    private final BookRepository repository;

    private final BookMapper mapper;

    public Page<BookViewReadList> getPageFiltered(Pageable pageable, String filter) {
        PrivilegeService.checkPrivilege(Registry.BOOK, Level.SELECT);
        Page<BookViewReadList> byId = repository.findByUuidContainingIgnoreCase(filter, pageable).map(mapper::toViewReadList);
        if (byId.hasContent()) return byId;
        return repository.findByTitleContainingIgnoreCase(filter, pageable).map(mapper::toViewReadList);
    }

    public Page<BookViewReadList> getPage(Pageable pageable) {
        PrivilegeService.checkPrivilege(Registry.BOOK, Level.SELECT);
        return repository.findAll(pageable).map(mapper::toViewReadList);
    }

    public BookViewReadOne getOne(String id) {
        PrivilegeService.checkPrivilege(Registry.BOOK, Level.SELECT);
        Book book = getObject(id);
        return mapper.toViewReadOne(book);
    }

    public Book getObject(String id) {
        PrivilegeService.checkPrivilege(Registry.BOOK, Level.SELECT);
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Не найдена книга с идентификатором: " + id));
    }

    public String create(BookViewCreate view) {
        PrivilegeService.checkPrivilege(Registry.BOOK, Level.CUD);
        Book entity = mapper.toEntity(view);
        if (view.getAuthors() != null) {
            for (var auth : view.getAuthors()) {
                List<Author> authors = entity.getAuthors();
                authors.add(authorRepository.findById(auth.getUuid()).get());
            }
        }
        String bookId = repository.save(entity).getUuid();
        bookSubjectService.saveAll(bookId, view.getSubjects());
        return bookId;
    }

    public void update(String id, BookViewCreate view) {
        PrivilegeService.checkPrivilege(Registry.BOOK, Level.CUD);
        Book entity = mapper.toEntity(getObject(id), view);
        if (view.getAuthors() != null) {
            entity.setAuthors(new ArrayList<>());
            for (var auth : view.getAuthors()) {
                List<Author> authors = entity.getAuthors();
                authors.add(authorRepository.findById(auth.getUuid()).get());
            }
        }
        entity.setUuid(id);
        repository.save(entity);
    }

    public void delete(String id) {
        PrivilegeService.checkPrivilege(Registry.BOOK, Level.CUD);
        getObject(id);
        repository.deleteById(id);
    }

    public List<Book> getList() {
        PrivilegeService.checkPrivilege(Registry.REPORT, Level.SELECT);
        return repository.findAll();
    }
}
