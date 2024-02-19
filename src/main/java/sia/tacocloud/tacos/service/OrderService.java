package sia.tacocloud.tacos.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sia.tacocloud.model.Order;
import sia.tacocloud.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public ResponseEntity<Object> saveOrder(Order order) {
        try {
            orderRepository.save(order);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
           return ResponseEntity.badRequest().body(e.getMessage());
        }
       
        
    }
}
