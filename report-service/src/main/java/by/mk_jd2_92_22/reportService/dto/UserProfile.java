package by.mk_jd2_92_22.reportService.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserProfile {

//    @Id
    private UUID uuid;

//    @Column(name = "dt_create")
//    @JsonProperty("dt_create")
    private LocalDateTime dtCreate;
//    @Column(name = "dt_update")
//    @JsonProperty("dt_update")
    private LocalDateTime dtUpdate;

    public UserProfile() {
    }

    public UserProfile(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "uuid=" + uuid +
                ", dtCreate=" + dtCreate +
                ", dtUpdate=" + dtUpdate +
                '}';
    }
}


