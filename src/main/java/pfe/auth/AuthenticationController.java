package pfe.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pfe.Assuré.Assure;
import pfe.Assuré.InsuredRegisterRequest;
import pfe.agence.Agence;
import pfe.agence.AgenceRegisterRequest;
import pfe.agence.AgenceRepository;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;
  private final AgenceRepository repository;

  @PostMapping("/assure/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody InsuredRegisterRequest request
  ) {
    return ResponseEntity.ok(service.registerUser(request));
  }
  @PostMapping("/agence/register")
  public ResponseEntity<AuthenticationResponse> register(
          @RequestBody AgenceRegisterRequest request
  ) {
    return ResponseEntity.ok(service.registerAgenceRequest(request));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }


}
