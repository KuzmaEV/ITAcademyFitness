package by.mk_jd2_92_22.foodCounter.services.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class RecipeDTO {

    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotEmpty(message = "Ingredients is mandatory")
    private List<IngredientDTO> composition;

    public RecipeDTO() {
    }

    public RecipeDTO(String name, List<IngredientDTO> ingredients) {
        this.title = name;
        this.composition = ingredients;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<IngredientDTO> getComposition() {
        return composition;
    }

    public void setComposition(List<IngredientDTO> composition) {
        this.composition = composition;
    }

}
