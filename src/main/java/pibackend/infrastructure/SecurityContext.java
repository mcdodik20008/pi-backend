package pibackend.infrastructure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import pibackend.domain.auth.user.model.entity.User;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Component
public class SecurityContext {

    private final Map<String, Pair<LocalDateTime, User>> authUsers = new HashMap<>();

    public static User currentUser = null;

    public Boolean userIsLogin(String clientAuthCode) {
        // Логинился ли у нас пользователь
        if (authUsers.containsKey(clientAuthCode)) {
            Pair<LocalDateTime, User> pair = authUsers.get(clientAuthCode);
            Duration duration = Duration.between(pair.getFirst(), LocalDateTime.now());
            if (duration.toMinutes() < 30) {
                currentUser = pair.getSecond();
                authUsers.replace(clientAuthCode, Pair.of(LocalDateTime.now(), pair.getSecond()));
                return true;
            }
            authUsers.remove(clientAuthCode);
        }
        return false;
    }

    public void addLoginUser(String clientAuthCode, User user) {
        if (!authUsers.containsKey(clientAuthCode)) {
            authUsers.put(clientAuthCode, Pair.of(LocalDateTime.now(), user));
        } else {
            authUsers.replace(clientAuthCode, Pair.of(LocalDateTime.now(), user));
        }
    }

    public void removeUser(String clientAuthCode) {
        authUsers.remove(clientAuthCode);
    }

    public void removeUserByLogin(String login) {
        for (String key : authUsers.keySet()) {
            if (authUsers.get(key).getSecond().getLogin().equals(login)){
                authUsers.remove(key);
            }
        }

    }
}
