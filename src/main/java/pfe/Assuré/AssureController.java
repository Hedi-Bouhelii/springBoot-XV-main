package pfe.Assuré;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pfe.agence.Agence;
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
@PreAuthorize("hasRole('ASSURE')")
public class AssureController {
  private final AssureService service;

  @GetMapping
  public ResponseEntity<String> sayHello() {
    return ResponseEntity.ok("Hello from user endpoint");
  }

}
