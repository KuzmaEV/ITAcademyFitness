package by.mk_jd2_92_22.reportService.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ProfileDTO {

    private UUID uuid;

    @JsonProperty("dt_create")
    private LocalDateTime dtCreate;
    @JsonProperty("dt_update")
    private LocalDateTime dtUpdate;

    private int height;
    private double weight;
    @JsonProperty("dt_birthday")
    private LocalDate dtBirthday;
    private double target;

    @JsonProperty("activity_type")
    private ActivityType activityType;
    private ProfileSex sex;

    private UserProfile user;

    public ProfileDTO() {
    }

    public ProfileDTO(UUID uuid,
                      LocalDateTime dtCreate, LocalDateTime dtUpdate,
                      int height, double weight, LocalDate dtBirthday, double target,
                      ActivityType activityType, ProfileSex sex,
                      UserProfile user) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.height = height;
        this.weight = weight;
        this.dtBirthday = dtBirthday;
        this.target = target;
        this.activityType = activityType;
        this.sex = sex;
        this.user = user;
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public LocalDate getDtBirthday() {
        return dtBirthday;
    }

    public void setDtBirthday(LocalDate dtBirthday) {
        this.dtBirthday = dtBirthday;
    }

    public double getTarget() {
        return target;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public ProfileSex getSex() {
        return sex;
    }

    public void setSex(ProfileSex sex) {
        this.sex = sex;
    }

    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ProfileResponseDTO{" +
                "uuid=" + uuid +
                ", dtCreate=" + dtCreate +
                ", dtUpdate=" + dtUpdate +
                ", height=" + height +
                ", weight=" + weight +
                ", dtBirthday=" + dtBirthday +
                ", target=" + target +
                ", activityType=" + activityType +
                ", sex=" + sex +
                ", user=" + user +
                '}';
    }
}
