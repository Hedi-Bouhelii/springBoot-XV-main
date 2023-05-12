package pfe.agence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pfe.role.Role;

@Getter
@Setter
@AllArgsConstructor
public class AgenceRegisterRequest {
    private String name;
    private String matricule;
    private String email;
    private int tel;
    private String address;
    private String password;

}
