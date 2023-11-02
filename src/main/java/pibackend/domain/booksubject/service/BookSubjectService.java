package pibackend.domain.booksubject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pibackend.domain.booksubject.model.entity.BookSubject;
import pibackend.domain.booksubject.model.mapper.BookSubjectMapper;
import pibackend.domain.booksubject.model.view.BookSubjectView;
import pibackend.domain.booksubject.repository.BookSubjectRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookSubjectService {

    private final BookSubjectRepository repository;

    private final BookSubjectMapper mapper;

    public Page<BookSubjectView> getPageByIdLike(Pageable pageable, Long id) {
        return repository.findByIdContaining(id, pageable).map(mapper::toView);
    }

    public Page<BookSubjectView> getPageBySubjectLike(Pageable pageable, String subject) {
        return repository.findBySubjectContainingIgnoreCase(subject, pageable).map(mapper::toView);
    }

    public Page<BookSubjectView> getPage(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toView);
    }

    public BookSubjectView getOne(Long id) {
        return mapper.toView(getObject(id));
    }

    private BookSubject getObject(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Не найдена тема книги с идентификатором: " + id));
    }

    public Long create(BookSubjectView view) {
        BookSubject entity = mapper.toEntity(view);
        return repository.save(entity).getId();
    }

    public void update(Long id, BookSubjectView view) {
        BookSubject entity = mapper.toEntity(getObject(id), view);
        entity.setId(id);
        repository.save(entity);
    }

    public void delete(Long id) {
        getObject(id);
        repository.deleteById(id);
    }
}
