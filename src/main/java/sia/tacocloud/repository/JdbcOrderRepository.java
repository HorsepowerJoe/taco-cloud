package sia.tacocloud.repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import sia.tacocloud.model.Order;
import sia.tacocloud.model.Taco;

@Repository
public class JdbcOrderRepository  implements OrderRepository{
    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;

    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbc) {
        this.orderInserter = new SimpleJdbcInsert(jdbc)
            .withTableName("taco_order")
            .usingGeneratedKeyColumns("id");

        this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
            .withTableName("taco_order_tacos");

        this.objectMapper = new ObjectMapper();
    }


    @Override
    public Order save(Order order) {
       order.setPlacedAt(new Date());
       long orderId = saveOrderDetails(order);
       order.setId(orderId);
       List<Taco> tacos = order.getTacos();

       for(Taco taco : tacos){
        saveTacoToOrder(taco, orderId);
       }
       return order;
    }

    private long saveOrderDetails(Order order){
        @SuppressWarnings("unchecked")
        Map<String, Object> values = 
            objectMapper.convertValue(order, Map.class);
            values.put("placedAt", order.getPlacedAt());

        long orderId =
            orderInserter
                .executeAndReturnKey(values)
                .longValue();
        return orderId;
    }

    private void saveTacoToOrder(Taco taco, long orderId){
        Map<String, Object> values = new HashMap<>();
        values.put("taco_order_id", orderId);
        values.put("taco_id", taco.getId());
        orderTacoInserter.execute(values);
    }
    
}
