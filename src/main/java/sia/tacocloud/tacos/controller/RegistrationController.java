package sia.tacocloud.tacos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sia.tacocloud.tacos.model.RegistrationForm;
import sia.tacocloud.tacos.service.RegistrationService;





// @Controller
// @RequestMapping("register")
@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping()
    public ResponseEntity<Object> processRegistration(@RequestBody RegistrationForm form) {       
       return registrationService.registration(form);
    }
    


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

    
    
}
