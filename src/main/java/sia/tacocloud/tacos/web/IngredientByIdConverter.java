package sia.tacocloud.tacos.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import sia.tacocloud.tacos.model.Ingredient;
import sia.tacocloud.tacos.model.OrderedIngredients;
import sia.tacocloud.tacos.repository.IngredientRepository;

@Component
@RequiredArgsConstructor
public class IngredientByIdConverter {
    private final IngredientRepository ingredientRepository;
    
    
    private List<Ingredient> iterateIngredients(OrderedIngredients orderedIngredients){
        List<Ingredient> list = new ArrayList<>();

        for(String id : orderedIngredients.getWrap()){
            list.add(ingredientRepository.findById(id).get());
        }
        for(String id : orderedIngredients.getCheese()){
            list.add(ingredientRepository.findById(id).get());
        }
        for(String id : orderedIngredients.getProtein()){
            list.add(ingredientRepository.findById(id).get());
        }
        for(String id : orderedIngredients.getSauces()){
            list.add(ingredientRepository.findById(id).get());
        }
        for(String id : orderedIngredients.getVeggies()){
            list.add(ingredientRepository.findById(id).get());
        }

        return list;
    }
}
