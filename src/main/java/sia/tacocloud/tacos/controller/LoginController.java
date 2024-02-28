package sia.tacocloud.tacos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sia.tacocloud.tacos.model.User;
import sia.tacocloud.tacos.service.LoginService;


@RestController
@RequiredArgsConstructor
// @RequestMapping("/api/login")
public class LoginController {
    // private final LoginService loginService;

    // @PostMapping()
    // public ResponseEntity<Object> login(@RequestBody User user) {
    //    return loginService.login(user);
    // }
    
}
