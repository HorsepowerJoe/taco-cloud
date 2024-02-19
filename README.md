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
try catch finally의 방식도 너무 불필요하게 길었어서 try catch resources로 바꾸어 사용했었는데<br />
JdbcTemplate을 사용하니 코드가 많이 간소화 되어 좋았다.<br />



