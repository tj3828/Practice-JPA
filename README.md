# JPA 프로그래밍의 기본

- JPA의 기초와 매핑
- 필드와 컬럼 매핑
- 연관관계 매핑
- 양방향 매핑
- JPA의 내부구조
- JPA 객체지향쿼리
- Spring Data JPA, QueryDSL

 ## Start Project
 - Spring Initializr 
 
 ## Maven
 - Spring Web
  ~~~
  <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-web</artifactId>
 </dependency>
 ~~~
 
 - Spring Data JPA
 ~~~
 <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-data-jpa</artifactId>
 </dependency>
 ~~~    
 - Embedded H2
 ~~~
 <dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
 ~~~
 
 ## H2-console setting
 - application.properties
 ~~~
 spring.h2.console.enabled=true
 spring.datasource.url=jdbc:h2:~./test
 ~~~ 
 
 ## H2-console URL
 - http://localhost:8080/h2-console


# 참고자료

- [Tacademy](https://www.youtube.com/playlist?list=PL9mhQYIlKEhfpMVndI23RwWTL9-VL-B7U)