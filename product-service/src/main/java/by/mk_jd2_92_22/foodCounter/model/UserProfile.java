package by.mk_jd2_92_22.foodCounter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserProfile {

//    @Id
    private UUID uuid;

//    @Column(name = "dt_create")
    @JsonProperty("dt_create")
    private LocalDateTime dtCreate;
//    @Column(name = "dt_update")
    @JsonProperty("dt_update")
    private LocalDateTime dtUpdate;

    public UserProfile() {
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
}


