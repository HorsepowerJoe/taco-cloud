package sia.tacocloud.tacos.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class OrderedIngredients {

    private String[] wrap;

    private String[] protein;

    private String[] cheese;

    private String[] veggies;

    private String[] sauces;
}
