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

<br>

>26.10.28 

### HTTP 요청 파라미터 - 단순 텍스트

[RequestBodyStringController](src/main/java/study/springmvc/basic/request/RequestBodyStringController.java) 코드

line 30 ~ 38 v1 서블릿 방식:  텍스트

line 40 ~ 47 v2 스프링 MVC 방식: InputStream(Reader), OutputStream(Writer)을 파라미터로 받아 사용.

line 49 ~ 57 v3 v2 개선 버전: `HttpEntity`를 통해 Input, Output 모두 사용

line 59 ~ v4 가장 일반적인 방식: `@RequestBody`(요청) 사용하면 메시지 바디 값을 바로 받을 수 있음 `@ResponseBody`(응답)

정리
- 요청 파라미터-`@RequestParam`, `@ModelAttribute`
- 메시지바디-`@RequestBody`

<br>

### HTTP 요청 파라미터 - JSON

[RequestBodyJsonController](src/main/java/study/springmvc/basic/request/RequestBodyJsonController.java) 코드

line 30 ~ 41 v1 서블릿 방식: `InputStream()` 값을 받아온 후 `objectMapper`를 통해 JSON 변환 -> 굉장히 복잡

line 43 ~ 52 v2 스프링 MVC 방식: `@RequestBody` 메시지 바디를 받아온 후 `objectMapper`를 통해 JSON 변환

line 55 ~ 63 v3: `@RequestBody`로 바로 객체를 받아와 사용

line 65 ~ 72 v4: `HttpEntity<>` 알아서 객체 타입으로 변환

line 74 ~ 80 v5: 반환 타입을 객체로 설정하여 `@RequestBody`에서 받아온 데이터를 바로 리턴

***`@RequestBody`는 `@RequestParam`, `@ModelAttribute`과 다르게 생략 불가능**
- String, int, Integer -> `@RequestParam`
- 그 외 나머지 -> `@Modelattribute`

<br>

### HTTP 응답 - 정적 리소스, 뷰 템플릿

[ResponseViewController](src/main/java/study/springmvc/basic/response/ResponseViewController.java), [hello.html](src/main/resources/templates/response/hello.html) 코드

line 11 ~ 17 v1: `ModelAndView()`로 rescources/templates/response 경로에 있는 html(타임리프 사용) data(이름)에 hello!!(값)을 넣음

line 19 ~ 25 v2 일반적인 방식: `Model`을 사용. `model.addAttribute()`를 통해 바로 값을 넣음

Thymeleaf 스프링 부트 기본 설정 - 변경시에만 `application.properties` 수정
- spring.thymeleaf.prefix=classpath:/templates/
- spring.thymeleaf.suffix=.html

<br>

>26.10.29

### HTTP 응답 - HTTP API, 메시지 바디

[ResponseBodyController](src/main/java/study/springmvc/basic/response/ResponseBodyController.java) 코드

line 21 ~ 24 v1 서블릿 방식

line 26 ~ 29 v2 가장 단순한 방식

line 32 ~ 36 v3 v2보다 더 단순한 방식: `@ResponseBody`사용

line 38 ~ 46 v4 JSON 전송 방식 v1: 반환 값을 객체로 지정 `ResponseEntity<>`

line 49 ~ 61 v5 JSON 전송 방식 v2: `ResponseEntity<>`없이 객체 형태 그대로 반환 값 지정 및 `@ResponseStatus()`로 상태 코드 지정

<br>

### HTTP 메시지 컨버터

HTTP 메시지 컨버터는 요청, 응답 둘 다 사용
- `canRead()`, `canWrite()`: 메시지 컨버터가 해당 클래스, 미디어 타입을 지원하는지 체크
- `read()`, `write()`: 메시지 컨버터를 통해 메시지를 읽고 쓰는 기능

스프링 부트 기본 메시지 컨버터
- ByteArrayHttpMessageConverter: Byte[] 데이터 처리 - 클래스타입 `Byte[]`, 미디어타입 `*/*`
- StringHttpMessageConverter: String 문자로 데이터 처리 - 클래스타입 `String`, 미디어타입 `*/*`
- MappingJackson2HttpMessageConverter: application/json 처리 - 클래스타입 객체 또는 `HashMap`, 미디어타입 `application/json`

<br>

### 요청 매핑 헨들러 어댑터 구조

`@RequestMapping`을 처리하는 핸들러 어댑터가 `RequestMappingHandlerAdapter`(요청 매핑 핸들러 어댑터)

핸들러 어댑터가 컨트롤러의 매우 다양한 파라미터를 호출, 사용할 수 있는데 이유 -> **`ArgumentResolver`**

**`ArgumentResolver`(요청): 컨트롤러(핸들러)가 필요로 하는 다양한 파라미터의 값(객체)을 생성**

**`ReturnValueHandler`(응답): : 컨트롤러의 반환 값을 변환**

앞서 설명한 HTTP 메시지 컨버터의 위치 -> `ArgumentResolver`, `ReturnValueHandler` 두 곳 모두에 위치함
- HTTP 메시지 컨버터를 사용하는 `@RequestBody` 컨트롤러가 필요로 하는 파라미터 값에 사용
- `@ResponseBody`도 컨트롤러의 반환 값에 사용

요청: `@RequestBody`와 `HttpEntity`를 처리하는 `ArgumentResolver`가 있음. `ArgumentResolver`들의 HTTP 메시지 컨버터를 사용해서 필요한 객체 생성

응답: `@RequestBody`와 `HttpEntity`를 처리하는 `ReturnValueHandler`가 있음. `ReturnValueHandler`들이 HTTP 메시지 컨버터를 호출해서 응답 결과를 생성

스프링 MVC는
- `@RequestBody`와 `@RequestBody`가 있으면 -> `RequestResponseBodyMethodProcessor`(ArgumentResolver) 사용
- `HttpEntity`가 있으면 -> `HttpEntityMethodProcessor`(ArgumentResolver) 사용

스프링 리졸버 관련 기능을 확장 -> `WebMvcConfigurer`를 확장하면 됨