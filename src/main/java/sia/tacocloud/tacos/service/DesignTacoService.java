package sia.tacocloud.tacos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import sia.tacocloud.tacos.dto.IngredientDto;
import sia.tacocloud.tacos.model.Ingredient;
import sia.tacocloud.tacos.model.Ingredient.Type;
import sia.tacocloud.tacos.model.Taco;
import sia.tacocloud.tacos.repository.IngredientRepository;
import sia.tacocloud.tacos.repository.TacoRepository;

@Service
@RequiredArgsConstructor
public class DesignTacoService {
    // private final TacoRepository repo;
    private final IngredientRepository jdbcIngredientRepository;
    private final TacoRepository jdbcTacoRepository;

    public String getDesignForm() throws JsonProcessingException {
        IngredientDto ingredientDto = new IngredientDto();

        // Type[] types = Ingredient.Type.values();
        // for(Type type : types){
        // model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients,
        // type));
        // }
        // model.addAttribute("taco", new Taco()); 모델 객체에 담아서 뷰로 넘겨주지만 그러지 못하니까 리팩토링
        // 되게 난해하네.. 각 타입들을 lowerCase해서 key로 value는 이넘에 정의된 것들이 맞는지 필터해서 value로..
        // 결국 모델에 넣은 이유 = 각 속성들로 커스터마이즈 타코하기 위해서니까 filter를 거쳐서 각 key에 맞는 value 대칭
        // 새로운 dto로 넘겨서 대체. 중복 제거하는 set으로..

        // for (Ingredient i : repo.getIngredients()) { // 레포에 담긴 타코들을 분해해서 각 속성으로 대칭하기
        //     switch (i.getType()) {
        //         case CHEESE:
        //             ingredientDto.getCheeses().add(i);
        //             break;

        //         case WRAP:
        //             ingredientDto.getWraps().add(i);
        //             break;

        //         case PROTEIN:
        //             ingredientDto.getProteins().add(i);
        //             break;

        //         case SAUCE:
        //             ingredientDto.getSauces().add(i);
        //             break;

        //         case VEGGIES:
        //             ingredientDto.getVeggies().add(i);
        //             break;

        //         default:
        //             break;
        //     }
        // } // 진짜 난해하다.. 혼란하다..

        // ingredientDto.setTaco(new Taco());

        List<Ingredient> ingredients = new ArrayList<>();
        jdbcIngredientRepository.findAll().forEach(i->{ingredients.add(i);}); //여전히 스위치로 변환해야 함

        for (Ingredient i : ingredients) { // ingredients에 담긴 타코들을 분해해서 각 속성으로 대칭하기
            switch (i.getType()) {
                case CHEESE:
                    ingredientDto.getCheeses().add(i);
                    break;

                case WRAP:
                    ingredientDto.getWraps().add(i);
                    break;

                case PROTEIN:
                    ingredientDto.getProteins().add(i);
                    break;

                case SAUCE:
                    ingredientDto.getSauces().add(i);
                    break;

                case VEGGIES:
                    ingredientDto.getVeggies().add(i);
                    break;

                default:
                    break;
            }
        } 

        ingredientDto.setTaco(new Taco());
        

        return new ObjectMapper().writeValueAsString(ingredientDto);

    }

    private List<Ingredient> filterByType(
            List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    public ResponseEntity<Object> saveDesign(@Valid Taco design) {
        try {
           jdbcTacoRepository.save(design);
        return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());  
        }
    }
}
