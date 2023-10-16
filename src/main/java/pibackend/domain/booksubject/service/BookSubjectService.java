package pibackend.domain.booksubject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import pibackend.domain.booksubject.model.entity.BookSubject;
import pibackend.domain.booksubject.model.mapper.BookSubjectMapper;
import pibackend.domain.booksubject.model.view.BookSubjectView;
import pibackend.domain.booksubject.repository.BookSubjectRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookSubjectService {

    private final BookSubjectRepository repository;

    private final BookSubjectMapper mapper;

    public List<BookSubjectView> getList() {
        return repository.findAll().stream()
                .map(mapper::toView)
                .toList();
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
        repository.save(entity).getId();
    }

    public void delete(Long id) {
        getObject(id);
        repository.deleteById(id);
    }
}
