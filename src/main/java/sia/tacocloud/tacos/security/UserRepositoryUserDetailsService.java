package sia.tacocloud.tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sia.tacocloud.tacos.model.User;
import sia.tacocloud.tacos.repository.UserRepository;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService{
    private UserRepository userRepository;

    @Autowired
    public UserRepositoryUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("작동중");
       User user = userRepository.findByUsername(username);
       if( user != null){
        return user;
       }
       throw new UsernameNotFoundException("User '"+ username + "' not found");
    }
    
}
