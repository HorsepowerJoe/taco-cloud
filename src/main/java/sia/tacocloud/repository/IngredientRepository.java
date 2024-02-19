package sia.tacocloud.repository;

import sia.tacocloud.model.Ingredient;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();
    Ingredient findById(String id);
    Ingredient findByName(String name);
    Ingredient save(Ingredient ingredient);
    
}
