package pibackend.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "Origin, Auth-Token, X-Requested-With, Content-Type, Accept, Key, remember-me");
        String servletPath = req.getServletPath();
        String method = req.getMethod();
        String clientAuthCode = req.getHeader("Auth-Token");
        List<String> exceptionPaths = Arrays.asList("/login", "/logout", "/user/registration", "/user/change_password");
        if (method.equals("OPTIONS") || servletPath.contains("/swagger-ui") || exceptionPaths.contains(servletPath) || servletPath.contains("v3")) {
            // ЧИЛИМ!!!! (Регаем катку в тарков)
        } else if (clientAuthCode != null && !clientAuthCode.isBlank()) {
            // Проверяем закончилась ли сессия
            Boolean sessionIsValid = securityContext.userIsLogin(clientAuthCode);
            if (!sessionIsValid) {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Время сессии вышло, авторизируйтесь повторно");
                return;
            }
        } else {
            // Если чел не логинился, то домой его!!!!
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Доступ запрещен. Авторизируйтесь");
            return;
        }
        // если мы дошли до сюда, то пользователю можно пытаться делать что-то большее...
        chain.doFilter(request, response);
    }
}