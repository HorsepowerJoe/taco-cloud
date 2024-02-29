package sia.tacocloud.tacos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;





@Controller
public class HomeController {

    @GetMapping({"/", "/design", "/orders/**", "/login", "/register"})
    public String home() {
        return "/index.html";
    } 

    
}
