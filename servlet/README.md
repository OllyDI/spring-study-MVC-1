# 스프링-MVC-복습

>26.10.21

## 서블릿
### 스프링 부트 서블릿 환경 구성

```@ServletComponentScan``` 하위 패키지를 전부 찾아 서블릿 자동 등록

[HelloServlet.java](src/main/java/study/servlet/basic/HelloServlet.java) 단축키 `Ctrl + O` 자물쇠로 잠긴 `service` protected 메서드 생성
-> 서블릿 호출 시 `service` 메서드 호출

[application.properties](src/main/resources/application.properties) `logging.level.org.apache.coyote.http11=trace` 로킹 레벨 변경 가능

TRACE > DEBUG > INFO > WARN > ERROR > FATAL (또는 OFF) 순서

<br>

### `HttpServletRequest` 기본 사용법 (옛날 방식 헤더를 전부 직접 생성)
[RequestHeaderServlet.java](src/main/java/study/servlet/basic/request/RequestHeaderServlet.java) 참고

<br>

### HTTP 요청 데이터 - 개요
- GET - 쿼리 파라미터
  - 메시지 바디 없이 URL 쿼리 파라미터에 데이터를 포함해서 전달
  - ex) 검색, 필터, 페이징 등에 많이 사용
- POST - HTML Form
  - 메시지 바디에 쿼리 파리미터 형식으로 전달 username=hello&age=20
  - 예) 회원 가입, 상품 주문, HTML Form 사용
- HTTP message body - 데이터를 직접 담아서 요청
  - HTTP API에서 주로 사용, JSON, XML, TEXT
  - 데이터 형식은 주로 JSON 사용
  - POST, PUT, PATCH

<br>

### HTTP 요청 데이터 - GET 쿼리 파라미터
[RequestParamServlet.java](src/main/java/study/servlet/basic/request/RequestParamServlet.java)

전체 파라미터 조회 line 26`req.getParameterNames()`

단일 파라미터 조회 line 32 `req.getParameter()`: 값이 중복일 때는 첫 번째 값을 반환, GET POST 형식 둘다 지원

중복 파라미터 조회 line 40 `req.getParameterValues()`: 하나의 파라미터에 여러 값이 들어가 있을 때 사용

<br>

### HTTP 요청 데이터 POST - API 메시지 바디 - 단순 텍스트
메시지 바디에 데이터를 포함해서 보냄 -> content-type을 반드시 지정해야 함

[RequestBodyStringServlet.java](src/main/java/study/servlet/basic/request/RequestBodyStringServlet.java)
데이터 읽기 line 19 `req.getInputStream()`

<br>

### HTTP 요청 데이터 POST - API 메시지 바디 - JSON

JSON 파싱을 위한 객체 생성 [HelloData.java](src/main/java/study/servlet/basic/HelloData.java)

[RequestBodyJsonServlet.java](src/main/java/study/servlet/basic/request/RequestBodyJsonServlet.java) line 20 `ObjectMapper()`객체 생성 후  line 28 `objectMapper.readValue()`를 사용하면 `HelloData`로 파싱

<br>

### HttpServletResponse - 기본 사용법
HttpServletResponse 역할
 - HTTP 응답코드 지정
 - 헤더 생성
 - 바디 생성
 - 편의 기능: Content-Type, 쿠키, Redirect

[ResponseHeaderServlet.java](src/main/java/study/servlet/basic/response/ResponseHeaderServlet.java) 

응답 코드 생성 line 20 `res.setStatus()` `HttpServletResponse` 상수로 번호 지정

헤더 생성 line 23 ~ `res.setHeader()`

편의 메서드 line 29 ~ `content(res)`, `cookie(res)`, `redirect(res)`

<br>

### HTTP 응답 데이터 - 단순 텍스트, HTML
 - 단순 텍스트 응답 - `writer.println("ok");`
 - HTML 응답 - [ResponseHtmlServlet.java](src/main/java/study/servlet/basic/response/ResponseHtmlServlet.java) 단순히 HTML을 보내면 됨
 - HTML API 응답 - [ResponseJsonServlet.java](src/main/java/study/servlet/basic/response/ResponseJsonServlet.java) `objectMapper.writeValueAsString()` 객체를 JSON으로 변환

<br>

>26.10.22

## 서블릿, JSP, MVC 패턴
기술이 변화한 순서대로 해보면서 단점 개선, 어떻게 바뀌는지 알아봄

### 서블릿
서블릿 회원관리 웹 애플리케이션 [memberEntity](src/main/java/study/servlet/domain/member), [HTML Form](src/main/java/study/servlet/web/servlet) 생성

<br>

### JSP - 기술력면이나 편의성면에서 최신 기술에 많이 밀림. 거의 안씀

JSP 회원관리 웹 애플리케이션 [webapp](src/main/webapp/jsp) 생성

<br>

### MVC - 서블릿(컨트롤러), JSP(뷰) 사용 -> 한계가 있음

[View](src/main/webapp/WEB-INF), [ServletMVC](src/main/java/study/servlet/web/servletmvc) 컨트롤러에서 뷰 이동을 위한 `req.getRequestDispatcher()` 사용

한계
 - [ServletMVC](src/main/java/study/servlet/web/servletmvc) View로 이동하는 `dispatcher.forward(req, res)` 중복
 - [ServletMVC](src/main/java/study/servlet/web/servletmvc) ViewPath 중복
 - [ServletMVC](src/main/java/study/servlet/web/servletmvc) req, res가 사용되지 않을 때도 있음 (필요 없는 코드)
 - **기능이 복잡해질수록 공통으로 처리해야 하는 부분이 증가 -> 공통 처리가 어려움, 중복 증가**

`프론트 컨트롤러 패턴`을 도입해서 해결

<br>

>26.10.23

## MVC 프레임워크 만들기 (이후 스프링 MVC 사용) - MVC 도입 위해서 프론트 컨트롤러 필요

`FrontController` 스프링 MVC의 핵심 - 공통처리, 요청에 맞는 컨트롤러 호출 등
`DispatcherServlet`도 `FrontController`패턴으로 구현되어 있음

### 프론트 컨트롤러 V1 - V1부터 V5까지 단계적으로 도입

[frontControllerV1](src/main/java/study/servlet/web/frontcontroller/v1/FrontControllerServletV1.java) `req.getRequestURI()`를 통해 접속 주소를 가져와 매핑 후 맞는 객체 호출

<br>

### 프론트 컨트롤러 V2 - V1에서 View를 분리해서 중복 제거

[MyView.java](src/main/java/study/servlet/web/frontcontroller/MyView.java)를 통해 뷰를 분리 - 중복 제거

[frontControllerV2](src/main/java/study/servlet/web/frontcontroller/v2/FrontControllerServletV2.java) 결과

<br>

### 프론트 컨트롤러 V3 - 모델 도입 서블릿(req, res) 종속성 제거, 뷰 이름 중복 제거(/WEB-INF/views/...)

[ModelView.java](src/main/java/study/servlet/web/frontcontroller/ModelView.java) 모델 생성 - 뷰 이름, 모델

[frontControllerV3](src/main/java/study/servlet/web/frontcontroller/v3/FrontControllerServletV3.java) 순서
1. 경로 호출 후 컨트롤러 매핑
2. line 41 - 42 `createParamMap(req)`로 req의 파라미터를 전부 꺼낸 후 모델에 담음
3. line 44 - 45 컨트롤러에서 리턴한 `viewName` 논리 이름을 가져온 후 `ex) new-form` `viewResolver`호출 - 물리 경로 생성
4. line 47 모델을 view에 넘겨줌
5. [MyView.java](src/main/java/study/servlet/web/frontcontroller/MyView.java) line 23 모델에 담긴 데이터를 꺼내 `req.setAttribute()`(jsp형식 - 여기에 데이터를 담음)에 전부 담음
6. [MyView.java](src/main/java/study/servlet/web/frontcontroller/MyView.java) line24 `viewPath`에 맞는 jsp로 이동 

<br>

### 프론트 컨트롤러 V4 - 컨트롤러에서의 ModelView 반복 생성 제거

[controllerV4](src/main/java/study/servlet/web/frontcontroller/v4/controller) 컨트롤러가 ViewName만 반환

[FrontControllerServletV4](src/main/java/study/servlet/web/frontcontroller/v4/FrontControllerServletV4.java) 컨트롤러에 line 44 모델만 생성해서 넘겨주면 끝

<br>

### 유연한 컨트롤러 V5 - V3-V4 컨트롤러 둘다 사용가능 (어댑터 패턴)

1. [FrontControllerServletV5](src/main/java/study/servlet/web/frontcontroller/v5/FrontControllerServletV5.java) line 55 접속 주소가 잘못된 주소인지 확인
2. [adapter](src/main/java/study/servlet/web/frontcontroller/v5/adapter)의 supports를 통해 해당 컨트롤러로 호환이 되는지 확인 -> line 61 [FrontControllerServletV5](src/main/java/study/servlet/web/frontcontroller/v5/FrontControllerServletV5.java)
3. [FrontControllerServletV5](src/main/java/study/servlet/web/frontcontroller/v5/FrontControllerServletV5.java) line 63 ~ 해당 어댑터에서 반환된 모델로 뷰 랜더링

V4 컨트롤러 어댑터 [ControllerV4HandlerAdapter](src/main/java/study/servlet/web/frontcontroller/v5/adapter/ControllerV4HandlerAdapter.java) line 30 ~ 33 - 기존 V4는 `viewName`반환 여기서는 `ModelView`반환

<br>

> 26.10.26

## 스프링 MVC - 구조 이해

### 스프링 MVC 전체 구조
앞서 직접 만든 MVC와 스프링 MVC의 구조가 같음
- FrontController -> DispatcherServlet
- handlerMappingMap -> HandlerMapping
- MyHandlerAdapter -> HandlerAdapter
- ModelView -> ModelAndView
- viewResolver -> ViewResolver
- MyView -> View

동작 순서
1. 핸들러 조회: 핸들러 매핑을 통해 요청 URL에 매핑된 핸들러(컨트롤러) 조회
2. 핸들러 어댑터 조회: 핸들러를 실행할 수 있는 핸들러 어댑터 조회
3. 핸들러 어댑터 실행: 핸들러 어댑터 실행
4. 핸들러 실행: 핸들러 어댑터가 실제 핸들러 실행
5. ModelAndView 반환: 핸들러 어댑터는 핸들러가 반환하는 정보를 ModelAndView로 변환 후 반환
6. viewResolver 호출: 뷰 리졸버를 찾고 실행
   JSP의 경우: InternalResourceViewResolver 가 자동 등록, 사용
7. View 반환: 뷰 리졸버는 뷰의 논리 이름을 물리 이름으로 바꾸고, 렌더링 역할을 담당하는 뷰 객체 반환
   JSP의 경우 InternalResourceView(JstlView) 를 반환, 내부에 forward() 로직이 있음
8. 뷰 렌더링: 뷰를 통해서 뷰 렌더링

과거의 핸들러 매핑과 핸들러 어댑터 참고 [OldControllerAndHandler](src/main/java/study/servlet/web/springmvc/old)

<br>

### 스프링 MVC - 시작

[스프링 MVC V1](src/main/java/study/servlet/web/springmvc/v1)

@Controller: 컴포넌트 스캔 대상, 어노테이션 기반 컨트롤러로 인식(RequestMappingHandlerMapping)

@RequestMapping: 요청 정보 매핑, 해당 URL이 호출되면 이 메서드 호출


<br>

### 스프링 MVC - 컨트롤러 통합

[V1](src/main/java/study/servlet/web/springmvc/v1)에서 분리되어 있던 컨트롤러 통합

[스프링 MVC V2](src/main/java/study/servlet/web/springmvc/v2/SpringMemberControllerV2.java) 클래스 레벨에 `@RequestMapping("/springmvc/v2/members")`을 추가하여 중복 제거

<br>

### 스프링 MVC - 실용적인 방식

[스프링 MVC V3](src/main/java/study/servlet/web/springmvc/v3/SpringMemberControllerV3.java) 
1. `ModelAndView`로 뷰를 반환하지 않고 `String`형태로 뷰 논리 이름을 직접 반환
2. req, res를 사용하지 않고 `@RequestParam`을 통해 요청 파라미터를 직접 받음
3. `Model`을 파라미터로 넘겨 사용가능
4. `@GetMapping()`, `@PostMapping()` 어노테이션 사용으로 GET, POST 방식으로 매핑