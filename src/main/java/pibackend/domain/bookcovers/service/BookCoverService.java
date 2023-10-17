package pibackend.domain.bookcovers.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pibackend.domain.bookcovers.model.entity.BookCover;
import pibackend.domain.bookcovers.model.mapper.BookCoverMapper;
import pibackend.domain.bookcovers.model.view.BookCoverView;
import pibackend.domain.bookcovers.repository.BookCoverRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookCoverService {

    private final BookCoverRepository repository;

    private final BookCoverMapper mapper;

    public Page<BookCoverView> getList(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toView);
    }

    public BookCoverView getOne(Long id) {
        return mapper.toView(getObject(id));
    }

    private BookCover getObject(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Не найдена обложка с идентификатором: " + id));
    }

    public Long create(BookCoverView view) {
        BookCover entity = mapper.toEntity(view);
        return repository.save(entity).getId();
    }

    public void update(Long id, BookCoverView view) {
        BookCover entity = mapper.toEntity(getObject(id), view);
        entity.setId(id);
        repository.save(entity);
    }

    public void delete(Long id) {
        getObject(id);
        repository.deleteById(id);
    }
}
