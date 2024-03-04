package sia.tacocloud.tacos.model;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;

@Data
public class RegistrationForm {
    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;

    public User toUser(PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setCity(city);
        user.setFullname(fullname);
        user.setPassword(passwordEncoder.encode(password));
        user.setPhoneNumber(phone);
        user.setRole("ROLE_USER");
        user.setState(state);
        user.setStreet(street);
        user.setUsername(username);
        user.setZip(zip);
        return user;
    }
}
