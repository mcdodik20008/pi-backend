package pibackend.domain.auth.user.service;

import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pibackend.domain.auth.user.model.mapper.UserMapper;
import pibackend.domain.auth.user.model.view.UserView;
import pibackend.domain.auth.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    public Boolean registration(UserView view) {
        var entity = mapper.toEntity(view);
        if (repository.existsByLogin(entity.getLogin())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Пользователь с таким логином уже существует.");
        }
        entity.setPassword(
                Hashing.sha256()
                .hashString(entity.getPassword(), StandardCharsets.UTF_8)
                .toString());
        repository.save(entity);
        return true;
    }

}
