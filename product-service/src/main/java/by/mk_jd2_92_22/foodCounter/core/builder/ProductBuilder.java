package by.mk_jd2_92_22.foodCounter.core.builder;

import by.mk_jd2_92_22.foodCounter.model.Product;

import java.time.LocalDateTime;
import java.util.UUID;

public class ProductBuilder {

    private UUID uuid;

    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;

    private String name;

    private int kcal;
    private double proteins;
    private double fats;
    private double carbohydrates;

    private int weight;

    private UUID userId;

    private ProductBuilder() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public ProductBuilder setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public ProductBuilder setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
        return this;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public ProductBuilder setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public int getKcal() {
        return kcal;
    }

    public ProductBuilder setKcal(int kcal) {
        this.kcal = kcal;
        return this;
    }

    public double getProteins() {
        return proteins;
    }

    public ProductBuilder setProteins(double proteins) {
        this.proteins = proteins;
        return this;
    }

    public double getFats() {
        return fats;
    }

    public ProductBuilder setFats(double fats) {
        this.fats = fats;
        return this;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public ProductBuilder setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
        return this;
    }

    public int getWeight() {
        return weight;
    }

    public ProductBuilder setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public ProductBuilder setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public static ProductBuilder create(){
        return new ProductBuilder();
    }

    public Product build(){
        return new Product(uuid, dtCreate, dtUpdate, name, kcal, proteins, fats, carbohydrates, weight, userId);
    }


}
