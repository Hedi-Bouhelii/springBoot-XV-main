package pfe.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pfe.Assuré.Assure;
import pfe.Assuré.AssureDTO;
import pfe.Assuré.AssureRepository;
import pfe.Assuré.InsuredRegisterRequest;
import pfe.Expert.ExpertDTO;
import pfe.Expert.ExpertRepository;
import pfe.agence.Agence;
import pfe.agence.AgenceDTO;
import pfe.agence.AgenceRegisterRequest;
import pfe.agence.AgenceRepository;
import pfe.config.JwtService;
import pfe.role.Role;
import pfe.token.Token;
import pfe.token.TokenRepository;
import pfe.token.TokenType;
import pfe.user.User;
import pfe.user.UserRepository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final AssureRepository assureRepository;
  private final AgenceRepository agenceRepository;
  private final ExpertRepository expertRepository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse registerUser(InsuredRegisterRequest request) {
    var assure = new Assure(
            request.getEmail(),
            passwordEncoder.encode(request.getPassword()),
            Role.ASSURE,
            request.getFirstname(),
            request.getLastname(),
            request.getCin(),
            request.getTel(),
            request.getAddress(),
            request.getAgence()
    );
    Agence agence = agenceRepository.findByName(request.getAgence());
    assure.setAssurance(agence);
    assureRepository.save(assure);
    return AuthenticationResponse.builder()
            .message("Insured registered successfully")
            .build();
  }
  public AuthenticationResponse register(User request) {
    var admin = new User(
            request.getEmail(),
            passwordEncoder.encode(request.getPassword()),
            Role.ADMIN
    );
    repository.save(admin);
    return AuthenticationResponse.builder()
            .message("Insured registered successfully")
            .build();
  }
  public AuthenticationResponse registerAgenceRequest(AgenceRegisterRequest request) {
    var agence = new Agence(
            request.getEmail(),
            passwordEncoder.encode(request.getPassword()),
            Role.AGENCE,
            request.getName(),
            request.getMatricule(),
            request.getTel(),
            request.getAddress()
    );
    agence.setStatus("desactivated");
    agence.setActivated(false);
    agenceRepository.save(agence);
    return AuthenticationResponse.builder()
            .message("Agence registered successfully")
            .build();
  }
  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            )
    );
    var user = repository.findByEmail(request.getEmail())
            .orElseThrow();
    if (user.getRole().equals(Role.AGENCE)) {
      var agence = agenceRepository.findByEmail(request.getEmail())
              .orElseThrow();
      var jwtToken = jwtService.generateToken(user);
      revokeAllUserTokens(user);
      saveUserToken(user, jwtToken);
      return new AuthenticationResponse (user.getId(), jwtToken,
              new AgenceDTO(agence.getId(),agence.getEmail(),agence.getName(),agence.getMatricule(),agence.getTel(), agence.getFile(),agence.getAddress(), agence.getStatus(),agence.isActivated()),user.getRole());
    }

    else if (user.getRole().equals(Role.EXPERT)) {
      var expert = expertRepository.findByEmail(request.getEmail())
              .orElseThrow();
      var jwtToken = jwtService.generateToken(user);
      revokeAllUserTokens(user);
      saveUserToken(user, jwtToken);
      return new AuthenticationResponse(user.getId(), jwtToken,
              new ExpertDTO(expert.getId(),expert.getEmail(),expert.getName(), expert.getTel(), expert.getAddress()),user.getRole());
    }
    else if (user.getRole().equals(Role.ADMIN)) {
      var jwtToken = jwtService.generateToken(user);
      revokeAllUserTokens(user);
      saveUserToken(user, jwtToken);
      return AuthenticationResponse.builder()
              .role(user.getRole())
              .accessToken(jwtToken)
              .build();
    }
    else if (user.getRole().equals(Role.ASSURE)) {
      var assure = assureRepository.findByEmail(request.getEmail())
              .orElseThrow();
      var jwtToken = jwtService.generateToken(user);
      revokeAllUserTokens(user);
      saveUserToken(user, jwtToken);
      return new AuthenticationResponse(user.getId(), jwtToken,
              new AssureDTO(assure.getId(),assure.getFirstName(),assure.getLastName(), assure.getEmail(), assure.getTel(), assure.getCin(), assure.getAddress(), assure.getAssurance().getName()),user.getRole());
    }
    else
      return AuthenticationResponse.builder()
              .message("Invalid credentials")
              .build();
  }
  public void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
            .user(user)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}