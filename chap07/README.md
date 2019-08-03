# Chap07

- JPQL

## JPQL

- JPA는 앞서 본 형태의 쿼리 지원 뿐만 아니라 복잡한 쿼리에 대한 기능을 제공한다.

- 그 중 하나는 JPQL은 Java Persistence Query Language의 약자로 객체를 대상으로 검색을 할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색하게 해주는 기능이다.

- 이 기능이 필요한 이유는, 모든 DB 데이터를 객체로 변환해서 검색하는 것은 불가능하다. 때문에, 애플리케이션이 필요한 데이터만 DB에서 불러오려면 결국 검색 조건이 포함된 SQL이 필요하게 된다.

- 정리하자면 JPA는 SQL을 추상화한 JPQL이라는 객체 지향 쿼리 언어를 제공하고 (특정 데이터베이스에 의존x), 이는 엔티티 객체를 대상으로 쿼리를 짤 수 있다.

- 일반 SQL은 데이터베이스 테이블을 대상으로 쿼리를 짜는 것이다.

## 문법

- 엔티티와 속성은 대소문자 구분(Member, username)

- JPQL 키워드는 대소문자 구분 안함

- 엔티티 이름을 사용, 테이블 이름이 아님(Member)

- 별칭은 필수

- query.getResultList() : 결과가 하나 이상, 리스트 반환

- query.getSingleResult() : 결과가 정확히 하나, 단일 객체 반환 (하나가 아니라면 예외발생)

- 파라미터 바인딩 (이름기준) 

~~~
SELECT m FROM Member m where m.username=:username
query.setParameter("username", usernameParam);
~~~

- 파라미터 바인딩 (위치기준)

~~~
SELECT m FROM Member m where m.username=?1
query.setParameter(1, usernameParam);
~~~

- 프로젝션
  - 엔티티 프로젝션
  ~~~ 
  SELECT m.team FROM Member m
  ~~~
  
  - 단순 값 프로젝션
  
  ~~~
  SELECT username, age FROM Member m
  ~~~
  
   - new 명령어
  ~~~
  SELECT new com.example.jpa.user.UserDTO(m, username, m.age) FROM Member m
  ~~~
  
- 페이징 API

   - setFirstResult(int startPosition) : 조회 시작 위치 
   
   - setMaxResults(int maxResult) : 조회할 데이터 수
   
- 페치 조인

   - 엔티티 객체 그래프를 한번에 조회하는 방법
   
   - 별칭 사용 불가
   
   - Lazy로 설정되어 있을 때 fetch 조인을 사용할 시 즉시 로딩으로 작동하게 됨. (N+1문)
   
   - JPQL
        ~~~
        SELECT m FROM Member m JOIN FETCH m.team
        ~~~
        
   - SQL 
        ~~~
        SELECT M.*, T.* FROM MEMBER T INNER JOIN TEAM T ON M.TEAM_ID=T.ID
        ~~~
        
- @NamedQuery

   - 엔티티에 미리 쿼리를 지정하고 사용할 수 있음.
   
   - 기존의 JPQL 방식은 문자열이기 때문에 오류를 잡아낼 수 없다는 단점이 존재.
   
   - 이 방식은 애플리케이션 로딩시점에 오류를 잡아 낼 수 있음.
   
   ~~~
   @Entity
   @NamedQuery(
        name = "Member.findByUsername",
        query = "select m from Member m where m.username = :username")
   public class Member {
        ...
   }
   ~~~
           