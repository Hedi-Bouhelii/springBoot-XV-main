package pfe.forgot;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import pfe.user.User;
import pfe.user.UserRepository;
import pfe.user.UserService;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/v1/forgot")
public class ResetPasswordController {
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final UserService userService;
    @Value("${spring.mail.username}")
    private String sender;

    @PostMapping("/recovery")
    public ResponseEntity<?> ResponseEntity(@RequestBody PasswordRecoveryRequest passwordRecoveryRequest) throws
            MailSendException {
        String email = passwordRecoveryRequest.getEmail();
        if(userRepository.findByEmail(email).isPresent()) {
            String token = String.valueOf(System.currentTimeMillis());
            userService.updateResetPasswordToken(token, email);
            try {
                sendEmail(email, token);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResponseEntity.ok().body("SENT: Check Your mail!");
        }
        else return ResponseEntity.badRequest().body("There is no account with this email" + email);
    }

    public void sendEmail(String recipientEmail, String token) {
        if(userRepository.findByEmail(recipientEmail).isPresent()) {
            String subject = "Password Reset Request";
            String content = "Hello Sir, use this code to recover your account :" + token ;
            try {
                // Creating a simple mail message
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                // Setting up necessary details
                mailMessage.setFrom(sender);
                mailMessage.setTo(recipientEmail);
                mailMessage.setText(content);
                mailMessage.setSubject(subject);
                // Sending the mail
                mailSender.send(mailMessage);
            }
            // Catch block to handle the exceptions
            catch (MailAuthenticationException e) {
                e.printStackTrace();
            }
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<?> ResponseEntity(@RequestBody ResetPasswordRequest request){
        String token = request.getToken();
        String password = request.getPassword();
        User user = userService.getByResetPasswordToken(token);
        if (user == null) {
            return ResponseEntity.badRequest().body("Error!");
        } else {
            userService.updatePassword(user, password);
        }
        return ResponseEntity.ok().body("Done!");
    }
}
