package pfe.Assuré;

import lombok.*;
import pfe.role.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class InsuredRegisterRequest {

  private String firstname;
  private String lastname;
  private String email;
  private int cin;
  private int tel;
    private String address;
  private String password;
  private String agence;


}
