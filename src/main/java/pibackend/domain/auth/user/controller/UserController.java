package pibackend.domain.auth.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pibackend.domain.auth.role.model.entity.Level;
import pibackend.domain.auth.role.model.entity.Registry;
import pibackend.domain.auth.user.model.entity.User;
import pibackend.domain.auth.user.model.view.UserChangePassword;
import pibackend.domain.auth.user.model.view.UserChangePasswordNoConfirmation;
import pibackend.domain.auth.user.model.view.UserView;
import pibackend.domain.auth.user.model.view.UserViewCreate;
import pibackend.domain.auth.user.service.UserService;
import pibackend.infrastructure.PrivilegeService;
import pibackend.infrastructure.SecurityContext;
import pibackend.infrastructure.export.UserExcelExporter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user", produces = "application/json")
public class UserController {

    private final UserService service;

    @GetMapping
    public Page<UserView> getList(Pageable pageable,
                                  @RequestParam(required = false) String filter) {
        PrivilegeService.checkPrivilege(Registry.USER, Level.SELECT);
        return service.getPage(pageable, filter);
    }

    @Transactional
    @GetMapping("/currentUser")
    public UserView getCurrentUser() {
        User user = SecurityContext.currentUser;
        return service.getOne(user.getLogin());
    }

    @GetMapping("/{login}")
    public UserView getOne(@PathVariable String login) {
        PrivilegeService.checkPrivilege(Registry.USER, Level.SELECT);
        return service.getOne(login);
    }

    @PostMapping("/registration")
    public Boolean registration(@RequestBody UserViewCreate user) {
        PrivilegeService.checkPrivilege(Registry.USER, Level.CUD);
        return service.registration(user);
    }

    @PatchMapping("/change_password")
    public Boolean changePassword(@RequestBody UserChangePassword view) {
        return service.changePassword(view);
    }

    @PatchMapping("/change_password_admin")
    public Boolean changePasswordAdmin(@RequestBody UserChangePasswordNoConfirmation view) {
        PrivilegeService.checkPrivilege(Registry.USER, Level.CUD);
        return service.changePasswordAdmin(view);
    }

    @PutMapping("/{login}")
    public UserView update(@PathVariable String login, @RequestBody UserView view) {
        PrivilegeService.checkPrivilege(Registry.USER, Level.CUD);
        service.update(login, view);
        return service.getOne(login);
    }

    @DeleteMapping("/{login}")
    public void delete(@PathVariable String login) {
        PrivilegeService.checkPrivilege(Registry.USER, Level.CUD);
        service.delete(login);
    }

    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        PrivilegeService.checkPrivilege(Registry.REPORT, Level.SELECT);
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        var users = service.getList();
        UserExcelExporter excelExporter = new UserExcelExporter(users);
        excelExporter.export(response);
    }

}
