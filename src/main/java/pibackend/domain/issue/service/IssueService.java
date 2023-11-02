package pibackend.domain.issue.service;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pibackend.domain.issue.model.entity.Issue;
import pibackend.domain.issue.model.mapper.IssueMapper;
import pibackend.domain.issue.model.view.IssueView;
import pibackend.domain.issue.repository.IssueRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class IssueService {

    private final IssueMapper mapper;

    private final IssueRepository repository;

    public Page<IssueView> getPageByIdLike(Pageable pageable, Long id) {
        return repository.findByIdContaining(id, pageable).map(mapper::toView);
    }

    public Page<IssueView> getPage(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toView);
    }

    public IssueView getOne(Long id) {
        return mapper.toView(getObject(id));
    }

    private Issue getObject(Long id) {
        return repository.findById(id)
            .orElseThrow(() ->
                new RuntimeException("Не найдена выдача с идентификатором: " + id));
    }

    public Long create(IssueView view) {
        Issue entity = mapper.toEntity(view);
        return repository.save(entity).getId();
    }

    public void update(Long id, IssueView view) {
        Issue entity = mapper.toEntity(getObject(id), view);
        entity.setId(id);
        repository.save(entity);
    }

    public void delete(Long id) {
        getObject(id);
        repository.deleteById(id);
    }    
}
