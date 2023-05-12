package pfe.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChangepwdRequest {
    String Currentpassword;
    String newPassword;
}
