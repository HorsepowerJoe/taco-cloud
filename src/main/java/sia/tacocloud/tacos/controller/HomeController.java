package sia.tacocloud.tacos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;





@Controller
public class HomeController {

    @GetMapping({"/", "/design", "/orders/**", "/login", "/register"})
    public String home() {
        return "/index.html";
    } 

    
}
