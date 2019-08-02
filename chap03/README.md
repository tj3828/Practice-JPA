# Chap03
필드와 컬럼 매핑

## 데이터베이스 스키마 자동 생성
- DDL을 애플리케이션 실행 시점에 자동 생성

- 테이블 중심 -> 객체 중심

- 데이터베이스 방언을 활용해서 데이터베이스에 맞는 적절한 DDL 생성

- 생성된 DDL은 운영서버에서는 사용하지 않거나, 적절히 다음은 후 에 사용되고, 위에서 생성된 DDL은 개발 장비에서만 사용

## 스키마 자동 생성시 주의
- spring.jpa.hibernate.ddl-auto
  
  - create : 기존 테이블 삭제 후 다시 생성 (DROP + CREATE)
  
  - create-drop : create와 같으나 종료시점에 테이블 DROP
  
  - update : 변경분만 반영
  
  - validate : 엔티티와 테이블이 정상 매핑되었는지만 확인
  
  - none : 사용하지 않음
 
 - 운영장비에서는 절대 create, create-drop, update는 사용하면 안된다.
 
   - 개발 초기단계 : create or update
 
   - 테스트 서버 : update or validate
 
   - 스테이징과 운영서버 : validate or none
   
 ## 매핑 Annotation
 - 자바 객체에 영향을 주진 않고 철저히 매핑을 위한 어노테이션
 
   - @Column : DB에 들어갈 테이블의 칼럼 이름을 정의함.
   
   - @Enumerated :  Enum에 대한 매핑. 
   
   - @Temporal : 시간관련한 매핑 정의.
   
   - @Lob : 컨텐츠의 길이가 클때 사용.
   
   - @Transient : 매핑은 하지 않지만, 객체에는 넣고 싶은 필드. 예를 들어, flag값 
   
  
 ### @Column 속성
   
   - name : 필드와 매핑할 테이블의 칼럼 이름
   
   - insertable, updateable : 읽기 전용
   
   - nullable : null 허용여부 결정, DDL 생성시 사용
   
   - unique : 유니크 제약 조건, DDL 생성시 사용
   
   - columnDefinition, length, precision, scale (DDL)
   
 ### @Enumerated value
 
   - EnumType.STRING : 실무에서 사용하는 것을 추천. 열겨형 이름을 그대로 저장한다.
   
   - EnumType.ORDINAL : Enum 클래스에 정의된 원소들을 순서대로 숫자로 반환하여 유지보수하기에 좋지 않음. 
   
  ### @Temporal
  
   - TemporalType.DATE : 날짜
   
   - TemporalType.TIME : 시간
   
   - TemporalType.TIMESTAMP : 날짜와 시간
   
  ### @Lob
  
   - CLOB : String, char[], java.sql.CLOB
   
   - BLOB : byte[], java.sql.BLOB
   
   - 필드의 타입에 따라 CLOB과 BLOB이 결정됨.
   
   
  ## 식별자 매핑
  
  - @Id : 직접 매핑
  
  - @GeneratedValue  
  
    - IDENTITY : 데이터베이스에 위임, MYSQL
        
    - SEQUENCE : 데이터베이스 시퀀스 오브젝트 사용, ORACLE (@SequenceGenerator 필요)
  
    - TABLE : 키 생성용 테이블 사용, 모든 DB 사용 (@TableGenerator 필요)
    
    - Auto : 방언에 따라 자동 지정, 기본값   
    
  ## 권장 식별자 전략
  
   - 기본 키 제약 조건 : null x, 유일, 변하지 않는다.
   
   - 미래까지 이 조건을 만족하는 자연키(비지니스 키)는 찾기 어렵기 때문에 대리키(대체키)를 사용해야 한다.
   
   - 예를 들어, 주민등록번호도 기본 키로 사용하다가 주민등록번호의 정책이 바껴 디비에 등록하지 못하는 사례도 있다.
   
   - 권장 : Long + 대체키 + 키 생성전략 사용