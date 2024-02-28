package sia.tacocloud.tacos.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Calendar;
import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    // private final DataSource dataSource;
    private final UserDetailsService userDetailsService;

    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/design", "/orders")
                        .hasRole("USER")
                        .requestMatchers("/api/design", "/api/design")
                        .access(this::hasRole)
                        .requestMatchers("/", "/**", "/api/**")
                        .access(this::permitAll)
                        .anyRequest()
                        .permitAll())
                .httpBasic(withDefaults());

        return http.build();
    }


    public AuthorizationDecision hasRole(Supplier<Authentication> authentication, RequestAuthorizationContext object){
        Authentication auth = authentication.get();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
            return new AuthorizationDecision(true);
        } else {
            return new AuthorizationDecision(false);
        }
    }


    public AuthorizationDecision permitAll(Supplier<Authentication> authentication, RequestAuthorizationContext object){
        return new AuthorizationDecision(true);
    }

    public AuthorizationDecision onlyUserAndTuesDay(Supplier<Authentication> authentication, RequestAuthorizationContext object){
        Calendar now = java.util.Calendar.getInstance();
        if(hasRole(authentication,object).isGranted() && now.get(now.DAY_OF_WEEK) == now.TUESDAY){
            return new AuthorizationDecision(true);
        }else{
            return new AuthorizationDecision(false);
        }
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return userDetailsService;
    }





    // @Bean
    // public JdbcUserDetailsManager jdbcUserDetailsService() {
    // PasswordEncoder passwordEncoder = passwordEncoder();
    // UserDetails user =
    // User.withUsername("user1").password("password1").roles("USER").passwordEncoder(passwordEncoder::encode).build();
    // JdbcUserDetailsManager jdbcUserDetailsManager = new
    // JdbcUserDetailsManager(dataSource);
    // jdbcUserDetailsManager.createUser(user);
    // return jdbcUserDetailsManager;
    // }
    // 
    // @Bean
    // public EmbeddedLdapServerContextSourceFactoryBean contextSourceFactoryBean() {
    //     EmbeddedLdapServerContextSourceFactoryBean contextSourceFactoryBean = EmbeddedLdapServerContextSourceFactoryBean
    //             .fromEmbeddedLdapServer();
    //     contextSourceFactoryBean.setPort(8389);
    //     contextSourceFactoryBean.setRoot("dc=tacocloud,dc=com");
    //     return contextSourceFactoryBean;
    // }

    // @Bean
    // LdapAuthoritiesPopulator authorities(BaseLdapPathContextSource contextSource) {
    //     String groupSearchBase = "ou=groups";
    //     DefaultLdapAuthoritiesPopulator authorities = new DefaultLdapAuthoritiesPopulator(contextSource,
    //             groupSearchBase);
    //     authorities.setGroupSearchFilter("(member={0})");
    //     return authorities;
    // }

    // @Bean
    // AuthenticationManager ldapAuthenticationManager(
    //         BaseLdapPathContextSource contextSource) {
    //     LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(contextSource);
    //     factory.setUserSearchBase("ou=people");
    //     factory.setUserSearchFilter("(uid={0})");
    //     return factory.createAuthenticationManager();
    // }
}
