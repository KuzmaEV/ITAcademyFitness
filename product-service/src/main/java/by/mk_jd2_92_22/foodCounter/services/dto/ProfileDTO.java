package by.mk_jd2_92_22.foodCounter.services.dto;

import by.mk_jd2_92_22.foodCounter.model.ActivityType;
import by.mk_jd2_92_22.foodCounter.model.ProfileSex;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

public class ProfileDTO {

    @Positive(message = "Height must be a positive number")
    @NotNull(message = "Height is mandatory")
    private int height;

    @Positive(message = "Weight must be a positive number")
    @NotNull(message = "Weight is mandatory")
    private double weight;

    @JsonProperty("dt_birthday")
    @Past(message = "Birthday is the date of birth")
    @NotNull(message = "Birthday is mandatory")
    private LocalDate dtBirthday;

    @Positive(message = "Target must be a positive number")
    @NotNull(message = "Target is mandatory")
    private double target;

    @JsonProperty("activity_type")
    @NotNull(message = "ActivityType is mandatory")
    private ActivityType activityType;

    @NotNull(message = "ProfileSex is mandatory")
    private ProfileSex sex;

    public ProfileDTO() {
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
}
