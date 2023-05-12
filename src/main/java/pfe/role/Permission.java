package pfe.role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    AGENCE_READ("agence:read"),
    AGENCE_UPDATE("agence:update"),
    AGENCE_DELETE("agence:create"),
    AGENCE_CREATE("agence:delete"),
    ASSURE_READ("assure:read"),
    ASSURE_UPDATE("assure:update"),
    ASSURE_DELETE("assure:create"),
    ASSURE_CREATE("assure:delete"),
    EXPERT_READ("expert:read"),
    EXPERT_UPDATE("expert:update"),
    EXPERT_DELETE("expert:create"),
    EXPERT_CREATE("expert:delete")

    ;

    @Getter
    private final String permission;
}
