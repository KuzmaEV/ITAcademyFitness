package by.mk_jd2_92_22.reportService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JournalFood {

    @Id
    private UUID uuid;

    @JsonProperty("dt_create")
    private LocalDateTime dtCreate;

    @JsonProperty("dt_update")
    private LocalDateTime dtUpdate;

    @JsonProperty("dt_supply")
    private LocalDateTime dtSupply;

    private  Product product;

    private Recipe dish;

    private int weight;

    @JsonIgnore
    private UUID profile;


    public JournalFood() {
    }

    public JournalFood(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate,
                       LocalDateTime dtSupply, Product product, Recipe dish, int weight, UUID profile) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.dtSupply = dtSupply;
        this.product = product;
        this.dish = dish;
        this.weight = weight;
        this.profile = profile;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Recipe getDish() {
        return dish;
    }

    public void setDish(Recipe dish) {
        this.dish = dish;
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

    public UUID getProfile() {
        return profile;
    }

    public void setProfile(UUID profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "JournalFood{" +
                "uuid=" + uuid +
                ", dtCreate=" + dtCreate +
                ", dtUpdate=" + dtUpdate +
                ", dtSupply=" + dtSupply +
                ", product=" + product +
                ", dish=" + dish +
                ", weight=" + weight +
                ", profile=" + profile +
                '}';
    }
}
