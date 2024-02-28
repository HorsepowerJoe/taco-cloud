package sia.tacocloud.tacos.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sia.tacocloud.tacos.model.User;
import sia.tacocloud.tacos.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;

    public ResponseEntity<Object> login(User user) {
      User findUser = userRepository.findByUsername(user.getUsername());
      if(findUser != null) {
        //정보 저장 후 respEntity에 무언가 담아서 리턴
        return ResponseEntity.ok().body("");
      }

      return ResponseEntity.badRequest().body("");

      

        
    }
}
