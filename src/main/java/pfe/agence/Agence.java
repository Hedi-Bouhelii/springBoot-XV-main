package pfe.agence;

import jakarta.persistence.*;
import lombok.*;
import pfe.Assuré.Assure;
import pfe.Expert.Expert;
import pfe.role.Role;

import java.util.ArrayList;
import java.util.List;
import pfe.user.User;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Setter
public class Agence extends User {
    private String name;
    private String matricule;
    private int tel;
    private String address;
    private String status;
    private String file;
    private boolean activated = false ;

    public Agence(String email, String password, Role role, String name, String matricule, int tel, String address) {
        super(email, password, role);
        this.name = name;
        this.matricule = matricule;
        this.tel = tel;
        this.address = address;
    }

}
