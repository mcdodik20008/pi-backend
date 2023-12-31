package pibackend.domain.booksubject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pibackend.domain.auth.role.model.entity.Level;
import pibackend.domain.auth.role.model.entity.Registry;
import pibackend.domain.book.model.entity.Book;
import pibackend.domain.book.repository.BookRepository;
import pibackend.domain.booksubject.model.entity.BookSubject;
import pibackend.domain.booksubject.model.mapper.BookSubjectMapper;
import pibackend.domain.booksubject.model.view.BookSubjectViewReadList;
import pibackend.domain.booksubject.model.view.BookSubjectViewReadOne;
import pibackend.domain.booksubject.repository.BookSubjectRepository;
import pibackend.infrastructure.PrivilegeService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class BookSubjectService {

    private final BookSubjectRepository repository;

    private final BookSubjectMapper mapper;
    private final BookRepository bookRepository;

    public Page<BookSubjectViewReadList> getPageFiltered(Pageable pageable, String filter) {
        PrivilegeService.checkPrivilege(Registry.BOOK_SUBJECT, Level.SELECT);
        return repository.findBySubjectContainingIgnoreCase(filter, pageable).map(mapper::toViewReadList);
    }

    public Page<BookSubjectViewReadList> getPage(Pageable pageable) {
        PrivilegeService.checkPrivilege(Registry.BOOK_SUBJECT, Level.SELECT);
        return repository.findAll(pageable).map(mapper::toViewReadList);
    }

    public BookSubjectViewReadOne getOne(Long id) {
        PrivilegeService.checkPrivilege(Registry.BOOK_SUBJECT, Level.SELECT);
        return mapper.toViewReadOne(getObject(id));
    }

    private BookSubject getObject(Long id) {
        PrivilegeService.checkPrivilege(Registry.BOOK_SUBJECT, Level.SELECT);
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Не найдена тема книги с идентификатором: " + id));
    }

    public Long create(BookSubjectViewReadOne view) {
        PrivilegeService.checkPrivilege(Registry.BOOK_SUBJECT, Level.CUD);
        BookSubject entity = mapper.toEntity(view);
        return repository.save(entity).getId();
    }

    public List<BookSubject> saveAll(String bookId, List<BookSubjectViewReadList> subjects) {
        PrivilegeService.checkPrivilege(Registry.BOOK_SUBJECT, Level.CUD);
        Book book = bookRepository.findById(bookId).get();
        Stream<BookSubject> stream = subjects.stream().map(mapper::toEntity);
        stream.forEach(x -> x.setBook(book));
        return repository.saveAll(stream.toList());
    }

    public void update(Long id, BookSubjectViewReadOne view) {
        PrivilegeService.checkPrivilege(Registry.BOOK_SUBJECT, Level.CUD);
        BookSubject entity = mapper.toEntity(getObject(id), view);
        entity.setId(id);
        repository.save(entity);
    }

    public void delete(Long id) {
        PrivilegeService.checkPrivilege(Registry.BOOK_SUBJECT, Level.CUD);
        getObject(id);
        repository.deleteById(id);
    }
}
