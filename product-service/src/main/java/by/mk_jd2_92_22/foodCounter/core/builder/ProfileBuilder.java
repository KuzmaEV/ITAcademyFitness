package by.mk_jd2_92_22.foodCounter.core.builder;

import by.mk_jd2_92_22.foodCounter.model.ActivityType;
import by.mk_jd2_92_22.foodCounter.model.Profile;
import by.mk_jd2_92_22.foodCounter.model.ProfileSex;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ProfileBuilder {

    private UUID uuid;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private int height;
    private double weight;
    private LocalDate dtBirthday;
    private double target;
    private ActivityType activityType;
    private ProfileSex sex;
    private UUID userId;

    private ProfileBuilder() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public ProfileBuilder setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public ProfileBuilder setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
        return this;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public ProfileBuilder setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public ProfileBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    public double getWeight() {
        return weight;
    }

    public ProfileBuilder setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public LocalDate getDtBirthday() {
        return dtBirthday;
    }

    public ProfileBuilder setDtBirthday(LocalDate dtBirthday) {
        this.dtBirthday = dtBirthday;
        return this;
    }

    public double getTarget() {
        return target;
    }

    public ProfileBuilder setTarget(double target) {
        this.target = target;
        return this;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public ProfileBuilder setActivityType(ActivityType activityType) {
        this.activityType = activityType;
        return this;
    }

    public ProfileSex getSex() {
        return sex;
    }

    public ProfileBuilder setSex(ProfileSex sex) {
        this.sex = sex;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public ProfileBuilder setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public static ProfileBuilder create(){
        return new ProfileBuilder();
    }

    public Profile build(){
        return new Profile(uuid, dtCreate, dtUpdate, height, weight, dtBirthday, target,
                activityType, sex, userId);
    }
}
