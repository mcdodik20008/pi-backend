package pibackend.domain.issue.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pibackend.domain.auth.role.model.entity.Level;
import pibackend.domain.auth.role.model.entity.Registry;
import pibackend.domain.issue.model.entity.Issue;
import pibackend.domain.issue.model.mapper.IssueMapper;
import pibackend.domain.issue.model.view.IssueView;
import pibackend.domain.issue.repository.IssueRepository;
import pibackend.infrastructure.PrivilegeService;

import java.util.List;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class IssueService {

    private final IssueMapper mapper;

    private final IssueRepository repository;

    public Page<IssueView> getPage(Pageable pageable) {
        PrivilegeService.checkPrivilege(Registry.ISSUES, Level.SELECT);
        return repository.findAll(pageable).map(mapper::toView);
    }

    public IssueView getOne(Long id) {
        PrivilegeService.checkPrivilege(Registry.ISSUES, Level.SELECT);
        return mapper.toView(getObject(id));
    }

    private Issue getObject(Long id) {
        PrivilegeService.checkPrivilege(Registry.ISSUES, Level.SELECT);
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Не найдена выдача с идентификатором: " + id));
    }

    public Long create(IssueView view) {
        PrivilegeService.checkPrivilege(Registry.ISSUES, Level.CUD);
        Issue entity = mapper.toEntity(view);
        return repository.save(entity).getId();
    }

    public void update(Long id, IssueView view) {
        PrivilegeService.checkPrivilege(Registry.ISSUES, Level.CUD);
        Issue entity = mapper.toEntity(getObject(id), view);
        entity.setId(id);
        repository.save(entity);
    }

    public void delete(Long id) {
        PrivilegeService.checkPrivilege(Registry.ISSUES, Level.CUD);
        getObject(id);
        repository.deleteById(id);
    }

    public List<Issue> getFiltered(String filter) {
        List<Issue> byCustomerName = repository.findByCustomerName(filter);
        if (byCustomerName.size() > 0) return byCustomerName;
        List<Issue> byBookTitle = repository.findByBookTitle(filter);
        if (byBookTitle.size() > 0) return byBookTitle;
        List<Issue> byBookId = repository.findByBookUuid(filter);
        return byBookId;
    }

    public List<Issue> getList() {
        return repository.findAll();
    }

    public Page<IssueView> getPageFiltered(Pageable pageable, String filter) {
        Page<IssueView> byCustomerName = repository.findByCustomerName(filter, pageable).map(mapper::toView);
        if (byCustomerName.hasContent()) return byCustomerName;
        Page<IssueView> byBookTitle = repository.findByBookTitle(filter, pageable).map(mapper::toView);
        if (byBookTitle.hasContent()) return byBookTitle;
        Page<IssueView> byBookId = repository.findByBookUuid(filter, pageable).map(mapper::toView);
        return byBookId;
    }

    public Page<IssueView> getHistoryPageFiltered(Pageable pageable, String filter) {
        Page<IssueView> byCustomerName = repository.findByCustomerNameAndDateOfReturnIsNotNull(filter, pageable).map(mapper::toView);
        if (byCustomerName.hasContent()) return byCustomerName;
        Page<IssueView> byBookTitle = repository.findByBookTitleAndDateOfReturnIsNotNull(filter, pageable).map(mapper::toView);
        if (byBookTitle.hasContent()) return byBookTitle;
        Page<IssueView> byBookId = repository.findByBookUuidAndDateOfReturnIsNotNull(filter, pageable).map(mapper::toView);
        return byBookId;
    }

    public Page<IssueView> getHistoryPage(Pageable pageable) {
        // return repository.findAllAndDateOfReturnIsNotNull(pageable).map(mapper::toView);
        return null;
    }
}
