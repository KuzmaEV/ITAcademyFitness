package by.mk_jd2_92_22.reportService.model;

import by.mk_jd2_92_22.reportService.dto.ReportParams;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "report_food_journal")
public class Report {

    @Id
    private UUID uuid;
    @Column(name = "dt_create")
    @JsonProperty("dt_create")
    private LocalDateTime dtCreate;
    @Version
    @Column(name = "dt_update")
    @JsonProperty("dt_update")
    private LocalDateTime dtUpdate;

    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private ReportType type;

    private String description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "dtFrom", column = @Column(name = "dt_from")),
            @AttributeOverride( name = "dtTo", column = @Column(name = "dt_to"))})
    private ReportParams params;

    @JsonIgnore
    @Column(name = "profile_id")
    private UUID profileId;

    public Report() {
    }

    public Report(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, Status status,
                  ReportType type, String description, ReportParams params, UUID userId) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.status = status;
        this.type = type;
        this.description = description;
        this.params = params;
        this.profileId = userId;
    }

    public UUID getUuid() {
        return uuid;
    }


    public LocalDateTime getDtCreate() {
        return dtCreate;
    }


    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ReportParams getParams() {
        return params;
    }

    public void setParams(ReportParams params) {
        this.params = params;
    }

    public UUID getProfileId() {
        return profileId;
    }

    public void setProfileId(UUID profileId) {
        this.profileId = profileId;
    }

    @Override
    public String toString() {
        return "Report{" +
                "uuid=" + uuid +
                ", dtCreate=" + dtCreate +
                ", dtUpdate=" + dtUpdate +
                ", status=" + status +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", params=" + params +
                ", userId=" + profileId +
                '}';
    }
}
