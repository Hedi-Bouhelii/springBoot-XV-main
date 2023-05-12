package pfe.Assuré;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import pfe.agence.Agence;
import pfe.role.Role;
import pfe.user.User;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Setter
public class Assure extends User {
    private String firstName;
    private String lastName;
    private int tel;
    private int cin;
    private String address;
    @ManyToOne
    @JoinTable(
            name = "agence_users",
            joinColumns = @JoinColumn(name = "assure_id"),
            inverseJoinColumns = @JoinColumn(name = "agence_id"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Agence assurance;

    public Assure(String email, String password, Role role, String firstName, String lastName, int tel, int cin, String address, String agence) {
        super(email, password, role);
        this.firstName = firstName;
        this.lastName = lastName;
        this.tel = tel;
        this.cin = cin;
        this.address = address;
    }
}
