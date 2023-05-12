package pfe.Expert;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pfe.agence.Agence;
import pfe.role.Role;
import pfe.user.User;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Setter
public class Expert extends User {
    private String name;
    private int tel;
    private String address;
    @ManyToOne
    @JoinTable(
            name = "agence_experts",
            joinColumns = @JoinColumn(name = "expert_id"),
            inverseJoinColumns = @JoinColumn(name = "agence_id"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Agence assurance;

    public Expert(String email, String password, Role role, String name, int tel, String address) {
        super(email, password,role);
        this.name = name;
        this.tel = tel;
        this.address = address;
    }

    public Expert(Integer id,String email, String name, int tel, String address) {
        super(id, email);
        this.name = name;
        this.tel = tel;
        this.address = address;
    }


}
