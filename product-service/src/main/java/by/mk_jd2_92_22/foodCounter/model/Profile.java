package by.mk_jd2_92_22.foodCounter.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    private UUID uuid;

//    @JsonProperty("dt_create")
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;

    @Version
//    @JsonProperty("dt_update")
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;

    private int height;
    private double weight;

//    @JsonProperty("dt_birthday")
    @Column(name = "dt_birthday")
    private LocalDate dtBirthday;
    private double target;

//    @JsonProperty("activity_type")
    @Column(name = "activity_type")
    @Enumerated(value = EnumType.STRING)
    private ActivityType activityType;

    @Column(name = "sex")
    @Enumerated(value = EnumType.STRING)
    private ProfileSex sex;

    @Column(name = "profile_user")
    private UUID user;

    public Profile() {
    }

    public Profile(UUID uuid,
                   LocalDateTime dtCreate, LocalDateTime dtUpdate,
                   int height, double weight, LocalDate dtBirthday, double target,
                   ActivityType activityType, ProfileSex sex,
                   UUID user) {
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

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setDtBirthday(LocalDate dtBirthday) {
        this.dtBirthday = dtBirthday;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public void setSex(ProfileSex sex) {
        this.sex = sex;
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

    public int getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public LocalDate getDtBirthday() {
        return dtBirthday;
    }

    public double getTarget() {
        return target;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public ProfileSex getSex() {
        return sex;
    }

    public UUID getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Profile{" +
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
