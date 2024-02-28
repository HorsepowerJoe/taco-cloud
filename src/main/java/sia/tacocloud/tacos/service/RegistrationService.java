package sia.tacocloud.tacos.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sia.tacocloud.tacos.model.RegistrationForm;
import sia.tacocloud.tacos.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<Object> registration(RegistrationForm form){
        try {
            userRepository.save(form.toUser(passwordEncoder));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
       
    }
    
}
