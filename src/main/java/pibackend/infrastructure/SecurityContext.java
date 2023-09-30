package pibackend.infrastructure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Component
public class SecurityContext {

    private final Map<String, Pair<LocalDate, SecurityProperties.User>> authUsers = new HashMap<>();

    private static SecurityProperties.User currentUser;

    public Boolean userIsLogin(String clientAuthCode) {
        // Логинился ли у нас пользователь
        if (authUsers.containsKey(clientAuthCode)) {
            Pair<LocalDate, SecurityProperties.User> pair = authUsers.get(clientAuthCode);
            Duration duration = Duration.between(pair.getFirst(), LocalDate.now());
            if (duration.get(ChronoUnit.MINUTES) < 30) {
                currentUser = pair.getSecond();
                authUsers.replace(clientAuthCode, Pair.of(LocalDate.now(), pair.getSecond()));
                return true;
            }
            authUsers.remove(clientAuthCode);
        }
        return false;
    }

    public void addLoginUser(String clientAuthCode, SecurityProperties.User user){
        if (authUsers.containsKey(clientAuthCode)){
            authUsers.put(clientAuthCode, Pair.of(LocalDate.now(), user));
        } else{
            authUsers.replace(clientAuthCode, Pair.of(LocalDate.now(), user));
        }
    }

    public void removeUser(String clientAuthCode){
        authUsers.remove(clientAuthCode);
    }
}
