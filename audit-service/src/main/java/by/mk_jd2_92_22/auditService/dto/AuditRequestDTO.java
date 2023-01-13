package by.mk_jd2_92_22.auditService.dto;

import by.mk_jd2_92_22.auditService.model.Type;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class AuditRequestDTO {

    @NotNull
    private UUID user;
    @NotBlank
    private String text;
    @NotNull
    private Type type;

    public AuditRequestDTO() {
    }

    public UUID getUser() {
        return user;
    }

    public void setUser(UUID user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
