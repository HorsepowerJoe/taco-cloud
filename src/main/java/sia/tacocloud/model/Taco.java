package sia.tacocloud.model;

import lombok.Data;

@Data
public class Taco {
    private String name;
    private OrderedIngredients orderedIngredients;
}
