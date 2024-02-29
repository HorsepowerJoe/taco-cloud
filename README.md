<h2 align=center>실무를 통해 백엔드의 더 깊은 내용들을 접하고 싶었으나 취업이 되지 않아 <br />
  <br />
  Spring in Action으로 실무 섀도복싱 + 공식문서 Vue 3.0 시작합니다. <br />
  <br />
</h2>
 <br />

<h3>1. Spring in Action(taco-cloud)</h3>
<p>2. Node.JS 교과서</p>
<p>3. 서비스 운영이 쉬워지는 AWS 인프라 구축 가이드</p>
<br />


## 24-02-29
<b>JWT를 이용하여 Stateless한 인증 구현하기.</b><br />
<br />
책의 내용은 formLogin을 이용한 세션방식의 Stateful한 인증 구현이었다.<br />
그러나 나의 경우는 Vue와 Spring을 연동하여 백엔드를 Stateless하게 구현하여야 하기 때문에 책의 내용을 따라가지 않고,<br />
별도의 브랜치인 taco-cloud-with-thymeleaf-and-low-spring-version에 세션 방식의 로그인을 구현하였다.<br />
<br />

Stateless와 Stateful 그리고 RESTful 하다는 것은 무엇이고, 왜 나는 Stateless한 구현을 하여야 하는지에 대한 정리가 필요했다.<br />
무작정 '난 그냥 그렇게 하기로 했으니까 REST고 Stateless야' 라고 근거없이 우기기는 싫었기 때문이다. <br />
<br />

일단 나의 경우는 프론트엔드와 백엔드가 분리되어 있는 구조이다. <br />
Vue는 클라이언트 사이드에서 실행되기 때문에 클라이언트 사이드 처리는 WAS에서 직접적으로 이루어지지 않는다. <br />
<br />

Vue는 클라이언트 측에서 실행되기 때문에, 페이지 라우팅은 클라이언트 측에서 처리된다. <br />
유저가 브라우저에서 192.168.0.100:8080/design등의 주소로 요청을 보내면, 이 요청은 WAS에 도달하게 되지만 <br />
WAS는 해당 요청을 받아서 리액트 애플리케이션의 라우팅을 처리하는 것이 아니라, 단순히 정적 파일을 제공하는 역할만 수행한다. <br />
<br />

WAS는 요청된 주소에 대한 정적 파일(빌드된 HTML, CSS, JavaScript 파일 등)을 제공하게 되고<br />
클라이언트 사이드에서는 라우터가 해당 주소에 대한 뷰를 렌더링하고 사용자에게 보여주게 된다. <br />
<br />

이것을 CSR 즉, Client Side Rendering이라고 한다.<br />
CSR의 장점으로는 View 렌더링을 브라우저에서 하기 때문에 트래픽이 감소하고, 페이지를 넘어갈 때에 새로고침이 발생하지 않아 네이티브 앱과 비슷한 경험을 할 수 있다.<br />
단점으로는 서버에 처음으로 요청을 하게 되면 전체 페이지에 대한 모든 문서 파일을 받기 때문에 서버 사이드 렌더링보다는 속도가 느린 점이 있다.<br />
<br />

JSP나 Thymeleaf를 사용한 서버 사이드 렌더링 방식에서는 페이지마다 요청과 응답이 이루어지기 때문에 서버에서 이용자의 상태를 저장하기 편하다는 이점이 있다.<br />
그로 인하여 서버 측에서 세션을 사용해서 사용자의 로그인 상태나 기타 상태 정보를 유지하고 관리할 수 있다.<br />
<br />

반면에 Vue와 같은 클라이언트 측 렌더링 기술을 사용하는 경우에는 일반적으로 페이지 간의 이동이 AJAX를 통해 이루어지며<br />
페이지가 변경될 때마다 서버에 새로운 요청을 보내지 않기 때문에 서버 측에서 이용자의 상태를 저장하기가 어렵다.<br />
<br />

그렇기 때문에 프론트와 백엔드가 분리되어 클라이언트 사이드에서 상태를 관리하며, 서버와 통신이 필요할 시에는 REST API를 이용하여 Stateless한 통신을 하는 것이 바람직하다.<br />
<br />

REST API로 Stateless한 인증을 구현하는 방법으로는 대표적으로 Oauth와 JWT가 있는데. 나는 둘 다 전부 다 구현하여 어떻게 사용하는지 요목조목 살펴볼 예정이다.<br />
처음으로는 JWT를 사용하여 Stateless한 구현을 시작하기로 했다.<br />
<br />

JWT를 사용하여 인증 인가를 구현하려면 커스텀 필터를 사용하여야 한다.<br />
기존의 formLogin이나 httpBasic을 모두 disable하여 주고, REST API의 경우에는 CSRF공격에 취약하지 않기 때문에 csrf도 disable 한다.<br />
formLogin과 httpBasic을 disable하면 UsernamePasswordAuthenticationFilter가 꺼지게 되는데 JWT인증을 위해 UsernamePasswordAuthenticationFilter를 커스터마이즈 하여<br />
커스텀 필터로써 사용하면 된다.<br />
<br />

```
package sia.tacocloud.tacos.jwt;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public LoginFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        System.out.println("성공");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
                System.out.println("실패");
        
    }

    

}

```

<br />
여기서 평소에 사용하지 않았던 생성자 주입 방식을 사용하였는데 그 이유는 UsernamePasswordAuthenticationFilter의 기본 주소가 /login으로 되어 있기 때문이다.<br />
나는 Vue의 라우팅을 nonApiPattern으로 작성하고, API요청의 경우에는 /api/**의 패턴으로 작성하였기 때문의 기본 /login 주소를 변경할 필요가 있었다.<br />
<br />

```
 public LoginFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/api/login");
    }
```

<br />
생성자 주입을 사용하여 authenticationManager를 주입하고 setFilterProcessesUrl로 기본 주소를 /api/login으로 초기화 하였다.<br />
attemptAuthentication에서는 username과 password를 받아 검증을 담당하는 객체로 자료를 넘겨주는데, 넘겨주기 위해서는<br />
UsernamePasswordAuthenticationToken객체에 담아 보내주어야 한다.<br />
이렇게 보내진 UsernamePasswordAuthenticationToken은 AuthenticationManager에서 검증을 거치고 검증이 완료되면 successfulAuthentication이 동작되며<br />
그렇지 못한 경우에는 unsuccessfulAuthentication이 작동하게 된다.<br />
필터를 구현하였다면 SecurityFilterChain에 해당 Filter를 등록해 주어야 한다.<br />
<br />


```
 @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/design", "/orders")
                        .hasRole("USER")
                        .requestMatchers("/api/design", "/api/design")
                        .access(this::hasRole)
                        .requestMatchers("/", "/**", "/api/**")
                        .access(this::permitAll)
                        .anyRequest()
                        .permitAll())
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class)
                .httpBasic(withDefaults());

        return http.build();
    }
```

<br />
위에서는 addFilterAt을 사용하였는데, 그 이유는 formLogin과 httpBasic을 disable 하였기 때문이다.<br />
UsernamePasswordAuthenticationFilter가 비활성화 되었기 때문에 해당 자리에 원하는 필터를 주입하는 addFilterAt를 사용하여 등록시켰다.<br />
이제 LoginFilter는 UsernamePasswordAuthenticationFilter의 위치에서 UsernamePasswordAuthenticationFilter대신 사용된다.<br />

<br />



## 24-02-28
<b>formLogin 메서드에 대하여.</b><br />
formLogin 메서드는 Customizer<FormLoginConfigurer<HttpSecurity>>를 매개변수로 받아 폼 로그인을 커스터마이즈 할 수 있게 해준다.<br />
기존 .formLogin().loginPage("/login").usernameParameter()......의 방식에서<br />
<br />

```
.formLogin(formLogin->{ formLogin
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .loginPage("/login")
                            .loginProcessingUrl("/api/login")
                            .defaultSuccessUrl("/design", true);
                        })
```
<br />
위 방식으로 구현하도록 변경, 권장되었다. <br />
로그아웃 또한 마찬가지로 다음과 같다.<br />
<br />

```
.logout(logout -> { logout
			.deleteCookies("remove")
			.invalidateHttpSession(false)
			.logoutUrl("/logout")
			.logoutSuccessUrl("/");

                })
```
<br />

그러나 이번에는 백엔드를 RESTful Api로 사용할것이기 때문에 폼 로그인을 사용하지 않고 다른 방식으로 구현하여야 하기 때문에 <br />
부득이하게 새로운 프로젝트를 생성하여 타임리프와 스프링 사용으로 전환하여야 했다. <br />
새로운 마이그레이션 브랜치 taco-cloud-with-thymeleaf-and-low-spring-version를 만들어보자.<br />
<br />
<hr />
이제 책의 내용을 따라갈 수 있게 되었다<br />
스프링 시큐리티의 마지막 챕터 사용자 인지하기 챕터이다.<br />
핵심적인 키워드는 AuthenticationPrincipal이다.<br />
<br />

로그인 객체를 가져오는 방법으로는 SecurityContextHolder, Principal, @AuthenticationPrincipal이 있다.<br />
모든 내용은 공식 문서 https://docs.spring.io/spring-security/reference/servlet/integrations/servlet-api.html#servletapi-remote-user 를 참고하였다.
<br />
<hr />
<br />

<b>1. SecurityContextHolder를 사용하는 방법</b><br />
<br />
HttpServletRequest.getRemoteUser()은 현재 사용자 이름을 반환하는 SecurityContextHolder.getContext().getAuthentication().getName()의 결과를 반환한다.<br />
이를 이용하여 null이 리턴된다면 사용자가 인증되었는지 아니면 익명인지 여부를 결정할 수 있다.<br />
사용자가 인증되었는지 여부를 파악하는 것은 특정 UI 요소가 표시되어야 하는지 여부를 결정하는 데 유용할 수 있다.<br />
ex) 로그아웃 링크는 사용자가 인증된 경우에만 표시<br />
<br />
<br />

HttpServletRequest.getUserPrincipal()은 SecurityContextHolder.getContext().getAuthentication()의 결과를 반환한다.<br />
사용자 이름과 비밀번호를 사용한 인증을 사용할 때 UsernamePasswordAuthenticationToken의 인스턴스이다.<br />
사용자에 대한 추가 정보가 필요한 경우 유용할 수 있다.<br />
<br />

```
// 사용자의 Principal 정보를 가져온다.
Authentication auth = httpServletRequest.getUserPrincipal();

//UserDetails의 인스턴스
MyCustomUserDetails userDetails = (MyCustomUserDetails) auth.getPrincipal();

// UserDetails에서 사용자의 이름을 가져온다.
String firstName = userDetails.getFirstName();
// UserDetails에서 사용자의 성을 가져온다.
String lastName = userDetails.getLastName();
```

<br />
<br />
HttpServletRequest.getUserPrincipal()는 SecurityContextHolder.getContext().getAuthentication()의 결과를 반환한다.<br />
사용자 이름과 비밀번호 기반 인증을 사용할 때 UsernamePasswordAuthenticationToken의 인스턴스이다.<br />
이 메서드는 사용자에 대한 추가 정보가 필요한 경우 유용하다.<br />
<br />

```
// HttpServletRequest.getUserPrincipal()를 통해 현재 사용자의 인증 정보를 가져온다.
Authentication auth = httpServletRequest.getUserPrincipal();

MyCustomUserDetails userDetails = (MyCustomUserDetails) auth.getPrincipal();

// 사용자의 성과 이름을 가져온다.
String firstName = userDetails.getFirstName();
String lastName = userDetails.getLastName();
```

<br />
HttpServletRequest.isUserInRole(String) 메서드는 SecurityContextHolder.getContext().getAuthentication().getAuthorities()에서<br />
isUserInRole(String)로 전달된 역할을 가진 GrantedAuthority가 포함되어 있는지 여부를 결정한다.<br />
주의할 점은 ROLE_이 자동으로 붙기 때문에 따로 ROLE_을 붙이지 않아야 한다는 것.<br />
<br />

```
boolean isAdmin = httpServletRequest.isUserInRole("ADMIN");
```
<br />


<b>2. Principal을 사용하는 방법</b><br/>
<br/>

```
@Controller 
public class MyController { 

    @GetMapping("/username") 
    @ResponseBody 
    public String currentUserName(Principal principal) { 
       User user = myRepository.findByUsername(principal.getName());
       return user;
    } 
}
```
<br />
Principal을 사용한다면 위의 코드처럼 Controller에서 유저 정보를 불러올 수 있다..<br />
그러나 Principal은 Spring Security의 객체가 아니라 Java의 객체이기 때문에 getName()외에는 사용할만한 메서드가 없다.<br />
Principal 대신 Authentication 객체를 인자로 받도록 하는 코드도 가능하다.<br />
<br />

```
@Controller 
public class MyController { 

    @GetMapping("/username") 
    @ResponseBody 
    public String currentUserName(Authentication authentication) { 
	User user = (User) authentication.getPrincipal();
	return user;
    } 
}
```
<br />
Authentication 객체를 얻은 다음 getPrincipal()을 호출하여 Principal객체를 얻을 수 있다.<br />
단, getPrincipal()은 java.util.Object 타입을 반환하므로 원하는 타입으로 변환을 해야 하니 주의하자.<br />
<br />
<br />

<b>3. @AuthenticationPrincipal을 사용하는 방법</b><br />
1번과 2번과 같은 방법들은 보안과 관련 없는 코드들이 혼재하여 비효율적이며, 기능적인 부분에서 다양하지 않다는 단점이 있다.<br />
Spring Security 3.2부터는 @AuthenticationPrincipal 어노테이션으로 Custom 로그인 객체를 가져올 수 있다.
@AuthenticationPrincipal을 사용한 예는 다음과 같다. <br />
<br />

```
@Controller 
public class MyController { 

    @GetMapping("/username") 
    @ResponseBody 
    public String currentUserName(@AuthenticationPrincipal User user) { 
	return user;
    } 
}
```

<br />
@AuthenticationPrincipal의 장점으로는 타입 변환이 필요 없고 Authentication과 동일하게 보안 특정 코드만 갖는다.<br />
또한 Custom 로그인 객체를 가져올 수 있기 때문에 기능적 활용도가 높다는 것이다.<br />
<br />
이제 스프링 시큐리티 챕터가 모두 끝났다. 책은 Thymeleaf와 구버전 Spring Security를 사용하여 작성되었기 때문에 시간이 많이 걸리고 있다.<br />
책의 환경과 같이 taco-cloud-with-thymeleaf-and-low-spring-version을 구성하였으니 이제 두 마리 토끼를 잡을 수 있다.<br />
<br />
다음은 쉬어가는 챕터 구성 속성 사용하기.<br />
학원에서 배운 내용들이기에 복습 개념으로 읽어보면 되겠다.<br />



## 24-02-26
UserDetails 인터페이스를 구현한 User 클래스에 대하여. <br />
<br />

UserDetails를 구현한 User 클래스는 기본 사용자 정보를 프레임워크에 제공한다.<br />
예를 들어, 해당 사용자에게 부여된 권한과 해당 사용자 계정을 사용할 수 있는 지의 여부등을 제공하는 것이다.<br />
<br />

getAuthorities() 메서드는 해당 사용자에게 부여된 권한을 저장한 컬렉션을 반환한다.<br />
메서드 이름이 is로 시작하고 Expired로 끝나는 다양한 메서드들은 해당 사용자 계정의 활성화 또는 비활성화 여부를 나타내는 boolean값을 반환한다.<br />
<br />
<hr />
<br />

<b>스프링 표현식</b>

스프링 시큐리티에서는 hasRole(), paermitAll() 등의 보안 요구 선언 메서드들이 있다. <br />
이 중 SpEL표현식이 true일 때 접근을 허용하는 access(String) 메서드가 있는데, Spring Security 6에서는<br />
인자로 String 타입이 아닌 AuthorizationManager<RequestAuthorizationContext>를 받는 것으로 변경되었다.<br />

AuthorizationManager는 무엇일까? 공식 문서에서는 다음과 같이 설명하고 있다.<br />
<br />

```
Spring Security는 메소드 호출 또는 웹 요청과 같은 보안 객체에 대한 액세스를 제어하는 인터셉터를 제공합니다.
Invocation Handling은 AuthorizationManager 인스턴스에 의해 메소드 호출이나 웹 요청이 진행될 수 있는지에 대한 사전 호출 결정을 의미합니다.
또한 AuthorizationManager 인스턴스에 의해 반환된 값이 허용되는지에 대한 사후 호출 결정도 이루어집니다.

AuthorizationManager는 AccessDecisionManager와 AccessDecisionVoter를 대체합니다.
AccessDecisionManager 또는 AccessDecisionVoter를 사용자 지정하는 애플리케이션은 AuthorizationManager를 사용하도록 변경하는 것이 좋습니다.
AuthorizationManager는 Spring Security의 요청 기반, 메소드 기반 및 메시지 기반 인가 구성 요소에서 호출되며 최종 액세스 제어 결정을 담당합니다.
AuthorizationManager 인터페이스에는 두 가지 메소드가 포함되어 있습니다.

AuthorizationDecision check(Supplier<Authentication> authentication, Object secureObject);

default AuthorizationDecision verify(Supplier<Authentication> authentication, Object secureObject)
        throws AccessDeniedException {
    // ...
}
AuthorizationManager의 check 메소드는 인가 결정을 내리기 위해 필요한 모든 관련 정보를 전달받습니다.
특히, secure Object를 전달함으로써 실제 보안 객체 호출에 포함된 인수를 검사할 수 있습니다.
예를 들어, 보안 객체가 MethodInvocation인 경우 MethodInvocation에서 Customer 인수를 쿼리하고,
그에 따라 AuthorizationManager에서 고객에 대한 작업이 허용되었는지 확인하는 보안 로직을 구현할 수 있습니다.
구현체는 액세스가 허용되면 양수인 AuthorizationDecision을 반환하고
액세스가 거부되면 음수인 AuthorizationDecision을 반환하며
결정을 보류하는 경우에는 null인 AuthorizationDecision을 반환해야 합니다.

verify는 check를 호출하고, 액세스가 거부되는 경우 AccessDeniedException을 발생시킵니다.
```
<br />
공식 문서에서 설명하였던 AuthorizationManager는 다음과 같이 구성되어 있다.<br />
<br />

```
@FunctionalInterface
public interface AuthorizationManager<T> {

	/**
	 * Determines if access should be granted for a specific authentication and object.
	 * @param authentication the {@link Supplier} of the {@link Authentication} to check
	 * @param object the {@link T} object to check
	 * @throws AccessDeniedException if access is not granted
	 */
	default void verify(Supplier<Authentication> authentication, T object) {
		AuthorizationDecision decision = check(authentication, object);
		if (decision != null && !decision.isGranted()) {
			throw new AccessDeniedException("Access Denied");
		}
	}

	/**
	 * Determines if access is granted for a specific authentication and object.
	 * @param authentication the {@link Supplier} of the {@link Authentication} to check
	 * @param object the {@link T} object to check
	 * @return an {@link AuthorizationDecision} or null if no decision could be made
	 */
	@Nullable
	AuthorizationDecision check(Supplier<Authentication> authentication, T object);

}
```
<br />
verify 메서드는 주어진 authentication 및 object를 사용하여 액세스 권한을 확인하고, 액세스가 거부되면 공식 문서의 설명과 같이 AccessDeniedException을 발생시킨다. <br />
여기에서 Supplier<Authentication> authentication은 현재 사용자의 인증 정보를 제공하는 Supplier이고, 이 Supplier를 통해 현재 사용자의 인증 정보를 얻을 수 있다. <br />
T object는 액세스 권한을 확인할 대상 객체를 나타낸다.<br />
<br />

먼저, check 메서드를 사용하여 주어진 authentication과 object에 대한 액세스 결정을 수행하며 그 결과를 AuthorizationDecision 객체로 받는다.<br />
그런 다음, decision이 null이 아니고 권한이 부여되지 않았다면ㅡ즉. decision.isGranted()가 false인 경우ㅡ "Access Denied" 메시지를 가진 AccessDeniedException을 던진다.<br />
<br />

기존의 access는 SpEL표현식을 사용하여 인증을 진행하였지만, 바뀐 Spring Security 6에서는 request를 받아 AuthorizationDecision(boolean)으로 처리해 주어야 한다.<br />
access의 사용 방법에 대해서는 학습하였으니 책의 내용으로 들어가보자.<br />
<hr />

좋은 예가 바로 나왔다 p.151)<br />
<br />

```
@Override
protected void configure(HttpSecurity http) throws Exception {
	http
	  .authorizeRequests()
	  .antMatchers("/design", "/orders")
	    .access("hasRole('ROLE_USER')")
	  .antMatchers("/", "/**").access("permitAll");
}
```
<br />
위의 내용을 Spring Security 6버전의 방식으로 바꾸게 된다면 아래와 같아진다.<br />
<br />

```
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
```
<br />
더 복잡한 심화 내용으로 들어가보자.. 위의 내용을 알았다면 분명 할 수 있을 것이다.. p.151)<br />
<br />

```
@Override
protected void configure(HttpSecurity http) throws Exception {
	http
	  .authorizeRequests()
	  .antMatchers("/design", "/orders")
	    .access("hasRole('ROLE_USER') && " +
	      "T(java.util.Calendar).getInstance().get(" +
	      "T(java.util.Calendar).DAY_OF_WEEK) == " +
	      "T(java.tuil.Calendar).TUESDAY")
	  .antMatchers("/", "/**").access("permitAll");
}
```
<br />
아..<br />
러프하게 생각해보자<br />
T(java.util.Calendar).getInstance().get(T(java.util.Calendar).DAY_OF_WEEK) == T(java.tuil.Calendar).TUESDAY<br />
결국 화요일에만 ROLE_USER의 제약조건이 승인되는 것인데..<br />

<br />

AuthorizationManager의 verify에는 request 객체를 인자로 받고 있다. 그 말은 리퀘스트 객체의 세션에서 요청이 언제 이루어 졌는지 시간 정보를 알 수 있을 것이다.<br />
그러나 이 방법은 클라이언트쪽에서 조작을 할 수 있지 않을까 하는 걱정이 있으니..<br />
책과 같이 java.util.Calendar.getInstance()를 사용하여 구현해보자.<br />

<br />

```
   public AuthorizationDecision onlyUserAndTuesDay(Supplier<Authentication> authentication, RequestAuthorizationContext object){
        Calendar now = java.util.Calendar.getInstance();
        if(hasRole(authentication,object).isGranted() && now.get(now.DAY_OF_WEEK) == now.TUESDAY){
            return new AuthorizationDecision(true);
        }else{
            return new AuthorizationDecision(false);
        }
    }
```

챕터 하나가 통째로 무용지물이 되었으니.. 빠르게 진도를 나가봐야겠다.. <br />
<br />
<hr />
<br />







## 24-02-23
오늘은 Spring Security에 내장된 세 번째 사용자 스토어인 LDAP 기반의 사용자 스토어를 알아볼 것이다.<br />
책에서는 WebSecurityConfigurerAdapter를 상속받아 Configure를 Override하는 방식으로 시큐리티를 구성하였다..<br />
코드는 다음과 같다.<br />
```
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .ldapAuthentication()
            .userSearchBase("ou=people")
            .userSearchFilter("(uid={0})")
            .groupSearchBase("ou=group")
            .groupSerachFilter("member={0}")
            .contextSource()
            .root("dc=tacocloud,dc=com")
            .ldif("classpath:users.ldif")
            .and()
            .passwordCompare()
            .passwordEncoder(new BCryptPasswordEncoder())
            .passwordAttribute("userPasscode");
            .contextSource().url("ldap://tacocloud.com:389/dc=tacocloud,dc=com")
    }

}
```
<br />

첫 페이지 부터 진도가 나가지 않았다. 바뀐 Spring Security 6에서는 당연하게도 위 방식의 작성을 지원하지 않았기 때문이다.  
기술 블로그에서도 LDAP 방식은 잘 사용하지 않았는지 제대로 포스팅 된 내용들이 없어 공식 문서를 참고하게 되었다.  

먼저 LDAP라는 것에 대해서 알 필요가 있는데.  
  
LDAP(Lightweight Directory Access Protocol)는 경량 디렉터리 접근 프로토콜로, X.500이라는 DAP의 경량화된 버전이라 Lightweight가 붙게 되었다.  
<br />
<br />
핵심적인 내용으로는  
1. HTTP Protocol과 유사하게 Request와 Response를 가지고 있고, ldap://xxxx.xxxx.xx:port와 같은 형태로 URL이 정의되어 HTTP와 유사한 점이 많다.  
2. HTTP와 다르게 LDAP는 인증이 되어야만 Request와 Response가 가능하다.  
3. HTTP는 평문으로 전송하여 패킷 탈취 가능성이 있지만 LDAP의 경우에는 인코딩을 적용하여 바이너리 포맷으로 전송하기 때문에 보안의 이점이 있다.  
4. LDAP는 LDIF 포맷으로 데이터를 Tree 구조로 저장한다.
<br />
<br />

이제 LDAP인증을 Spring Security 6 방식으로 구현할 차례다.  
기존의 간단한 위 내용과는 달리 LDAP방식의 인증을 Spring Security 6로 구현하려면 먼저 다음의 과정들이 필요하다.  
내용들은 https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/ldap.html 을 참고하여 작성하였다. 
<br />
<hr />
<br />
1. 구성을 지정할 LDAP 서버가 필요하다.<br />
<br />
LDAP방식의 인증은 LDAP서버를 통해서 처리되기 때문에 먼저 LDAP서버를 셋업할 필요가 있다.<br />
기존에는 모든 과정이 사전 구성 되어있어 셋업이 필요 없었지만<br />
Spring Security 6에서는 컴포넌트 방식으로 구성하여야 하기 때문에 코드의 양이 더 많아진다.<br />
<br />

```
@Bean
public EmbeddedLdapServerContextSourceFactoryBean contextSourceFactoryBean() {
    //Embedded LDAP 서버 컨텍스트 소스 Factory Bean을 생성
    EmbeddedLdapServerContextSourceFactoryBean contextSourceFactoryBean = EmbeddedLdapServerContextSourceFactoryBean
            .fromEmbeddedLdapServer();
    
    //책에서는 389포트를 사용한다고 하는데 나는 8000번대 포트로 통일시켰다.
    contextSourceFactoryBean.setPort(8389);
    
    //책에서 .root("dc=tacocloud,dc=com")로 지정했던 것이 이 방식에서는 아래와 같다.
    contextSourceFactoryBean.setRoot("dc=tacocloud,dc=com");


    return contextSourceFactoryBean;
}
```
<br />

contextSourceFactoryBean은 Spring Security에서 LDAP 서버와의 연결을 설정하는 데 사용된다.  
이를 통해 Spring 애플리케이션이 LDAP 서버로부터 사용자 인증 및 권한 부여를 수행할 수 있는 것이다.  
  
EmbeddedLdapServerContextSourceFactoryBean은 내장된 LDAP 서버와의 연결을 설정하는 데 사용되며  
설정된 포트 및 루트 DN과 같은 구성 속성을 사용하여 LDAP 서버에 연결된다.  
  
이 방식을 Embedded UnboundID Server방식이라고 하는데. 실행을 시켜보면 
<br />

```
org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'authorities' defined in class path resource [sia/tacocloud/tacos/security/SecurityConfig.class]: Unsatisfied dependency expressed through method 'authorities' parameter 0: Error creating bean with name 'contextSourceFactoryBean': FactoryBean threw exception on object creation
.
.
.
Caused by: java.lang.IllegalStateException: Embedded LDAP server is not provided
```

<br />
위와 같은 에러가 발생할텐데..<br />
여기서 정말 많이 헤매었다. 공식 문서를 찾아서 보았을 때는 종속성으로 unboundid-ldapsdk가 추가되어 있어야 작동한다는 것.<br />
책에서는 이 부분을 짚지 않았어서 종속성이 문제일 것이라 생각지 못하고 삽질을 많이 했었다.<br />
그리고 공식 문서를 찾아보고 EmbeddedLdapServerContextSourceFactoryBean의 내용을 뜯어보면서 그 이유를 알게 되었다.<br />  
<br />

```
	private static final String UNBOUNDID_CLASSNAME = "com.unboundid.ldap.listener.InMemoryDirectoryServer";

	private static final boolean unboundIdPresent;

	static {
		ClassLoader classLoader = EmbeddedLdapServerContextSourceFactoryBean.class.getClassLoader();
		unboundIdPresent = ClassUtils.isPresent(UNBOUNDID_CLASSNAME, classLoader);
	}

@Override
	public DefaultSpringSecurityContextSource getObject() throws Exception {
		if (!unboundIdPresent) {
			throw new IllegalStateException("Embedded LDAP server is not provided");
		}
		this.container = getContainer();
		this.port = this.container.getPort();
		DefaultSpringSecurityContextSource contextSourceFromProviderUrl = new DefaultSpringSecurityContextSource(
				"ldap://127.0.0.1:" + this.port + "/" + this.root);
		if (this.managerDn != null) {
			contextSourceFromProviderUrl.setUserDn(this.managerDn);
			if (this.managerPassword == null) {
				throw new IllegalStateException("managerPassword is required if managerDn is supplied");
			}
			contextSourceFromProviderUrl.setPassword(this.managerPassword);
		}
		contextSourceFromProviderUrl.afterPropertiesSet();
		return contextSourceFromProviderUrl;
	}
```
<br />
private static final String UNBOUNDID_CLASSNAME = "com.unboundid.ldap.listener.InMemoryDirectoryServer";  <br />
이 코드는 UnboundID 라이브러리의 InMemoryDirectoryServer 클래스의 패키지 경로를 문자열로 할당한다.  <br />
InMemoryDirectoryServer는 UnboundID에서 제공하는 내장형 LDAP 서버를 생성하기 위해 사용될 것이다.  <br />
  <br />
이후 ClassLoader에 의해 동적으로 로딩되어 ClassUtils.isPresent()에 의해 존재 여부를 확인하게 되고.  <br />
getObject()에서 로딩이 되지 않았을 경우 ㅡ즉. 해당 라이브러리가 없을 경우ㅡ  <br />
new IllegalStateException("Embedded LDAP server is not provided")을 반환한다.  <br />
<br />
그랬다.. 종속성의 문제였다..  <br />
pom.xml에 해당 내용을 추가하였고, 문제를 해결하였다.  <br />
<br />
<hr />
<br />
2. 책의 내용을 따라가기 위하여 ldapAuthenticationManager를 구현한다.<br />
<br />

```
@Bean
    AuthenticationManager ldapAuthenticationManager(
            BaseLdapPathContextSource contextSource) {
        LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(contextSource);
        factory.setUserSearchBase("ou=people");
        factory.setUserSearchFilter("(uid={0})");
        return factory.createAuthenticationManager();
    }
```
<br />
기존 책의 내용과는 다르게 ldapAuthenticationManager에서는 groupSettings를 할 수 없다.<br />
groupSettings를 위해서는 LdapAuthoritiesPopulator의 구현이 필요하다.<br />
LdapAuthoritiesPopulator는 사용자에게 어떤 권한이 반환되는지 결정하는 데 사용된다.<br />
<br />

```
 @Bean
    LdapAuthoritiesPopulator authorities(BaseLdapPathContextSource contextSource) {
        String groupSearchBase = "ou=groups";
        DefaultLdapAuthoritiesPopulator authorities = new DefaultLdapAuthoritiesPopulator(contextSource,
                groupSearchBase);
        authorities.setGroupSearchFilter("(member={0})");
        return authorities;
    }
```
<br />

이제 내장 LDAP 사용을 위한 구성이 끝났다.<br />
책에서는 원격 LDAP서버 참조에 대해서도 설명하여 놓았기에 새로운 방식의 외장 LDAP 서버를 참조하는 방법에 대해서도 알아보았다.<br />
<br />

```
ContextSource contextSource(UnboundIdContainer container) {
	return new DefaultSpringSecurityContextSource("ldap://tacocloud.com:8389/dc=tacocloud,dc=com");
}
```
<br />

정말 험난한 길이다. 그래도 새로운 것을 배우고 익힌다는 것은 언제나 마음을 든든하게 해 준다.<br />
책에서는 2페이지로 정리된 내용이지만.. 쉽지 않았다.. 정보가 많지 않아서 어려움이 많았던 것 같다.<br />
공식 문서를 찾아보는 습관을 굳히는 데에 도움이 되었다.<br />

<hr />


## 24-02-22
기존 책의 내용은 WebSecurityConfigurerAdapter를 상속받아 시큐리티를 구성하였다.<br />

```
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure (HttpSecurity http) throws Exception {
    http
    .authorizeRequests()
      .antMatchers("/design", "/orders")
        .access("hasROLE('ROLE_USER')")
      .antMatchers("/", "/**").access("permitAll")
    .and()
      .httpBasic();
  }
  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
          .withUser("user1")
          .password("{noop}password1")
          .authorities("ROLE_USER")
  }
}
```

하지만 바뀐 Spring Security 6에서는 람다 DSL을 사용하여 SecurityFilterChain을 구현하도록 권장하고 있다.<br />
위의 내용을 SpringFilterChain 방식으로 바꾸게 되면 다음과 같다.
```
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((auth) -> auth
                                .requestMatchers("/design", "/orders")
                                .hasRole("USER")
                                .requestMatchers("/api", "/api/**")
                                .hasRole("USER")
                                .requestMatchers("/", "/**")
                                .permitAll()
                                .anyRequest()
                                .permitAll()
                ).httpBasic(withDefaults());
        
                return http.build();
    }

      @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
            .username("admin")
            .password("admin")
            .roles("ADMIN")
            .username("user")
            .password("user")
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }
}
```
핵심적인 내용은 다음과 같다.<br />
<ul>
  <li>모든 내용은 람다 DSL방식으로 작성되어야 한다는 것.</li>
  <li>antMatchers는 사라졌고(deprecated가 아니다) requestMatchers를 사용하여야 한다는 것.</li>
  <li>기존 오타에 취약하고 난해하였던 .access(hasROLE('ROLE_USER')) 방식이 표현이 명확해 졌다는 것.</li>
  <li>컴포넌트 방식으로 구현을 하기 때문에 자유롭게 필터 순서를 구성할 수 있다는 것.</li>
</ul>
  
원래는 버전을 낮추어 책의 내용을 따라가려고 했지만.<br />
분명히 나보다 머리 좋고 더 뛰어나고 더 지식적으로 풍요로운 사람들의 집단에서<br />
무언가를 사용하지 않기를 권장하고 더 나은 사용법으로 유도하는 것은<br />
다 그만한 이유가 있을 것이라고 판단했는데 아니나 다를까 다 마땅한 이유가 있었다.<br />
<br />
위 핵심 내용 정리를 토대로 얻을 수 있는 이점으로는<br />
1. 모든 내용이 람다 DSL 방식으로 구성되면 보안 구성을 보다 간결하고 직관적으로 작성 가능하다는 이점이 있다. 타입의 안정성은 덤으로.<br />
2. antMatchers에서 requestMatchers로 바꾸어 사용하게 되면 객체를 사용 가능하기 때문에 복잡한 규칙도 정의가 가능하다.<br />
```
.requestMatchers(HttpMethod.POST, "/api/**").authenticated() -> 특정 HTTP 메서드와 경로에 대한 보안 설정
.requestMatchers(new IpAddressMatcher("192.168.1.0/24")).authenticated() -> 특정 IP 주소에서의 요청에 대한 보안 설정
.requestMatchers(new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest")).permitAll() -> 특정 요청 헤더 값을 기반으로 보안 설정
```
3. 기존 오타에 취약하고 난해하였던 .access(hasROLE('ROLE_USER')) 방식의 표현이 명확해 졌다는 것.
4. 컴포넌트 방식으로 구현을 하기 때문에 자유롭게 필터 순서를 구성할 수 있다는 것.

<hr />

JDBC기반의 사용자 스토어 인증 부분 p.125<br />
기존 WebSecurityConfigurerAdapter를 상속받아 configure를 오버라이드하여 작성하는 구문은 다음과 같다.<br />
```
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
  auth
    .jdbcAuthentication()
    .dataSource(dataSource)
    .passwordEncoder(new BCryptPasswordEncoder());
}
```
그러나 스프링 시큐리티6에서 JDBC 기반 사용자 스토어 인증 구성은 컴포넌트 방식으로 이루어지기 때문에 다음과 같다.
```
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

@Bean
    public JdbcUserDetailsManager jdbcUserDetailsService() {
        return new JdbcUserDetailsManager(dataSource);
    }
```
빈으로 패스워드 인코더를 등록하면 자동 구성이 완료되기 때문에 따로 passwordEncoder를 지정할 필요가 없다.<br />
물론 UserDetails를 사전 구성 해야한다면 passwordEncoder를 사용하여야 한다.<br />
<br />
책에서는 PasswordEncoder를 구현한 NoEncodingPasswordEncoder를 사용하여 임시로 평문 처리를 하였지만<br />
스프링 시큐리티 6버전 부터는 패스워드를 평문으로 처리하는 것 자체를 막아두었기에 다음과 같이 코드를 바꾸어 주었다.<br />
```
 @Bean
    public JdbcUserDetailsManager jdbcUserDetailsService() {
        PasswordEncoder passwordEncoder = passwordEncoder();
        UserDetails user = User.withUsername("user1").password("password1").roles("USER").passwordEncoder(passwordEncoder::encode).build();
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.createUser(user);
        return jdbcUserDetailsManager;
    }
```
<hr />
<b>사용자 정보 쿼리의 커스터마이징</b><br />
중요한 내용이라 정리하였다.<br />
<br />
UserDetailsManager는 스프링 시큐리티 기본 db 테이블을 사용하고 있다.
기본적인 테이블 구성은 다음과 같다.

```
사용자 정보 테이블
TABLE users(
  username,
  password,
  enabled
)

사용자 권한 테이블
TABLE authorities (
    username,
    authority,
    CONSTRAINT fk_authorities_users
        FOREIGN KEY (username) REFERENCES users(username)
);

authorities 테이블에서 username과 authority 열에 대한 고유 인덱스
UNIQUE INDEX ix_auth_username
    ON authorities (username, authority);
```

그룹의 사용자의 경우에는 group_members와 group_authorities를 사용한다.<br />
사전 지정된 db tables, sql query를 사용하려면 관련 테이블을 생성하고 사용자 데이터를 추가하여야 한다.<br />
<br />
그러나 사전 지정된 db tables와 sql query가 아닌 커스터마이즈 된 것들로 사용하기 위해서는<br />
UserDetailsManager의 set 메서드들을 찾아보면 된다.<br />
```  
.userByUsernameQuery("select username, password, enabled from users where username=?")  
.authoritiesByUsernameQuery("select username, authority from authorities where username=?")  
```
<br />

## 24-02-21
JPA 챕터에 들어왔다. 지금까지 JPA를 썼던 방식은 정말 기초적인 부분이었음을 다시 깨닫게 되었다.<br />
레포 구현체를 생성할 때 스프링 데이터는 해당 레포 인터페이스에 정의된 메서드를 찾아 메서드 이름을 분석하며<br />
저장되는 객체의 컨텍스트에서 메서드의 용도가 무엇인지 파악한다.<br />
책에서는 이를 'DSL을 정의하고 있어 퍼시스턴스에 관한 내용이 레포 메서드의 시그니처에 표현된다.'고 서술하였다.
DSL이 무엇일까.<br />

JPA의 DSL은 데이터베이스와 상호작용하기 위한 쿼리를 생성하는 데 사용된다.<br />
DSL은 Criteria API와 JPQL(Java Persistence Query Language)을 포함하는데 이는 다음과 같다.<br />

Criteria API는 JPQL (Java Persistence Query Language) 조회의 문자열을 빌드하는 대신 JAVA 오브젝트를 사용하여 조회를 빌드하기 위한 API.<br />
런타임 시 동적으로 조회 빌드를 지원하며 컴파일러가 검증할 수 있는 유형 안전 조회를 빌드하는 기능도 지원하고 <br />
JPQL을 통해서는 불가능한 조회를 표현할 수 있다고 한다.<br  />

JPQL은 무엇이길래 JPQL로는 불가능한 조회를 Criteria API는 표현할 수 있다고 하는 걸까?<br />

JPQL(Java Persistence Query Language): JPQL은 객체지향 쿼리 언어로, 엔티티와 관련된 객체를 대상으로 쿼리를 작성할 수 있다고 한다.<br />
JPQL은 SQL과 비슷한 구문을 가지고 있지만, 엔티티와 속성을 기준으로 쿼리를 작성하므로 객체를 직접 다루는 데 더 적합하다고 한다.<br />
JPQL은 문자열 형태로 작성되며, EntityManager를 통해 실행된다.<br />

글로만 보면 잘 이해가 되지 않았다. 다음의 내용은 Criteria API에서 제공하는 Docs의 일부이다.<br />


```
목록 1. 단순하고 잘못된 JPQL 쿼리
EntityManager em = ...;
String jpql = "select p from Person where p.age > 20";
Query query = em.createQuery(jpql);
List result = query.getResultList();

이 기본 예는 JPA 1.0의 쿼리 실행 모델의 다음과 같은 주요 측면을 보여줍니다.

JPQL 쿼리는 String(라인 2)로 지정됩니다.
EntityManagerJPQL 문자열(3행)이 주어지면 실행 가능한 쿼리 인스턴스를 생성하는 팩토리입니다.
쿼리 실행 결과는 untyped로 구성됩니다 java.util.List.
그러나 이 간단한 예에는 심각한 오류가 있습니다. 실제로 코드는 문제 없이 컴파일되지만 JPQL 쿼리 문자열의 구문이 올바르지 않기 때문에 런타임 시 실패하게 됩니다. 목록 1 의 두 번째 줄에 대한 올바른 구문은 다음과 같습니다.

String jpql = "select p from Person p where p.age > 20";

불행하게도 Java 컴파일러는 이러한 오류를 감지할 방법이 없습니다. 오류는 런타임 시 3행 또는 4행에서 발생합니다(JPA 공급자가 쿼리 구성 또는 실행 중에 JPQL 문법에 따라 JPQL 문자열을 구문 분석하는지 여부에 따라 다름).
.
.
.
```

```
Criteria API의 주요 장점 중 하나는 구문상 잘못된 쿼리 구성을 금지한다는 것입니다. 목록 2는 인터페이스 를 사용하여 목록 1 의 JPQL 쿼리를 다시 작성합니다 CriteriaQuery.

EntityManager em = ...
CriteriaBuilder qb = em.getCriteriaBuilder();
CriteriaQuery<Person> c = qb.createQuery(Person.class);
Root<Person> p = c.from(Person.class);
Predicate condition = qb.gt(p.get(Person_.age), 20);
c.where(condition);
TypedQuery<Person> q = em.createQuery(c); 
List<Person> result = q.getResultList();
.
.
.
```
JPQL은 문자열로 지정되는 쿼리 언어이며, 엔티티 객체를 대상으로 쿼리를 실행하고 결과를 반환한다.<br />
JPQL을 사용할 때는 문법 오류가 컴파일 시간에는 감지되지 않고 실행 시에만 발견되기 때문에 Criteria API는 JPQL의 한계를 극복하기 위해 만들어졌다.<br />
또한 CriteriaQuery는 결과 타입을 제네릭으로 지정하여 타입 안정성을 보장한다고 한다.<br />
이게 공식 문서에서 강조하고 있는 typesafe querie!<br />
Criteria API를 사용하여 쿼리를 작성할 때는 JPQL과 달리 문자열이 아닌 Java 코드로 쿼리를 작성하므로 문법 오류가 발생할 가능성이 적다는 장점이 있고<br />
JPQL보다 더 복잡한 쿼리를 작성할 수 있으며, 타입 안정성을 제공하여 안전하고 유연한 쿼리 작성이 가능하다고 한다.<br />
핵심은 런타임에서만 발견되는 JPQL의 한계를 극복하기 위해 Java코드로 쿼리를 작성하게 만들었고. 이 덕분에 타입 안정성이 부여된다는 것.<br />
<hr />
그렇기 때문에 구현체를 생성할 때 스프링 데이터가 이를 분석하고 쿼리를 만들 수 있는 것이었다.<br />
쿼리를 생성하기 위해서는 사용자가 이름을 정해주어야 하는데. 이 이름을 정하는 규칙(데이터 메서드 시그니처)은 다음과 같다. <br />
<br />
동사, 생략 가능한 처리 대상, By 단어, 서술어.<br />
findByDeliveryZip()의 경우에는 동사가 find, 서술어가 DeliveryZip이며, 처리 대상은 지정되지 않았지만 묵시적으로 Order가 된다.<br />
<br />
더 복잡한 예를 들어보자면 다음과 같다.
readOrdersByDeliveryZipAndPlacedAtBetween()<br />
read는 crud의 r이며 get과 find도 허용된다. count도 허용한다.(개체 수 정수 반환)<br />
By는 일치 여부의 확인에 사용될 속성의 시작을 나타낸다.<br />
DeliveryZip은 .deliveryZip 또는 .delivery.Zip 속성 값의 여부를 확인하도록 한다.<br />
And는 진짜 and<br />
PlacedAt은 .placeAt or placed.at 값의 일치 여부를 확인.<br />
Between은 지정된 값의 범위에 해당 값이 포함되어야 한다.<br />
<br />
또한 묵시적으로 수행되는 Equals와 Between 연상에 추가하여 스프링 데이터 메서드 시그니처에는 다음 연산자 중 어느 것도 포함될 수 있다.<br />
<ul>
  <li>IsAfter, After, IsGreaterThan, GreaterThan</li>
  <li>IsGreaterThanEqual, GreaterThanEqual</li>
  <li>IsBefore, Before, IsLessThan, LessThan</li>
  <li>IsLessThanEqual, LessThanEqual</li>
  <li>IsBetween, Between</li>
  <li>IsNull, Null</li>
  <li>IsNotNull, NotNull</li>
  <li>IsIn, In</li>
  <li>IsNotIn, NotIn</li>
  <li>IsStartingWith, StartingWith, StartsWith</li>
  <li>IsEndingWith, EningWith, EndsWith</li>
  <li>IsContaining, Containing, Contains</li>
  <li>IsLike, Like</li>
  <li>IsNotLike, NotLike</li>
  <li>IsTrue, True</li>
  <li>IsFalse, False</li>
  <li>Is, Equals</li>
  <li>IsNot, Not</li>
  <li>IgnoringCase, IgnoresCase</li>
</ul>
물론. 모든 String 비교에서 대소문자를 무시하기 위해 IgnoringCase와 IgnoresCase대신<br />
AllIgnoringCase또는 AllIgnoresCase를 메서드 이름으로 사용할 수 있다.<br />
<br />
지정된 열의 값을 기준으로 결과를 정렬하기 위해 메서드 이름의 끝에 OrderBy를 추가할 수도 있다.<br />
<br />
그러나 더 복잡한 쿼리의 경우에는 메서드 이름만으로는 감당하기 어렵기 때문에 @Query 어노테이션을 지정해서 쿼리를 커스터마이즈 할 수도 있다.<br />
<br />
정말 책을 사길 잘 했다고 느끼는 챕터였다. 원시적 DB연결에서 JdbcTemplate로 작업의 효율을 올려주었고.<br />
데이터 추가를 쉽게 실행하게 해 주는 SimpleJdbcInsert로 더 높은 효율을 추구하게 되었고<br />
스프링 데이터 JPA를 통해 레포 인터페이스를 작성하듯 JPA 퍼시스턴스를 쉽게 할 수 있게 되었다.<br />
<hr />
스프링 시큐리티 챕터에 들어왔다. 그러나 책과 다른 버전을 사용하였기 때문에 SecurityConfig을 정의하는 데에 어려움이 있었다.<br />
그 이유는 버전이 올라감에 따라 너무 많은 것들이 deprecated 되어 있었기 때문이다.<br />
기존의 Sprint Security는 WebSecurityConfigurerAdapter를 사용했었는데.<br />
<br />
이는 클래스에서 메서드를 오버라이드하여 보안을 구성하는 방법이었다.<br />
이 방식은 유지 관리가 어렵고, 복잡성이 증가하며, 컴포넌트 간의 결합도가 높아지는 등의 문제가 있었다고 하는데..<br />
사실 나는 모든 프로젝트를 비교적 최신 버전으로 구성하였기 때문에 구식 방법이 얼마나 복잡했고 얼만큼 결합도가 높았는지는 모른다.<br />
<br />
확실한 것은 기존의 WebSecurityConfigurerAdapter를 상속받아 한곳에 다 때려박아야 했던 코드들을 컴포넌트 방식으로 분할하였고.<br />
람다를 사용하여 타입의 안정성을 가져갔다는 것.<br />

## 24-02-19
엄청 재미있는 책이다. 액자식 구성으로.<br />
기존에는 이렇게 사용했어ㅡtry catch finallyㅡ<br />
너무 불편했지? 그래서 준비했어 ㅡJdbcTemplateㅡ 맛있지?<br />
근데 좀 텁텁하지? 그래서 준비했어 ㅡSimpleJdbcInsertㅡ 맛있지?<br />
근데 진짜는 지금부터야 ㅡJPAㅡ<br />
예전에 배울 때는 이런 점진적 발전이 아니라 try catch finally에서 바로 myBatis로 넘어와서 중간 단계를 모르니<br />
그냥 역사 수업이라고 생각했는데. 이렇게 점진적으로 발전하는 기술들을 모두 책을 통해서 써 보니 재미있다.<br />
이제부터 JPA 시작. 써 보긴 했지만 너무 기초적인 수준의 사용만 하였었는데.<br />
이제 활용과 심화로 들어가보자. <br />

## 24-02-18
이제 db를 연동하여 작업하는 챕터에 들어왔다. 책에서는 h2를 사용했는데 크게 상이한 점은 없었다.<br />
이전에는 CommandLineRunner로 더미 데이터들을 만들어 주었는데 스키마와 데이터.sql로 처리하는 것은 인상깊었다.<br />
버전이 달라 나의 경우에는 sql.init.mode를 세팅하여야 해당 sql을 인식했다.<br />
차이는 잘 모르겠다. 아마도 협업 과정에서 미리 작성된 문서를 사용할 경우에는 스키마와 데이터를 사용하는 것이 아닌가 싶다.<br />
그래도 CommandLineRunner 방식이 더 자바스러워서 좋은것 같다.<br />
또한 try catch finally 방식의 db연결은 너무 번거로운 과정이라며 책에서 이야기 하는데..<br />
그 이후에 세련된 방식이라고 JdbcTemplate 사용 예를 소개하는 부분이 크게 와닿았다..<br />
try catch finally의 방식도 너무 길었어서 try catch resources로 바꾸어 사용했었는데<br />
JdbcTemplate을 사용하니 코드가 많이 간소화 되어 좋았다.<br />

## 24-02-17
유효성 검증에 대한 내용이었다.<br />
Thymeleaf의 경우에는 스프링에서 valid 여부를 바로 전달할 수 있었는데.<br />
나의 경우에는 독립되어 작동하기 때문에 앞과 뒤 모두 유효성 검증이 들어갔다.<br />
JSP나 타임리프는 백과 프론트의 경계가 모호했는데.<br />
분리하여 작성하니 앞과 뒤에서 해야 할 일을 정하는 단계가 중요할 것 같았다.<br />
이전 프로젝트에서는 @nonNull만 사용하고 서비스에서 유효성 검증을 처리했었는데.<br /> 
Hibernate를 사용하니 다양한 유효성 검사가 가능해서 좋았다. 놀랐던 점은 신용카드 번호의 룬 검사까지 해준다는 점.<br />

## 24-02-15
이제 완벽하게 컨버팅의 갈피가 잡히기 시작했다. 이전에는 사용하지 않았던 slf4j를 사용하여 데이터 값을 로깅하는데. <br />
사실 아직까지는 어떤 쓰임이 있는지 잘 모르겠다. 그냥 단순히 하나씩 찍어서 값을 확인하는 번거로움을 없애주는 용도?<br />
<br />
react보다 vue가 훨씬 접근성이 좋고 배우기 쉽다고 들었는데.<br />
막상 뷰를 써보니까 리액트에 익숙했어서 그런지<br />
아니면 내가 정보도 많이 없는 컴포지션 api 방식으로 사용하고 있어서 그런지 잘 모르겠다.<br />
그래도 템플릿과 스크립트 스타일을 나눠서 가독성이 좋다는 느낌은 받았지만<br />
script setup방식이 아닌 구식의 템플릿은 오히려 더 지저분하게 느껴졌다.<br />
그래서 정보도 없는 컴포지션 api로 시작했는데 자료들이 많이 없어 아쉽다.<br />
그래도 좋은 점은 공식 문서에서는 컴포지션 api의 가이드를 따로 마련해 두었다는 점.<br />
<br />
이미 알고 있는 내용들이라 오늘 느낀 점은 없었다.<br />
스프링 시큐리티까지 아직 2챕터나 남았는데 부디 복잡한 컨버팅 없이 순탄히 진행되길..<br />
<br />

## 24-02-14
타임리프 구문들을 컨버팅 하면서 정말 난해함을 느꼈다.<br />
책에서는 모델 객체를 이용하는 구문들을 나의 경우에는 뷰와 스프링으로 나누어 구현했기 때문에<br />
코딩 방식이 완전히 달라졌고. 원문에서는 몇 줄로 끝났을 코드가<br />
Repo, Service, Dto로 나뉘며 엄청 양이 많아졌다.<br />
어렵지는 않지만 책을 잘못 산 것이 아닌가 하는 느낌..<br />
그래도 아무 생각 없이. 뇌 비우고 코드만 따라치는 일이 없어서 더 좋은 것 같다.<br />

<br />

물론 위 과정들도 일부 난해한 점이 있었지만<br />
vee-validate, vuelidate 이것들이 더 훨씬 엄청 난해하다..<br />
하루 종일 프론트만 만졌다..<br />
그것도 프레임워크가 아니라 라이브러리만 만졌다.<br />
<br />


책 내용만 따라간다면 한시간이면 다 끝났을 내용인것 같은데<br />
컨버팅 때문에 진도가 늦다.<br />


## 24-02-12
이전 프로젝트에서 restful api는 구현되지 않았음.. <br />
response객체가 컨트롤러에는 없어 상태 코드와 헤더 커스터마이즈를 어떻게 해야하나 싶었는데 <br />
responseentity 객체를 만들어 사용하는 것이 정석적인 방법인것 같다. <br />
서비스에서 데이터 리턴을 트라이하고 응답에 문제가 있을 시 0이나 null을 리턴하는 캐치가 얼마나 일자무식한 방법이었는지 깨달았다. <br /> 
또한 responseentity를 사용할 때는 결과값, 상태코드, 헤더값을 모두 프론트로 전달할 수 있다는 장점이 있다. <br />
모든 작업을 혼자 하니까 의문은 들었어도 문제성을 느끼지 않았겠지만.<br />
이런 지식도 없이 협업을 했었다면 분명히 좋지 못한 일이 일어났을 것이다.. <br />
















