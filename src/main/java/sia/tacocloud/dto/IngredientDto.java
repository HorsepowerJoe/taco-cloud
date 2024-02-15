package sia.tacocloud.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import sia.tacocloud.model.Ingredient;
import sia.tacocloud.model.Taco;

@Data
public class IngredientDto {
    private Set<Ingredient> wraps = new HashSet<>();
    private Set<Ingredient> proteins = new HashSet<>();
    private Set<Ingredient> cheeses = new HashSet<>();
    private Set<Ingredient> veggies = new HashSet<>();
    private Set<Ingredient> sauces = new HashSet<>();
    private Taco taco; 
}
