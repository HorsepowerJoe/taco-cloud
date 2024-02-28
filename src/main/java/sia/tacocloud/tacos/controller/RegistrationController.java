package sia.tacocloud.tacos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sia.tacocloud.tacos.service.RegistrationService;




// @Controller
// @RequestMapping("register")
@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    // 뷰페이지 반환
    // @GetMapping()
    // public String registerForm() {
    //     return "registration";
    // }


    // 가입 로직 수행 => 유저레포 저장
    // @PostMapping()
    // public String processRegistration(RegistrationForm form) {
    //     userRepository.save(form.toUser(passwordEncoder));
    //     return "redirect:/login";
    // }

    // @PostMapping()
    // public String processRegistration(RegistrationForm form) {
    //     return RegistrationService.registration(form);
        
    //     return "redirect:/login";
    // }
    
    
}
