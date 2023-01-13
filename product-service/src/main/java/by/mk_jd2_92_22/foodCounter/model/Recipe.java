package by.mk_jd2_92_22.foodCounter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class Recipe {

    @Id
    private UUID uuid;

    @JsonProperty("dt_create")
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;

    @Version
    @JsonProperty("dt_update")
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;

    private String title;

    @OneToMany
    @JoinColumn(name = "dish_uuid", referencedColumnName = "uuid")
    private List<Ingredient> ingredients;

    public Recipe() {
    }

    public Recipe(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, String name, List<Ingredient> ingredients) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.title = name;
        this.ingredients = ingredients;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
