package sia.tacocloud.tacos.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController implements ErrorController {

    @GetMapping({ "/design", "/orders/**", "/login", "/register" })
    public String home() {
        return "/index.html";
    }

    @GetMapping({"/","/error"})
    public String error() {
        return "/index.html";
    }

}
