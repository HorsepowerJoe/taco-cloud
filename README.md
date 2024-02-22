<h2>실무를 통해 백엔드의 더 깊은 내용들을 접하고 싶었으나 취업이 되지 않은 관계로.. <br />
  <br />
  Spring in Action으로 실무 섀도복싱 + 공식문서 Vue 3.0 시작합니다. <br />
  <br />
</h2>
 <br />

<h3>1. Spring in Action(taco-cloud)</h3>
<p>2. Node.JS 교과서</p>
<p>3. 서비스 운영이 쉬워지는 AWS 인프라 구축 가이드</p>
<br />
<br />


## 24-02-12
이전 프로젝트에서 restful api는 구현되지 않았음.. <br />
response객체가 컨트롤러에는 없어 상태 코드와 헤더 커스터마이즈를 어떻게 해야하나 싶었는데 <br />
responseentity 객체를 만들어 사용하는 것이 정석적인 방법인것 같다. <br />
서비스에서 데이터 리턴을 트라이하고 응답에 문제가 있을 시 0이나 null을 리턴하는 캐치가 얼마나 일자무식한 방법이었는지 깨달았다. <br /> 
또한 responseentity를 사용할 때는 결과값, 상태코드, 헤더값을 모두 프론트로 전달할 수 있다는 장점이 있다. <br />
모든 작업을 혼자 하니까 의문은 들었어도 문제성을 느끼지 않았겠지만.<br />
이런 지식도 없이 협업을 했었다면 분명히 좋지 못한 일이 일어났을 것이다.. <br />


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

## 24-02-17
유효성 검증에 대한 내용이었다.<br />
Thymeleaf의 경우에는 스프링에서 valid 여부를 바로 전달할 수 있었는데.<br />
나의 경우에는 독립되어 작동하기 때문에 앞과 뒤 모두 유효성 검증이 들어갔다.<br />
JSP나 타임리프는 백과 프론트의 경계가 모호했는데.<br />
분리하여 작성하니 앞과 뒤에서 해야 할 일을 정하는 단계가 중요할 것 같았다.<br />
이전 프로젝트에서는 @nonNull만 사용하고 서비스에서 유효성 검증을 처리했었는데.<br /> 
Hibernate를 사용하니 다양한 유효성 검사가 가능해서 좋았다. 놀랐던 점은 신용카드 번호의 룬 검사까지 해준다는 점.<br />


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
이는 클래스에서 메서드를 오버라이드하여 보안을 구성하는 방법이었다.<br />
이 방식은 유지 관리가 어렵고, 복잡성이 증가하며, 컴포넌트 간의 결합도가 높아지는 등의 문제가 있었다고 하는데..<br />
사실 나는 모든 프로젝트를 비교적 최신 버전으로 구성하였기 때문에 구식 방법이 얼마나 복잡했고 얼만큼 결합도가 높았는지는 모른다.<br />
<br />
확실한 것은 기존의 WebSecurityConfigurerAdapter를 상속받아 한곳에 다 때려박아야 했던 코드들을 컴포넌트 방식으로 분할하였고.<br />
람다를 사용하여 타입의 안정성을 가져갔다는 것.<br />




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

하지만 바뀐 Spring Security 6에서는 람다 DSL을 사용한 SecurityFilterChain을 구현하도록 권장하고 있다.<br />
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
3. 기존 오타에 취약하고 난해하였던 .access(hasROLE('ROLE_USER')) 방식이 표현이 명확해 졌다는 것.
4. 컴포넌트 방식으로 구현을 하기 때문에 자유롭게 필터 순서를 구성할 수 있다는 것.

<hr />

