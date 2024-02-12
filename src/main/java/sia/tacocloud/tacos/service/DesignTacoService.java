package sia.tacocloud.tacos.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import sia.tacocloud.tacos.Ingredient;
import sia.tacocloud.tacos.Ingredient.Type;

@Service
public class DesignTacoService {
   
    public String getDesignForm() throws JsonProcessingException{
        List<Ingredient> ingredients = Arrays.asList( // 이것이 DB역할을 하는 List
        new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
        new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
        new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
        new Ingredient("CARN", "Carnitas", Type.PROTEIN),
        new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
        new Ingredient("LETC", "Lettuce", Type.VEGGIES),
        new Ingredient("CHED", "Cheddar", Type.CHEESE),
        new Ingredient("JACK", "Monterrey", Type.CHEESE),
        new Ingredient("SLSA", "Salsa", Type.SAUCE),
        new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
    );

    // Type[] types = Ingredient.Type.values();
    // for(Type type : types){
    //     model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
    // }                                            
    // model.addAttribute("taco", new Taco());      모델 객체에 담아서 뷰로 넘겨주지만 그러지 못하니까 리팩토링

    return new ObjectMapper().writeValueAsString(ingredients);

    }
          
           



    private List<Ingredient> filterByType(             //타입 체크하는 메서드?
        List<Ingredient> ingredients, Type type) {
            return ingredients
                        .stream()
                        .filter(x -> x.getType().equals(type))
                        .collect(Collectors.toList());
        } 
    }


