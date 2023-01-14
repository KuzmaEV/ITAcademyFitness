package by.mk_jd2_92_22.userSecurity.model.dto;

import java.util.UUID;

public class AuditDTO {

    private UUID user;
    private String text;
    private Type type;

    public AuditDTO() {
    }

    public AuditDTO(UUID user, String text, Type type) {
        this.user = user;
        this.text = text;
        this.type = type;
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
