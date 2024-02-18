// package sia.tacocloud.tacos.controller;

// import java.util.Arrays;
// import java.util.List;
// import java.util.stream.Collectors;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;

// import lombok.extern.slf4j.Slf4j;
// import sia.tacocloud.tacos.Ingredient;
// import sia.tacocloud.tacos.Ingredient.Type;
// import sia.tacocloud.tacos.Taco;



// @Slf4j
// @Controller
// @RequestMapping("/design")
// public class DesignTacoController {
    
//     @GetMapping
//     public String showDesignForm(Model model) {
//         List<Ingredient> ingredients = Arrays.asList(
//             new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
//             new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
//             new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
//             new Ingredient("CARN", "Carnitas", Type.PROTEIN),
//             new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
//             new Ingredient("LETC", "Lettuce", Type.VEGGIES),
//             new Ingredient("CHED", "Cheddar", Type.CHEESE),
//             new Ingredient("JACK", "Monterrey", Type.CHEESE),
//             new Ingredient("SLSA", "Salsa", Type.SAUCE),
//             new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
//         );

//         Type[] types = Ingredient.Type.values();
//         for(Type type : types){
//             model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
//         }

//         model.addAttribute("taco", new Taco());
        
//         return "design";
//     }

//     private List<Ingredient> filterByType(
//         List<Ingredient> ingredients, Type type) {
//             return ingredients
//                         .stream()
//                         .filter(x -> x.getType().equals(type))
//                         .collect(Collectors.toList());
//         }  --> view가 vue기 때문에 api로 리팩토링 해야할듯
// }
package sia.tacocloud.tacos.controller;


import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sia.tacocloud.model.Taco;
import sia.tacocloud.tacos.service.DesignTacoService;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/design")
public class DesignTacoController {
    private final DesignTacoService designTacoService;

    @GetMapping()
    public ResponseEntity<String> showDesignForm() {
        
        try {
            return ResponseEntity.ok(designTacoService.getDesignForm());
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("JSON 처리 중 오류가 발생했습니다.");            
        }
    }


    @PostMapping()
    public ResponseEntity<Object> processDesign(@RequestBody@Valid Taco design, Errors errors) {

        if(errors.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
        }
        //타코 디자인을 저장
        log.info("Processing design: "+ design);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/orders/current"));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
    
    
}