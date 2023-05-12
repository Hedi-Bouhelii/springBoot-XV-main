package pfe.Expert;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pfe.agence.Agence;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Integer> {
    Optional<Expert> findByEmail(String email);

    List<Expert> findByAssurance(Agence agence);
}
