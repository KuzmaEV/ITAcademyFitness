package by.mk_jd2_92_22.reportService.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
public class Ingredient {

    @Id
    private UUID uuid;


    @ManyToOne
    private Product product;


    private int weight;

    public Ingredient() {
    }

    public Ingredient(UUID uuid, Product product, int weight) {
        this.uuid = uuid;
        this.product = product;
        this.weight = weight;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "uuid=" + uuid +
                ", product=" + product +
                ", weight=" + weight +
                '}';
    }
}

