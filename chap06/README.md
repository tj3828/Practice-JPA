# Chap06

 - JPA의 내부구조
 
 - 영속성 컨텍스트
 
 - 프록시와 즉시로딩, 지연로딩
 
## JPA의 간단한 동작원리

- Client의 요청 -> EntityManagerFactory의 EntityManager 생성 (쓰레드당 한개) -> 커넥션풀 -> DB
 
## 영속성 컨텍스트

 - "엔티티를 영구 저장하는 환경"
 
 - 눈에 보이지 않는 논리적인 개념
 
 - EntitiyManager를 통해 영속성 컨텍스트에 접근
 
## 엔티티의 생명주기

- 비영속 (new / transient) : 영속성 컨텍스트와 전혀 관계가 없는 상태
    - 영속성 컨텍스트에 넣진 않고, 객체만 생성한 상태.
    - Example
~~~
Member member = new Member();
member.setId("member1");
member.setUsername("회원1");
~~~

- 영속 (managed) : 영속성 컨텍스트에 저장된 상태
    - Example
~~~
Member member = new Member();
member.setId("member1");
member.setUsername("회원1");

em.persist(member);
~~~

- 준영속 (detached) : 영속성 컨텍스트에 저장되었다가 분리된 상태
    - em.detach(entity) : 특정 엔티티를 준영속 상태로 전환
    
    - em.clear() : 영속성 컨텍스트를 완전히 초기화
    
    - em.close() : 영속성 컨텍스트 종

    - Example
~~~
Member findMember = em.find(Member.class, member.getId());
em.detach(findMember);
findMember.setName("JPA STUDY"); // update 쿼리 실행 X
~~~

- 삭제 (removed) : 객체가 삭제된 상태
    - Example
~~~
em.remove(member);
~~~

## 영속성 컨텍스트의 이점

 - 1차 캐시, 엔티티 조회
    - 일종의 영속성 컨텍스트 안의 메모리라고 비유됨.
    
    - 트랜잭션 내에서만 유지됨.
    
    - @Id가 Key, Entity가 Value로 저장됨.
    
    - Ex.1)1차 캐시에 저장된 데이터를 조회시 DB에 접근하지 않고 조회함.
    
    - Ex.2)DB에서 조회할 때 1차캐시에 넣고 결과값 반환함.
    
 - 동일성 보장 (1차 캐시 이용)
~~~
Member a = em.find(Member.class, "member1");
Member b = em.find(Member.class, "member1");
System.out.println(a == b); // true
 ~~~
    
 - 쓰기지연
    - 트랜잭션 내에서 내부적으로 버퍼기능을 하여 SQL을 모아 한번에 보낼 수 있다.
    
    - 논리적으로는 persist 실행될 때 1차 캐시에 저장 후 생성된 SQL을 모았다가 commit()되는 시점에 한번에 SQL을 실행한다.
  
 - 변경 감지, 엔티티 수정
    - update와 같은 기능이 없는 이유 변경 감지를 통해 자동으로 영속 엔티티를 수정했을 때 update가 실행되기 때문이다.
    
    - 1차 캐시를 생성할 때 스냅샷도 만든다. 이 1차 캐시에 저장된 엔티티와 스냅샷을 비교하여 수정된 것이 있다면, 자동으로 persist 후 update 시킴.
    
    ~~~
    Member findMember = em.find(Member.class, member.getId());
    findMember.setName("JPA STUDY");
    ~~~
    
 ## flush
   - flush() : 변경감지 후 영속성 컨텍스트의 작업부터 SQL을 DB에 보내는 작업까지 진행, 트랜잭션 커밋과 JPQL 쿼리 실행시 자동으로 실행 됨.
     
   - 목적은 영속성 컨텍스트의 변경내용을 데이터베이스에 동기화하는 것으로 영속성 컨텍스트를 비우지 않는다.
        
   - commit() : flush() + DB의 commit
   
 ## 지연로딩
   - Member 객체를 조회하는데 Team 객체의 정보를 필요로 하지 않을 때는 Lazy를 이용하여 프록시로 조회한다.
   ~~~
   ...
   
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "TEAM_ID")
   private Team team; 
   
   ...
   ~~~
   
   - Team 객체에 대한 정보는 실제 team을 사용하는 시점에 team의 정보를 가져온다.
   
  ## 즉시로딩
  - Member 객체를 조회할 때 Team 객체의 정보도 바로 조회한다.
  
  - 실제 실무에서는 가급적 지연로딩이 성능상 용이하다.
  
  - 예상하지 못한 SQL이 발생하는 경우가 많 JPQL에서 N+1 문제를 일으킨다.
    
~~~
...

@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "TEAM_ID")
private Team team; 

...
~~~
   
   
  