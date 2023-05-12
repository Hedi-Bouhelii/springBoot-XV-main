package pfe.Expert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExpertRegisterRequest {
    private String name;
    private String email;
    private int tel;
    private String address;



}
