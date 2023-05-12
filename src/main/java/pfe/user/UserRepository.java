package pfe.user;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pfe.role.Role;

@Primary
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  @Query("SELECT u FROM User u WHERE u.role = 'AGENCE'")
  List<User> findAllAgence();
  Optional<User> findByEmail(String email);

  User findUserByEmail(String email);
  List<User> findByRole(Role role);
  @NotNull
  Optional<User> findById(Integer id);

    User findByResetPassword(String token);
}
