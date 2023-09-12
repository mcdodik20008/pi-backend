package pibackend.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pibackend.domain.book.model.mapper.BookMapper;
import pibackend.domain.book.model.view.BookViewReadList;
import pibackend.domain.book.repository.BookRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    private final BookMapper mapper;

    public List<BookViewReadList> getList() {
        return repository.findAll().stream()
                .map(mapper::toViewReadList)
                .toList();
    }
}
