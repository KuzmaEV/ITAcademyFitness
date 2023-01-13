package by.mk_jd2_92_22.foodCounter.services.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class RecipeDTO {

    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotEmpty(message = "Ingredients is mandatory")
    private List<IngredientDTO> ingredients;

    public RecipeDTO() {
    }

    public RecipeDTO(String name, List<IngredientDTO> ingredients) {
        this.title = name;
        this.ingredients = ingredients;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<IngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }

}
