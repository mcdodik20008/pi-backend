package pibackend.domain.author.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pibackend.domain.auth.role.model.entity.Privilege;
import pibackend.domain.auth.user.model.entity.User;
import pibackend.domain.auth.user.model.mapper.UserMapper;
import pibackend.domain.auth.user.model.view.UserView;
import pibackend.domain.auth.user.repository.UserRepository;
import pibackend.domain.author.model.view.AuthorViewCreate;
import pibackend.domain.author.model.view.AuthorViewReadList;
import pibackend.domain.author.model.view.AuthorViewReadOne;
import pibackend.domain.author.service.AuthorService;
import pibackend.infrastructure.SecurityContext;
import pibackend.infrastructure.export.AuthorExcelExporter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/author", produces = "application/json")
public class AuthorController {

    private final AuthorService service;

    private final UserMapper mapper;
    private final UserRepository userRepository;

    @GetMapping
    public Page<AuthorViewReadList> getPage(
            Pageable pageable,
            @RequestParam(required = false) String filterId,
            @RequestParam(required = false) String filterName) {
        if (filterId != null) {
            return service.getPageByIdLike(pageable, filterId);
        }
        if (filterName != null) {
            return service.getPageByNameLike(pageable, filterName);
        }
        return service.getPage(pageable);
    }

    @GetMapping("/{id}")
    public AuthorViewReadOne getOne(@PathVariable String id) {
        return service.getOne(id);
    }

    @Transactional
    @GetMapping("/currentUser")
    public UserView getCurrentUser() {
        User user = SecurityContext.currentUser;
        user = userRepository.findById(user.getLogin()).get();
        if (!user.getRoles().isEmpty() && !user.getRoles().get(0).getPrivileges().isEmpty()) {
            Privilege privileges = user.getRoles().get(0).getPrivileges().get(0);
            privileges = privileges;
        }
        return mapper.toView(user);
    }

    @PostMapping
    public AuthorViewCreate create(@RequestBody AuthorViewCreate view) {
        return service.create(view);
    }

    @PutMapping("/{id}")
    public AuthorViewReadOne update(@PathVariable String id, @RequestBody AuthorViewReadOne view) {
        service.update(id, view);
        return service.getOne(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        var dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        var currentDateTime = dateFormatter.format(new Date());

        var headerKey = "Content-Disposition";
        var headerValue = "attachment; filename=authors_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        var authors = service.getList();
        var excelExporter = new AuthorExcelExporter(authors);
        excelExporter.export(response);
    }

}
