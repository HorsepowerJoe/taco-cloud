package sia.tacocloud.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import sia.tacocloud.tacos.Ingredient;
import sia.tacocloud.tacos.Taco;

@Data
public class IngredientDto {
    private Set<Ingredient> wraps = new HashSet<>();
    private Set<Ingredient> proteins = new HashSet<>();
    private Set<Ingredient> cheeses = new HashSet<>();
    private Set<Ingredient> veggies = new HashSet<>();
    private Set<Ingredient> sauces = new HashSet<>();
    private Taco taco; //이거는 대체 뭐임? 뷰에서 완성시켜서 넘겨주는?
}
