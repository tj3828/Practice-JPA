# Chap08

- Spring Data JPA

- QueryDSL

## Spring Data JPA
 - 반복되는 CRUD 문제를 세련된 방법으로 해결
 
 - 개발자는 인터페이스만 작성하고 Spring Data JPA가 구현 객체를 동적으로 생성해서 주입
 
 - 적용 전
 ~~~
 public class MemberRepository {
    public void save(Member member) {...}
    public Member findOne(Long id) {...}
    public List<Member> findAll() {...}
    
    public Member findByUsername(String username) {...}
 }
 
 public class ItemRepository{
    public void save(Iten item) {...}
    public Member findOne(Long id) {...}
    public List<Member> findAll() {...}
 }
 ~~~
 
 - 적용 후
 ~~~
 public interface MemberRepository extends JpaRepository<Member, Long>{
    Member findByName(String name);
 }
 
 public interface ItemRepository extends JpaRepository<Item, Long>{
    // 비어있음
 }
 ~~~
 
 - 상속받은 JpaRepository 인터페이스 안에 기본적인 save(), findOne(), findAll() 등의 기능이 들어있다.
 
 ## 주요 기능
 
  - 메서드 이름만으로 쿼리 생성
  ~~~
  public interface MemberRepository extends JpaRepository<Member, Long>{
     Member findByName(String name);
  }
  
  SQL : SELECT * FROM MEMBER M WHERE M.USERNAME = 'hello'
  ~~~
  
  - 메서드의 이름만으로 애플리케이션의 로딩시점에서 오류를 판단할 수 있다.
  
  - 검색 + 정렬 + 페이징(page, size, sort)
  ~~~
  Page<Member> findByName(String name, Pageable pageable);
  ~~~
  
  - @Query, JPQL 정의 (애플리케이션 로딩시점에 오류체크)
  
  ~~~
  public interface MemberRepository extends JpaRepository<Member, Long> {
  
      @Query("select m from Member m where m.username = ?1")
      Member findByName(String name);
  }
  ~~~
  
  - 반환타입의 자유로운 선언
  
## QueryDSL

  - SQL과 JPQL을 코드로 작성할 수 있도록 해주는 빌더 API
  
  - JPA 크리테리아에 비해서 편리하고 실용적
  
  - SQL과 JPQL은 문자이기 때문에, Type-check가 불가능하다. 애플리케이션 로딩시점에 오류체크가 최선이다.
  
  - QueryDSL은 문자가 아닌 코드로 작성하여, 컴파일 시점에 문법 오류를 발견하고 IDE의 자동완성 도움을 받을 수 있다.
  
  - 동적쿼리에서 큰 장점이 있다.
  
## QueryDSL 동작원리

   - Member.java Entity -> APT -> QMember.java 생성
   
## QueryDSL 사용법

   - dependency
   
   ~~~
   <dependency>
       <groupId>com.querydsl</groupId>
       <artifactId>querydsl-jpa</artifactId>
       <version>4.2.1</version>
   </dependency>
   <dependency>
       <groupId>com.querydsl</groupId>
       <artifactId>querydsl-apt</artifactId>
       <version>4.2.1</version>
   </dependency>
   ~~~
   
   - plugin
   
   ~~~
   <plugin>
       <groupId>com.mysema.maven</groupId>
       <artifactId>apt-maven-plugin</artifactId>
       <version>1.1.3</version>
       <executions>
           <execution>
               <phase>generate-sources</phase>
               <goals>
                   <goal>process</goal>
               </goals>
               <configuration>
                   <outputDirectory>${project.build.directory}/generated-sources/java</outputDirectory>
                   <processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
               </configuration>
           </execution>
       </executions>
   </plugin>
   ~~~
   
   - Example
   
   ~~~
   @RequestMapping("/findQueryDSL")
   public List<Member> queryDSL() {
       JPAQueryFactory query = new JPAQueryFactory(em);
       QMember m = QMember.member;

       List<Member> list = query.selectFrom(m)
               .where(m.age.gt(0).and(m.name.contains("hello")))
               .orderBy(m.age.desc())
               .fetch();

       return list;

   }
   ~~~
   
   - 동적쿼리
   
   ~~~
   String name = "member";
   int age = 9;
   
   QMember m = QMember.member;
   
   BooleanBuilder builder = new BooleanBuilder();
   if(name != null) {
      builder.and(m.name.contains(name));
   }
   if(age != 0) {
      builder.and(m.age.gt(age));
   }
   
   List<Member> list = query.selectFrom(m).where(builder).fetch(); 
   ~~~
   
   - 제약조건 조립가능 (가독성, 재사용)
   
   ~~~
   return quert.selectFrom(coupon)
            .where(
                coupon.type.eq(typePara),
                isServiceable()
            )
            .fetch();
    
   private BooleanExpression isServiceable() {
       return coupon.status.eq("LIVE")
                .and(marketing.viewCount.lt(marketing.maxCount));
   }
   ~~~ 
   