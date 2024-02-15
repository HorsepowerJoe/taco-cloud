package sia.tacocloud.model;

import lombok.Data;

@Data
public class OrderedIngredients {
    private String[] wrap;
    private String[] protein;
    private String[] cheese;
    private String[] veggies;
    private String[] sauces;
}
