package pibackend.domain.author.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pibackend.domain.author.model.mapper.AuthorMapper;
import pibackend.domain.author.model.view.AuthorView;
import pibackend.domain.author.repository.AuthorRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository repository;

    private final AuthorMapper mapper;

    public List<AuthorView> getList() {
        return repository.findAll().stream()
                .map(mapper::toView)
                .toList();
    }
}
