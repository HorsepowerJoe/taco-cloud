package sia.tacocloud.tacos.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Taco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date createdAt;
    @NotNull
    @Size(min=5, message="Name must be at least 5 characters long")
    private String name;
    private OrderedIngredients orderedIngredients;

@PrePersist
void createdAt(){
    this.createdAt = new Date();
}
}
