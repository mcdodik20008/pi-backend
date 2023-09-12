package pibackend.domain.booksubject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
}
