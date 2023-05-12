package pfe.agence;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@Getter
public class AgenceDTO {
    private Integer id;
    private String email;
    private String name;
    private String matricule;
    private int tel;
    private String address;
    private String file;
    private String status;
    private  boolean activated;

    public AgenceDTO(Integer id, String email, String name, String matricule, int tel,String file, String address,String status,Boolean activated) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.matricule = matricule;
        this.tel = tel;
        this.file = file;
        this.address = address;
        this.status = status;
        this.activated = activated;
    }
    public AgenceDTO(Integer id, String email, String name, String matricule, int tel, String address,String status) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.matricule = matricule;
        this.tel = tel;
        this.address = address;
        this.status = status;
    }
}
