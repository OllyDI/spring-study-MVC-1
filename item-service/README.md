## 스프링 MVC - 웹 페이지 만들기
>26.10.30

### 요구사항 분석
상품 도메인 모델
- 상품 ID
- 상품명
- 가격
- 수량

상품 관리 기능
- 상품 목록
- 상품 상세
- 상품 등록
- 상품 수정

<br>

### 상품 도메인 개발

[domain](src/main/java/hello/itemservice/domain/item) 코드

`@Data` 어노테이션은 예측 불가한 상황 발생할 수 있어 사용 주의

`itemRepository` line 38 update의 경우에는 Dto 객체를 생성해서 사용하는게 좋음

<br>

### 상품 서비스 HTML

[상품 서비스HTML](src/main/resources/static) HTML로만 구현 -> 이후에 타임리프로 변환

<br>

### 상품 목록 - 앞서 만든 HTML을 타임리프로 구현

타임리프 사용 기본 문법 
```
<html lang="en-ko" xmlns:th="http://www.thymeleaf.org">
```
[items.html](src/main/resources/templates/basic/items.html) 코드

line 15 `|...|` 리터럴 대체, `@{...}` URL 링크

line 30 `th:each` 반복 출력, `${...}` 변수 표현식

<br>

### 상품 상세

[item.html](src/main/resources/templates/basic/item.html) 코드

line 22 `th:value` 속성 변경



<br>

>26.10.31

### 상품 등록 폼

[addForm.html](src/main/resources/templates/basic/addForm.html), [ItemRepository](src/main/java/hello/itemservice/domain/item/ItemRepository.java) 코드

line 18 `th:action` 폼 액션 이벤트

line 37 ~ 94 상품 등록 접속 및 처리는 url은 같고 GET, POST만 다르게 구현해서 사용

line 93 `return "redirect:/basic/items/{itemId}"` 이후 현재 아이템 아이디를 보고 리다이렉션

<br>

### 상품 등록 처리
1. line 43 ~ 57 `@RequestParam` - 도메인, 파라미터 이름 변경시 문제 발생. 코드도 길어짐
2. line 59 ~ 69 `@ModelAttribute(...)` - 코드 간결, 파라미터가 변경되도 도메인만 변경하면 됨
3. line 72 ~ 77 `@ModelAttribute` 이름 생략 시 객체 이름 사용(Item -> item). `@ModelAttribute` 모델 추가 기능도 함 -> `model.addAttribute()`제거
4. line 79 ~ 85 `@ModelAttribute` 자체도 생략 가능