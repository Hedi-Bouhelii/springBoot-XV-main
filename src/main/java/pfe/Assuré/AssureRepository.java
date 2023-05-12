package pfe.Assuré;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pfe.agence.Agence;
import pfe.user.User;
import pfe.user.UserRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssureRepository extends JpaRepository<Assure,Integer> {
   // List<Assure> findAllByAgence(String agence);
    Optional<Assure> findByEmail(String email);

    List<Assure> findByAssurance(Agence assurance);
}
