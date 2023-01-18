package by.mk_jd2_92_22.foodCounter.services.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.UUID;

public class JournalFoodDTO {

    @NotNull(message = "dt_supply is mandatory")
    @JsonProperty("dt_supply")
    private LocalDateTime dtSupply;

    private Product product;
    private Dish recipe;
    @Positive(message = "Weight cannot be less than 1")
    private int weight;

    public JournalFoodDTO() {
    }

    public JournalFoodDTO(LocalDateTime dtSupply, Product product, Dish dish, int weight) {
        this.dtSupply = dtSupply;
        this.product = product;
        this.recipe = dish;
        this.weight = weight;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Dish getRecipe() {
        return recipe;
    }

    public void setRecipe(Dish recipe) {
        this.recipe = recipe;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public LocalDateTime getDtSupply() {
        return dtSupply;
    }

    public void setDtSupply(LocalDateTime dtSupply) {
        this.dtSupply = dtSupply;
    }

    public static class Product{
        private UUID uuid;

        public UUID getUuid() {
            return uuid;
        }

        public void setUuid(UUID uuid) {
            this.uuid = uuid;
        }
    }

    public static class Dish{
        private UUID uuid;

        public UUID getUuid() {
            return uuid;
        }

        public void setUuid(UUID uuid) {
            this.uuid = uuid;
        }
    }
}
