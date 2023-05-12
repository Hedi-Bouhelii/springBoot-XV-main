package pfe.Expert;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pfe.agence.Agence;
import pfe.agence.AgenceRepository;
import pfe.auth.AuthenticationResponse;
import pfe.auth.AuthenticationService;
import pfe.config.JwtService;
import pfe.role.Role;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ExpertService {
    private final ExpertRepository expertRepository;
    private final AgenceRepository agenceRepository;
    private final AuthenticationService service;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
   private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;
    public AuthenticationResponse saveExpert(ExpertRegisterRequest request, Integer id){
        char[] possibleCharacters = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?").toCharArray();
        String randomStr = RandomStringUtils.random( 10, 0, possibleCharacters.length-1, false, false, possibleCharacters, new SecureRandom() );
        sendEmail(request.getEmail(), randomStr);
        var expert = new Expert(
                request.getEmail(),
                passwordEncoder.encode(randomStr),
                Role.EXPERT,
                request.getName(),
                request.getTel(),
                request.getAddress()
        );
        Agence agence = agenceRepository.findById(id).orElseThrow();
        expert.setAssurance(agence);
        expertRepository.save(expert);
        return AuthenticationResponse.builder()
                .message("Expert registered successfully")
                .build();
    }
        public void sendEmail(String to, String content) {
            // Prepare message using a Spring helper
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
                message.setTo(to);
                message.setFrom(sender);
                message.setSubject("Expert login credintials");
                message.setText("Hello Sir, This is your login password "+content);
                javaMailSender.send(mimeMessage);
            } catch (MailException | MessagingException e) {
                e.printStackTrace();
            }
    }
}
