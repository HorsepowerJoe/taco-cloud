package sia.tacocloud.tacos.repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import sia.tacocloud.tacos.model.Ingredient;
import sia.tacocloud.tacos.model.OrderedIngredients;
import sia.tacocloud.tacos.model.Taco;

@Repository
@RequiredArgsConstructor
public class JdbcTacoRepository {//implements TacoRepository {

    // private final JdbcTemplate jdbc;


    // private final JdbcIngredientRepository jdbcIngredientRepository;

    // @Override
    // public Taco save(Taco design) {
    //    long tacoId = saveTacoInfo(design);
    //    design.setId(tacoId);
       
    // //    for (Ingredient ingredient : design.getIngredients()){
    // //     saveIngredientToTaco(ingredient, tacoId);
    // //    } 순회하면서 taco_ingredients에 ingredient와 tacoId를 저장한다.. 구조를 바꾸었으니 그에 맞춰서 로직 변경 필요.

        
    //  // wrap ~ sauces 하나씩 돌면서 꺼내진 값에 맞는 ingredientId확인.. 하기 위해서는 순회하는 메서드 만들기 -> iterateIngredients
    // for(Ingredient ingredient : iterateIngredients(design.getOrderedIngredients())){
    //     saveIngredientToTaco(ingredient, tacoId);
    // }
      


    //    return design;

    // }

    // private List<Ingredient> iterateIngredients(OrderedIngredients orderedIngredients){
    //     List<Ingredient> list = new ArrayList<>();

    //     for(String id : orderedIngredients.getWrap()){
    //         list.add(jdbcIngredientRepository.findById(id));
    //     }
    //     for(String id : orderedIngredients.getCheese()){
    //         list.add(jdbcIngredientRepository.findById(id));
    //     }
    //     for(String id : orderedIngredients.getProtein()){
    //         list.add(jdbcIngredientRepository.findById(id));
    //     }
    //     for(String id : orderedIngredients.getSauces()){
    //         list.add(jdbcIngredientRepository.findById(id));
    //     }
    //     for(String id : orderedIngredients.getVeggies()){
    //         list.add(jdbcIngredientRepository.findById(id));
    //     }

    //     return list;

       

    // }

    // private long saveTacoInfo(Taco taco){
    //     taco.setCreatedAt(new Date());
    //     PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(
    //         "insert into taco (name, createdAt) values (?,?)",
    //         Types.VARCHAR,
    //         Types.TIMESTAMP
    //     );
    //     factory.setReturnGeneratedKeys(true);
        
    //     PreparedStatementCreator psc = factory.newPreparedStatementCreator(
    //         Arrays.asList(taco.getName(), new Timestamp(taco.getCreatedAt().getTime()))
    //     );
        
    //     KeyHolder keyHolder = new GeneratedKeyHolder();
    //     jdbc.update(psc, keyHolder);
        
    //     return keyHolder.getKey().longValue();
    // }

    // private void saveIngredientToTaco(Ingredient ingredient, long tacoId){
    //     jdbc.update("insert into taco_ingredients (taco_id, ingredient_id) values (?, ?)",tacoId, ingredient.getId());
    // }

    
}
