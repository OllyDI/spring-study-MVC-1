# 스프링-MVC-복습

>26.10.21

스프링 부트 서블릿 환경 구성

```@ServletComponentScan``` 하위 패키지를 전부 찾아 서블릿 자동 등록

`/servlet/basic/HelloServlet.java` 단축키 `Ctrl + O` 자물쇠로 잠긴 `service` protected 메서드 생성
-> 서블릿 호출 시 `service` 메서드 호출

<br>

`application.properties/logging.level.org.apache.coyote.http11=trace` 로킹 레벨 변경 가능

TRACE > DEBUG > INFO > WARN > ERROR > FATAL (또는 OFF) 순서

<br>

`HttpServletRequest` 기본 사용법 (옛날 방식 헤더를 전부 직접 생성) -> `servlet/basic/request/RequestHeaderServlet.java` 참고

<br>

HTTP 요청 데이터 - GET 쿼리 파라미터
