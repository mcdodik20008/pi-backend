package pibackend.domain.auth.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pibackend.domain.auth.user.model.mapper.UserMapper;
import pibackend.domain.auth.user.model.view.UserView;
import pibackend.domain.auth.user.repository.UserRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    public Boolean registration(UserView view) {
        var entity = mapper.toEntity(view);
        repository.save(entity);
        return true;
    }

}
