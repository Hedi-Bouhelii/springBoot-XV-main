package pfe.agence;



import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pfe.user.User;
import pfe.user.UserRepository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgenceRepository extends JpaRepository<Agence,Integer> {
    List<Agence> findAll();
    Agence findByName(String name);

    Optional<Agence> findByEmail(String email);

    @Query("SELECT a.file FROM Agence a WHERE a.id = :id")
    String findFileByAgenceId(Integer id);
}
