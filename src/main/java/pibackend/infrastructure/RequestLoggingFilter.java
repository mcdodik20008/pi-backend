package pibackend.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
@Component
public class RequestLoggingFilter implements Filter {

    @Autowired
    private SecurityContext securityContext;

    private ServletContext servletContext;

    public void init(FilterConfig fConfig) {
        this.servletContext = fConfig.getServletContext();
        this.servletContext.log("RequestLoggingFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String clientAuthCode = req.getHeader("ДоговоримсяКакой");
        String servletPath = req.getServletPath();
        boolean swagger = servletPath.equals("/swagger-ui/index.html") || servletPath.equals("/swagger-ui.html") || servletPath.contains("v3");
        boolean loginOrLogout = servletPath.equals("login") || servletPath.equals("logout");
        if (loginOrLogout || swagger) {
            // ЧИЛИМ!!!! (Регаем катку в тарков)
        } else if (clientAuthCode != null && !clientAuthCode.isBlank()) {
            // Проверяем закончилась ли сессия
            Boolean sessionIsValid = securityContext.userIsLogin(clientAuthCode);
            if (!sessionIsValid) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Время сессии вышло, авторизируйтесь повторно");
            }
        } else {
            // Если чел не логинился, то домой его!!!!
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Доступ запрешен. Авторизируйтесь");
        }

        // если мы дошли до сюда, то пользователю можно пытаться делать что-то большее...
        chain.doFilter(request, response);
    }

    public void destroy() {
        //we can close resources here
    }

}