package by.mk_jd2_92_22.auditService.model;

import javax.persistence.*;

@Entity
public class Audit {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    private UserAudit user;

    @Column(name = "text")
    private String text;
    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private Type type;

    public Audit() {
    }

    public String getId() {
        return id;
    }

    public UserAudit getUser() {
        return user;
    }

    public void setUser(UserAudit user) {
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
