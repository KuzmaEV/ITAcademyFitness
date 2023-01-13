package by.mk_jd2_92_22.foodCounter.services.dto;


import java.util.UUID;

public class IngredientDTO {

    private Product product;
    private int weight;

    public IngredientDTO() {
    }

    public IngredientDTO(Product product, int weight) {
        this.product = product;
        this.weight = weight;
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



    public class Product {
        private UUID uuid;

        public UUID getUuid() {
            return uuid;
        }

        public void setUuid(UUID uuid) {
            this.uuid = uuid;
        }
    }

}