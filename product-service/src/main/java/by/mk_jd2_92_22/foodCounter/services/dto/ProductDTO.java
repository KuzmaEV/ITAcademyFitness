package by.mk_jd2_92_22.foodCounter.services.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class ProductDTO {

    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotNull(message = "Calories is mandatory")
    @PositiveOrZero(message = "Calories cannot be less than 0")
    private int calories;
    @NotNull(message = "Proteins is mandatory")
    @PositiveOrZero(message = "Proteins cannot be less than 0")
    private double proteins;
    @NotNull(message = "Fats is mandatory")
    @PositiveOrZero(message = "Fats cannot be less than 0")
    private double fats;
    @NotNull(message = "Carbohydrates is mandatory")
    @PositiveOrZero(message = "Carbohydrates cannot be less than 0")
    private double carbohydrates;
    @NotNull(message = "Weight is mandatory")
    @Positive(message = "Weight cannot be less than 1")
    private int weight;

    public ProductDTO() {
    }

    public ProductDTO(String name, int kcal, double proteins, double fats, double carbohydrates, int weight) {
        this.title = name;
        this.calories = kcal;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.weight = weight;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
