package pfe;

import jakarta.annotation.Resource;
import org.apache.commons.lang3.RandomStringUtils;
import pfe.agence.AgenceRegisterRequest;
import pfe.auth.AuthenticationService;
import pfe.Assuré.InsuredRegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pfe.role.Role;
import pfe.user.User;
import pfe.user.UserRepository;

import java.security.SecureRandom;


@SpringBootApplication
public class ConstatApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConstatApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service, UserRepository userRepository
			) {
		if(userRepository.findAll().isEmpty()){
			return args -> {
				User admin  = new User(
						"karoui@gmail.com",
						"test123",
						Role.ADMIN
				);
				System.out.println("Admin token: " + service.register(admin).getAccessToken());

				AgenceRegisterRequest agence  = new AgenceRegisterRequest(
						"star",
						"EGH125KJ",
						"star@gmail.com",
						2568974,
						"belle vue",
						"test123"
				);
				System.out.println("Agence token: " + service.registerAgenceRequest(agence).getAgence());

				InsuredRegisterRequest assure  = new InsuredRegisterRequest(
						"ahmed",
						"abidi",
						"ahmed@gmail.com",
						1235789,
						23859887,
						"gabes",
						"test123",
						"star"
				);
				System.out.println("Assure token: " + service.registerUser(assure).getAssure());
				assure.setAgence(agence.getName());
			};
		}
		return args -> {
			System.out.println("They already exist in the database, so we don't need to add them again");
			/*char[] possibleCharacters = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?").toCharArray();
			String randomStr = RandomStringUtils.random( 10, 0, possibleCharacters.length-1, false, false, possibleCharacters, new SecureRandom() );
			System.out.println(randomStr);*/
		};
	}
}
