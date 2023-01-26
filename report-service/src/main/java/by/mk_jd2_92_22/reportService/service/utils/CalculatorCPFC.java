package by.mk_jd2_92_22.reportService.service.utils;

import by.mk_jd2_92_22.reportService.model.FoodCPFC;
import by.mk_jd2_92_22.reportService.model.Ingredient;
import by.mk_jd2_92_22.reportService.model.Product;
import by.mk_jd2_92_22.reportService.model.Recipe;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class CalculatorCPFC {

    public FoodCPFC calculateProduct(Product product, double weight){

        final int productWeight = product.getWeight();
        final int calories = product.getCalories();
        final double proteins = product.getProteins();
        final double fats = product.getFats();
        final double carbohydrates = product.getCarbohydrates();

        return calculateCPFC(productWeight, calories, proteins, fats, carbohydrates, weight);
    }

    public FoodCPFC calculateRecipe(Recipe recipe, double weight){

        int productWeight = 0;

        int calories = 0;
        double proteins = 0;
        double fats = 0;
        double carbohydrates = 0;


        final List<Ingredient> ingredients = recipe.getIngredients();

        for (Ingredient ingredient : ingredients) {

            final FoodCPFC productCPFC = this.calculateProduct(ingredient.getProduct(), ingredient.getWeight());

            productWeight += ingredient.getWeight();

            calories += productCPFC.getCalories();
            proteins += productCPFC.getProteins();
            fats += productCPFC.getFats();
            carbohydrates += productCPFC.getCarbohydrates();
        }

        return calculateCPFC(productWeight, calories, proteins, fats, carbohydrates, weight);

    }

    private FoodCPFC calculateCPFC(int productWeight,
                                   int calories,
                                   double proteins,
                                   double fats,
                                   double carbohydrates,
                                   double weight){

        FoodCPFC foodCPFC = new FoodCPFC();
        if (calories > 0){
            foodCPFC.setCalories((int) (calories / (productWeight / weight )));
        } else { foodCPFC.setCalories(0);}

        if (proteins > 0){
            foodCPFC.setProteins(this.round(proteins / (productWeight / weight )));
        } else { foodCPFC.setProteins(0);}

        if (fats > 0){
            foodCPFC.setFats(this.round(fats / (productWeight / weight )));
        } else { foodCPFC.setFats(0);}

        if (carbohydrates > 0){
            foodCPFC.setCarbohydrates(this.round(carbohydrates / (productWeight / weight )));
        } else { foodCPFC.setCarbohydrates(0);}

        return foodCPFC;
    }

    private double round(double value) {

        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
