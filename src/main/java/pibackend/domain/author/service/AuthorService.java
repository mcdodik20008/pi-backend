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

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository repository;

    private final AuthorMapper mapper;

    public Page<AuthorViewReadList> getPageByIdLike(Pageable pageable, String id) {
        PrivilegeService.checkPrivilege(Registry.AUTHOR, Level.SELECT);
        return repository.findByUuidContainingIgnoreCase(id, pageable).map(mapper::toViewReadList);
    }

    public Page<AuthorViewReadList> getPageByNameLike(Pageable pageable, String name) {
        PrivilegeService.checkPrivilege(Registry.AUTHOR, Level.SELECT);
        return repository.findByNameContainingIgnoreCase(name, pageable).map(mapper::toViewReadList);
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

    public String create(AuthorViewCreate view) {
        PrivilegeService.checkPrivilege(Registry.AUTHOR, Level.CUD);
        var entity = mapper.toEntity(view);
        return repository.save(entity).getUuid();
    }

    public void update(String id, AuthorViewReadList view) {
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
}
