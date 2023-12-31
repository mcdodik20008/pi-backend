package pibackend.domain.issue.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pibackend.domain.auth.role.model.entity.Level;
import pibackend.domain.auth.role.model.entity.Registry;
import pibackend.domain.issue.model.entity.Issue;
import pibackend.domain.issue.model.mapper.IssueMapper;
import pibackend.domain.issue.model.view.IssueView;
import pibackend.domain.issue.repository.IssueRepository;
import pibackend.infrastructure.PrivilegeService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

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
                        new ResponseStatusException(HttpStatus.CONFLICT, "Не найдена выдача с идентификатором: " + id));
    }

    public Long create(IssueView view) {
        PrivilegeService.checkPrivilege(Registry.ISSUES, Level.CUD);
        if (repository.countActiveIssue(view.getCustomer().getId()) > 5) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Невозможно выдать. У клиента 5 активных выдач");
        }
        Issue entity = mapper.toEntity(view);
        return repository.save(entity).getId();
    }

    public void update(Long id, IssueView view) {
        PrivilegeService.checkPrivilege(Registry.ISSUES, Level.CUD);
        LocalDate oldDate = getObject(id).getReturnUntil();
        Issue entity = mapper.toEntity(getObject(id), view);
        entity.setId(id);
        if (!entity.getReturnUntil().equals(oldDate)) {
            if (entity.getWasUpdated()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Невозможно изменить дату выдачи. Дата выдачи уже была изменена.");
            } else {
                entity.setWasUpdated(true);
            }
        }
        repository.save(entity);
    }

    public void delete(Long id) {
        PrivilegeService.checkPrivilege(Registry.ISSUES, Level.CUD);
        getObject(id);
        repository.deleteById(id);
    }

    public List<Issue> getListFiltered(String filter) {
        PrivilegeService.checkPrivilege(Registry.REPORT, Level.SELECT);
        List<Issue> byCustomerName = repository.findByCustomerName(filter);
        if (!byCustomerName.isEmpty()) return byCustomerName;
        List<Issue> byBookTitle = repository.findByBookTitleOrderByCustomerNameAsc(filter);
        if (!byBookTitle.isEmpty()) return byBookTitle;
        return repository.findByBookUuidOrderByCustomerNameAsc(filter);
    }

    public List<Issue> getList() {
        PrivilegeService.checkPrivilege(Registry.REPORT, Level.SELECT);
        return repository.findAll();
    }

    public Page<IssueView> getPageFiltered(Pageable pageable, String filter) {
        PrivilegeService.checkPrivilege(Registry.ISSUES, Level.SELECT);
        Page<IssueView> byCustomerName = repository.findByCustomerName(filter, pageable).map(mapper::toView);
        if (byCustomerName.hasContent()) return byCustomerName;
        Page<IssueView> byBookTitle = repository.findByBookTitle(filter, pageable).map(mapper::toView);
        if (byBookTitle.hasContent()) return byBookTitle;
        return repository.findByBookUuid(filter, pageable).map(mapper::toView);
    }

    public Page<IssueView> getHistoryPageFiltered(Pageable pageable, String filter) {
        PrivilegeService.checkPrivilege(Registry.ISSUES, Level.SELECT);
        Page<IssueView> byCustomerName = repository.findByCustomerNameAndDateOfReturnIsNotNullOrderByDateOfIssue(filter, pageable).map(mapper::toView);
        if (byCustomerName.hasContent()) return byCustomerName;
        Page<IssueView> byBookTitle = repository.findByBookTitleAndDateOfReturnIsNotNullOrderByDateOfIssue(filter, pageable).map(mapper::toView);
        if (byBookTitle.hasContent()) return byBookTitle;
        return repository.findByBookUuidAndDateOfReturnIsNotNull(filter, pageable).map(mapper::toView);
    }

    public Page<IssueView> getHistoryPage(Pageable pageable) {
        PrivilegeService.checkPrivilege(Registry.ISSUES, Level.SELECT);
        return repository.findByDateOfReturnIsNotNull(pageable).map(mapper::toView);
    }

    public Page<IssueView> getActualPageFiltered(Pageable pageable, String filter) {
        PrivilegeService.checkPrivilege(Registry.ISSUES, Level.SELECT);
        Page<IssueView> byCustomerName = repository.findByCustomerNameAndDateOfReturnIsNull(filter, pageable).map(mapper::toView);
        if (byCustomerName.hasContent()) return byCustomerName;
        Page<IssueView> byBookTitle = repository.findByBookTitleAndDateOfReturnIsNull(filter, pageable).map(mapper::toView);
        if (byBookTitle.hasContent()) return byBookTitle;
        return repository.findByBookUuidAndDateOfReturnIsNull(filter, pageable).map(mapper::toView);
    }

    public Page<IssueView> getActualPage(Pageable pageable) {
        PrivilegeService.checkPrivilege(Registry.ISSUES, Level.SELECT);
        return repository.findByDateOfReturnIsNull(pageable).map(mapper::toView);
    }
}
