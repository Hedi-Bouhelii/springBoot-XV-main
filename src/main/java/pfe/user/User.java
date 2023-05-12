package pfe.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Qualifier;
import pfe.role.Role;

import java.util.Collection;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Qualifier
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "user")
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String email;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;
  @Enumerated(EnumType.STRING)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Role role;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String resetPassword;


  public User(String email, String password, Role role) {
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public User(Integer id, String email) {
    this.id = id;
    this.email = email;
  }
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }

  @Override
  public String getPassword() {
    return password;
  }
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Override
  public String getUsername() {
    return email;
  }
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Override
  public boolean isEnabled() {
    return true;
  }

}
