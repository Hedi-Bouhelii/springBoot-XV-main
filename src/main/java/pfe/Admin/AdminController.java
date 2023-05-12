package pfe.Admin;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import pfe.agence.AgenceDTO;



import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AdminService service;

    @GetMapping("/agences")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<?> getAgences() {
        List<AgenceDTO> agences = service.getAllUsers();
        return new ResponseEntity<>(agences, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    @Hidden
    public String post() {
        return "POST:: admin controller";
    }

    @PatchMapping("/activate")
    @PreAuthorize("hasAuthority('admin:update')")
    public String put(@RequestParam("id") Integer id) {
        service.activateAgence(id);
        return "Account activated";
    }
    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:delete')")
    @Hidden
    public String delete() {
        return "DELETE:: admin controller";
    }
}
