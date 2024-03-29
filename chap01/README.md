 # Chap01

 JPA의 기초
 
 ## JPA(Java Persistence API)
 
 기업들이 사용하는 데이터베이스는 주로 관계형 데이터베이스(RDBMS)로 객체를 관계형 DB에 관리한다.
 이러한 데이버베이스에 접근하여 사용하기 위해 SQL 중심적인 개발이 이루어졌는데, 이러한 단점을 보완한 
 해외에서는 많이 쓰이는 JPA가 국내 대기업이 사용하면서 널리 사용되기 시작되었다.

## 기본 방식의 단점
객체 -> SQL 변환 -> SQL -> RDB

- 무한 반복되는 쿼리, 지루한 코드 : SQL에 의존적인 개발이 이루어진다.

- 엔티티 신뢰 문제 : 연관 관계에 대한 신뢰성을 보장할 수 없다. 즉, 계층형 아키텍쳐에서 논리적 계층 분할이 어렵다.

- 유지보수 : 변경사항이 있는 컬럼의 경우 개발자의 실수를 유발한다.

## 패러다임의 불일치
- 관계형 DB : 데이터를 어떻게 정교하게 저장하는 관점에 포커싱됨.

- 객체 : 추상화, 관리와 같은 관점에 포커싱되어 나온 사상.

## 객체와 관계형 DB의 차이
- 상속 : 상속이 있는 DB도 있지만, 궁극적으로 객체 상속 스타일의 구조가 아니다. 만약 상속되어있는 객체 관계에서 검색을 할 경우 각각의 객체마다 일일이 매핑해줘야한다. 떄문에 모든 데이터를 담을 수 있는 객체를 만들어 때려박는 식의 구조로 설계하게 된다.

- 연관관계 : 객체는 참조를 이용하지만, 테이블은 외래키를 사용하게 된다. 이는 비슷하게 보이지만, 확연히 다르다. 객체에서는 A라는 객체가 B를 참조한다면 A->B는 가능하지만 B->A는 불가능하다. 하지만 RDB는 참조키를 통해 양방향으로 참조할 수 있다. 즉, 객체의 연관관계는 방향성이 존재하고, RDB는 방향성이 존재하지 않는다는 것이다.


## JPA? ORM?
- JPA : 자바 진영의 ORM 기술 표준이다. 내부적으로는 JDBC API를 통해 DB와 연결되고 애플리케이션과 JDBC 사이에서 동작하게 된다.

- ORM(Object-relational mapping, 객체 관계 매핑) : 객체는 객체대로 설계하고, 관계형 데이터베이스는 관계형 데이터베이스대로 설계하는 기술로 ORM 프레임워크가 중간에서 객체와 테이블을 매핑해준다. 현재 대중적인 언어는 대부분 ORM 기술이 존재한다.

## JPA 동작 예시
- INSERT : DAO -> PERSIST -> JPA -> Entity 분석 -> INSERT SQL 생성 -> JDBC API 사용 -> INSERT SQL -> DB

- SELECT : DAO -> find -> JPA -> SELECT (JOIN) SQL 생성 -> JDBC API 사용 -> ResultSet 매핑 -> JDBC API 사용 -> SELECT (JOIN) SQL -> DB -> Entity Object -> DAO

- 이러한 JPA를 사용하여 패러다임의 불일치를 해결 할 수 있다.

## Hibernate

과거의 EJB라는 자바 표준이 존재하였는데 이는 아마추어(?)수준의 아주 느리고, 비효율적인 표준 방식이었다. 이에 Gavin King이라는 개발자가 오픈 소스의 하이버네이트를 만들었고, 이에 EJB는 거의 사용되지 않았다. 이 후 하이버네이트의 창시자인 Gavin King은 JPA 자바 표준을 만드는 프로젝트에 같이 참여하여 JPA를 만들게 된다. 이 때 JPA는 하이버네이트와 거의 같다 볼 수 있고, 인터페이스만 있다고 볼 수 있고, 구현체는 각 기업에서 하는 것이다. 그 중 오픈소스 구현체 중 하나가 하이버네이트이다. 

## JPA의 장점
- SQL 중심적인 개발에서 객체 중심으로의 개발

- 생산성 : CRUD에서 간단한 메소드로 DB에 변화를 줄 수 있다.

- 유지보수 : 개발자의 실수를 줄일 수 있다.

- 패러다임의 불일치 해결 : 상속, 연관관계 객체 그래프 탐색, 비교하기 등의 문제를 해결 할 수 있다.

- 성능 : 1차 캐시와 동일성 보장, 트랜잭션을 지원하는 쓰기 지연, 지연 로딩 등의 장점이 있다. 성능상의 문제가 많다는 지적이 있지만, 성능을 파악하여 최적화하면 성능상 문제는 없다.
  
  - 1차 캐시와 동일성 보장 : 같은 트랜잭션 안에서는 같은 엔티티를 반환 (조회 성능 향상), DB Isolation Level이 Read Commit이어도 애플리케이션에서 Repeatable Read 보장
  
  - 트랜잭션을 지원하는 쓰기 지연 (INSERT) : 트랙잭션을 커밋할 때까지 INSERT SQL을 모아서 JDBC BATCH SQL 기능을 사용하여 한번에 SQL을 전송.
  
  - 지연 로딩 : 객체가 실제 사용될 때 로딩하는 방식.
  
  - 즉시 로딩 : JOIN SQL로 한번에 연관된 객체까지 미리 조회하는 방식.
  
- 데이터 접근 추상화 벤더 독립성

## ORM을 다루기 위해서는..

ORM은 객체와 RDB 두 기둥위에 있는 기술로 실제 현업에서는 DB에서 일어나는 장애가 대부분이다. 떄문에 기본적으로 RDB를 잘 다루는 것이 중요한데 이를 최적화하여 ORM이 도와주는 것이다. 이를 자동화해준다고 해도 실제로 일어나는 Query를 예상하고, 많은 지식을 가지고 있어야 최적의 ORM을 사용할 수 있다.
