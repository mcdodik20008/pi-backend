package pibackend.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pibackend.domain.auth.role.model.entity.Level;
import pibackend.domain.auth.role.model.entity.Registry;
import pibackend.domain.book.model.entity.Book;
import pibackend.domain.book.model.mapper.BookMapper;
import pibackend.domain.book.model.view.BookViewReadList;
import pibackend.domain.book.model.view.BookViewReadOne;
import pibackend.domain.book.repository.BookRepository;
import pibackend.infrastructure.PrivilegeService;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    private final BookMapper mapper;

    public Page<BookViewReadList> getPageByIdLike(Pageable pageable, String id) {
        PrivilegeService.checkPrivilege(Registry.BOOK, Level.SELECT);
        return repository.findByUuidContainingIgnoreCase(id, pageable).map(mapper::toViewReadList);
    }

    public Page<BookViewReadList> getPageByTitleLike(Pageable pageable, String title) {
        PrivilegeService.checkPrivilege(Registry.BOOK, Level.SELECT);
        return repository.findByTitleContainingIgnoreCase(title, pageable).map(mapper::toViewReadList);
    }

    public Page<BookViewReadList> getPage(Pageable pageable) {
        PrivilegeService.checkPrivilege(Registry.BOOK, Level.SELECT);
        return repository.findAll(pageable).map(mapper::toViewReadList);
    }

    public BookViewReadOne getOne(String id) {
        PrivilegeService.checkPrivilege(Registry.BOOK, Level.SELECT);
        return mapper.toViewReadOne(getObject(id));
    }

    private Book getObject(String id) {
        PrivilegeService.checkPrivilege(Registry.BOOK, Level.SELECT);
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Не найдена книга с идентификатором: " + id));
    }

    public String create(BookViewReadList view) {
        PrivilegeService.checkPrivilege(Registry.BOOK, Level.CUD);
        Book entity = mapper.toEntity(view);
        return repository.save(entity).getUuid();
    }

    public void update(String id, BookViewReadList view) {
        PrivilegeService.checkPrivilege(Registry.BOOK, Level.CUD);
        Book entity = mapper.toEntity(getObject(id), view);
        entity.setUuid(id);
        repository.save(entity);
    }

    public void delete(String id) {
        PrivilegeService.checkPrivilege(Registry.BOOK, Level.CUD);
        getObject(id);
        repository.deleteById(id);
    }

}
