package pibackend.domain.bookcovers.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pibackend.domain.auth.role.model.entity.Level;
import pibackend.domain.auth.role.model.entity.Registry;
import pibackend.domain.bookcovers.model.entity.BookCover;
import pibackend.domain.bookcovers.model.mapper.BookCoverMapper;
import pibackend.domain.bookcovers.model.view.BookCoverViewReadList;
import pibackend.domain.bookcovers.model.view.BookCoverViewReadOne;
import pibackend.domain.bookcovers.repository.BookCoverRepository;
import pibackend.infrastructure.PrivilegeService;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookCoverService {

    private final BookCoverRepository repository;

    private final BookCoverMapper mapper;

    public Page<BookCoverViewReadList> getPage(Pageable pageable) {
        PrivilegeService.checkPrivilege(Registry.BOOK_COVERS, Level.SELECT);
        return repository.findAll(pageable).map(mapper::toViewReadList);
    }

    public BookCoverViewReadOne getOne(Long id) {
        PrivilegeService.checkPrivilege(Registry.BOOK_COVERS, Level.SELECT);
        return mapper.toViewReadOne(getObject(id));
    }

    private BookCover getObject(Long id) {
        PrivilegeService.checkPrivilege(Registry.BOOK_COVERS, Level.SELECT);
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Не найдена обложка с идентификатором: " + id));
    }

    public Long create(BookCoverViewReadOne view) {
        PrivilegeService.checkPrivilege(Registry.BOOK_COVERS, Level.CUD);
        BookCover entity = mapper.toEntity(view);
        return repository.save(entity).getId();
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
