# Chap02
- JPA 기초와 매핑

기본적으로 JPA가 동작하는 방식을 공부한다.

1. Persistence의 설정 정보 조회 

2. EntityManagerFactory 생성 

3. EntityManager 생성


## 객체 매핑
 - @Entity : JPA가 관리할 객체를 엔티티라 부름.
 - @Id : DB PK와 매핑 할 필드.
 
 ## 데이터베이스의 방언
 - JPA는 특정 데이터베이스에 종속적이지 않은 기술.
 
 - 각각의 데이터베이스가 제공하는 SQL 문법과 함수는 조금씩 다름.
 
 - 방언 : SQL 표준을 지키지 않거나 특정 데이터베이스만의 고유한 기능.
 
 ## 어플리케이션 개발 
 
- 엔티티 매니저 팩토리 설정 (애플리케이션 전체에서 공유)

- 엔티티 매니저 설정 (쓰레드간 공유하면 안되고, 사용 후 버려야함)

- 트랜잭션 (JPA의 모든 데이터 변경은 트랜잭션 안에서 실행되어야 함)

- CRUD


# 실습

## Member.java
~~~
@Entity
public class Member {

    @Id
    private int id;

    private String name;

    ... 
}
~~~

## application.properties
~~~
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=create
~~~

## JpaApplication.java
~~~
...

try {
    Member member = new Member();
    member.setId(100L);
    member.setName("안녕하세요");

    em.persist(member);

    tx.commit();
} catch (Exception e) {
    tx.rollback();
} finally {
    em.close();
}

...
~~~