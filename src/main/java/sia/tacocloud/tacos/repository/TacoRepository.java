package sia.tacocloud.tacos.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sia.tacocloud.tacos.model.Taco;
@Repository
public interface TacoRepository  extends CrudRepository<Taco, Long> {
    // Taco save(Taco design);
}
