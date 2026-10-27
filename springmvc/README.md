# 스프링-MVC-복습

>26.10.27

## 스프링 MVC - 기본 기능
### 요청 매핑

[requestMapping](src/main/java/study/springmvc/basic/requestmapping) 기본적인 매핑들

`@RestController` RestAPI용 어노테이션. return에 넣은 값 문자 그대로 반환

`@RequestMapping` URL 호출이 오면 메서드 실행. 배열로 제공 -> 다중 설정 가능, 메서드 호출 지정안하면 GET, POST 둘다 가능

`@PathVariable`은 [MappingController](src/main/java/study/springmvc/basic/requestmapping/MappingController.java) line 48 `{userId}`를 꺼내서 line 49에서 사용

`@PathVariable` 변수 이름과 파라미터 이름이 같으면 `@PathVariable`안에 괄호 생략 가능 ex) `@PathVariable String userId`

그 외 `Content-Type` 헤더 기반, `Accept` 헤더 기반 Media Type [MappingController](src/main/java/study/springmvc/basic/requestmapping/MappingController.java) line 92 ~

매핑 예시 [MappingClassController](src/main/java/study/springmvc/basic/requestmapping/MappingClassController.java) 참고

<br>

### HTTP 요청 - 기본, 헤더 조회

[RequestHeaderController](src/main/java/study/springmvc/basic/request/RequestHeaderController.java) 참고

<br>

### HTTP 요청 파라미터 - 쿼리 파라미터, HTML Form

[RequestParamController](src/main/java/study/springmvc/basic/request/RequestParamController.java), [hello-form.html](src/main/resources/static/basic/hello-form.html) 코드

line 17 ~ 25 `@RequestMapping()`  HTML Form 전송 과거 버전

<br>

### HTTP 요청 파라미터 - @RequestParam

[RequestParamController](src/main/java/study/springmvc/basic/request/RequestParamController.java) 코드

line 28 ~ 35 `@RequestMapping()`, `@ResponeBody` 활용 하여 RestAPI 형태로 문자를 반환.
`@RequestParam`을 통해 파라미터를 받아옴

line 38 ~ 44 `@ReqeustParam` 이름 생략 가능

line 47 ~ 53 `@ReqeustParam` 어노테이션까지도 생략 가능. 단 파라미터와 이름이 같아야 함

line 56 ~ 74 `@ReqeustParam(required = {true|false})` true로 설정시 파라미터가 필수로 있어야 됨

line 77 ~ 83 `@ReqeustParam(defaultValue = "")` 기본 값 설정 가능

line 86 ~ 91 `@RequestParam`을 `Map<>`으로 받을 수 있음

<br>

### HTTP 요청 파라미터 - @ModelAttribute

[RequestParamController](src/main/java/study/springmvc/basic/request/RequestParamController.java), [HelloData](src/main/java/study/springmvc/basic/HelloData.java) 코드

line 94 ~ 101 `@ModelAttribute`를 통해 객체에 파라미터 값을 바로 넣을 수 있음. **단 파라미터와 객체 변수 이름이 같아야 함**

객체 생성부터 객체의 프로퍼티를 찾아 `setter` 호출해서 값 바인딩

line 103 ~ 111 `@ModelAttribute`도 생략 가능 -> @RequestParam로 생략가능해서 혼란 조심해야 함