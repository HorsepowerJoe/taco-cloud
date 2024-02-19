package sia.tacocloud.repository;

import sia.tacocloud.model.Order;

public interface OrderRepository {
    Order save(Order order);
}
