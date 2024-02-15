package sia.tacocloud.tacos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import sia.tacocloud.model.Order;




@Slf4j
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @GetMapping("/current")
    public String orderForm(@RequestParam String param) {
        //원문에서는 주문 정보를 모델 객체로 받아와서 무언가를 처리하는 내용
        //프론트에서 처리해야할 내용?
        //주문정보 원기옥 모아서 -> api로 전송
        //원문을 따라가면 백에서 db임시저장 -> get요청시 보내주고 -> post요청 받아서 처리
        //임시 데이터를 db에 저장할 이유가 없어보임. 세션 스토리지에 넣어서 원기옥 모아 처리하기로 변경.
        return "";
    }

    @PostMapping()
    public String postMethodName(@RequestBody Order order) {
        log.info("Order submitted: "+order);
        return "";
    }
    
    
}
