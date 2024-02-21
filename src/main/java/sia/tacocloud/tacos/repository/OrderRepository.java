package sia.tacocloud.tacos.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import sia.tacocloud.tacos.model.Order;


public interface OrderRepository  extends CrudRepository<Order, Long> {
    // Order save(Order order);
    List<Order> findByDeliveryZip(String deliveryZip);
    List<Order> readOrdersByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date startDate, Date endDate);
}
