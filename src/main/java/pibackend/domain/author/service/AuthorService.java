package pibackend.domain.author.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pibackend.domain.auth.role.model.entity.Level;
import pibackend.domain.auth.role.model.entity.Registry;
import pibackend.domain.author.model.entity.Author;
import pibackend.domain.author.model.mapper.AuthorMapper;
import pibackend.domain.author.model.view.AuthorViewCreate;
import pibackend.domain.author.model.view.AuthorViewReadList;
import pibackend.domain.author.model.view.AuthorViewReadOne;
import pibackend.domain.author.repository.AuthorRepository;
import pibackend.infrastructure.PrivilegeService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository repository;

    private final AuthorMapper mapper;

    public Page<AuthorViewReadList> getPageFiltered(Pageable pageable, String filter) {
        PrivilegeService.checkPrivilege(Registry.AUTHOR, Level.SELECT);
        Page<AuthorViewReadList> byId = repository.findByUuidContainingIgnoreCase(filter, pageable).map(mapper::toViewReadList);
        if (byId.hasContent()) return byId;
        return repository.findByNameContainingIgnoreCase(filter, pageable).map(mapper::toViewReadList);
    }

    public Page<AuthorViewReadList> getPage(Pageable pageable) {
        PrivilegeService.checkPrivilege(Registry.AUTHOR, Level.SELECT);
        return repository.findAll(pageable).map(mapper::toViewReadList);
    }

    public AuthorViewReadOne getOne(String id) {
        PrivilegeService.checkPrivilege(Registry.AUTHOR, Level.SELECT);
        return mapper.toViewReadOne(getObject(id));
    }

    public Author getObject(String id) {
        PrivilegeService.checkPrivilege(Registry.AUTHOR, Level.SELECT);
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Не найден автор с идентификатором: " + id));
    }

    public AuthorViewCreate create(AuthorViewCreate view) {
        PrivilegeService.checkPrivilege(Registry.AUTHOR, Level.CUD);
        var entity = mapper.toEntity(view);
        if (view.getBirthDate() != null) {
            var birthDate = view.getBirthDate().split("T")[0];
            entity.setBirthDate(birthDate);
        }
        if (view.getDeathDate() != null) {
            var deathDate = view.getDeathDate().split("T")[0];
            entity.setDeathDate(deathDate);
        }
        return mapper.toViewCreate(repository.save(entity));
    }

    public void update(String id, AuthorViewReadOne view) {
        PrivilegeService.checkPrivilege(Registry.AUTHOR, Level.CUD);
        Author entity = mapper.toEntity(getObject(id), view);
        entity.setUuid(id);
        repository.save(entity).getUuid();
    }

    public void delete(String id) {
        PrivilegeService.checkPrivilege(Registry.AUTHOR, Level.CUD);
        getObject(id);
        repository.deleteById(id);
    }

    public List<Author> getList() {
        PrivilegeService.checkPrivilege(Registry.AUTHOR, Level.SELECT);
        return repository.findAll();
    }
}
