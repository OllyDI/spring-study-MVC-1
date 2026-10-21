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

### HTTP 응답 데이터 - 단순 텍스트, HTML
 - 단순 텍스트 응답 - `writer.println("ok");`
 - HTML 응답 - [ResponseHtmlServlet.java](src/main/java/study/servlet/basic/response/ResponseHtmlServlet.java) 단순히 HTML을 보내면 됨
 - HTML API 응답 - [ResponseJsonServlet.java](src/main/java/study/servlet/basic/response/ResponseJsonServlet.java) `objectMapper.writeValueAsString()` 객체를 JSON으로 변환