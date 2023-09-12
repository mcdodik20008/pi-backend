package pibackend.domain.bookcovers.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pibackend.domain.bookcovers.model.mapper.BookCoverMapper;
import pibackend.domain.bookcovers.model.view.BookCoverView;
import pibackend.domain.bookcovers.repository.BookCoverRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookCoverService {

    private final BookCoverRepository repository;

    private final BookCoverMapper mapper;

    public List<BookCoverView> getList() {
        return repository.findAll().stream()
                .map(mapper::toView)
                .toList();
    }
}
