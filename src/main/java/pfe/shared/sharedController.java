package pfe.shared;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pfe.forgot.ResetPasswordRequest;
import pfe.user.User;
import pfe.user.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shared/")
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
public class sharedController {
    private final UserService service;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/changePassword")
    public ResponseEntity<?> ResponseEntity(@RequestBody ChangepwdRequest request, @RequestParam("id") Integer id){
        String password = request.getNewPassword();
        User user = service.getById(id);
        if(passwordEncoder.matches(request.getCurrentpassword(),user.getPassword())){
            service.changePassword(user, password);
            return ResponseEntity.ok().body("Done!");
        }
        else return ResponseEntity.badRequest().body("Error!");
}
}
