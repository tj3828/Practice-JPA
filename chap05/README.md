# Chap05

 - 양방향 매핑
 
 - 매핑 어노테이션
 
 - 복합키 어노테이션

## 양방향 매핑

- Member에서 Team을 조회했고, 이제 Team에서도 여러 맴버를 조회할 수 있도록 매핑할 수 있다.

~~~
@Entity
public class Team {
    ... 

    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();
    
    ...
}
~~~

- 논리적으로 하나의 팀에는 여러 맴버가 올 수 있기 떄문에 @OneToMany와 매핑할 요소를 mappedBy 속성에 명시해주면 된다.

~~~
Member findMember = em.find(Member.class, member.getId());
Team findTeam = findMember.getTeam();
int memberSize = findTeam.getMembers().size(); // 역방향 조회
~~~

 - 위와 같이 양방향의 매핑을 한다면, 마치 테이블처럼 양방향으로 관계가 맺어진 것처럼 보인다. 하지만 실제로 객체에서는 단방향 객체를 각 방향으로 하나씩 둔 상태로 양방향처럼 보이게 만든 것이다.
 
 - 결국, 객체는 논리적으로 양방향으로 생각하게 서로 다른 단방향 연관관계 2개를 만들어야 하는 것이다.


## 연관관계의 주인 (Owner)

 - 기존 Member 객체에 Team을 넣는다면, Team 필드에 연관관계 매핑된 MEMBER 테이블에 FK에 매핑된다.
 
 - 하지만, Team 객체의 members 리스트에 맴버를 추가한다면, 애매한 상황이 발생한다. 
 
   - Member를 추가한다고 생각하자.
   
   - Member가 속한 Team을 Member 객체에 넣어준다.(1)
   
   - Team이 가진 맴버 목록(members)에 Member를 추가한다.(2)
   
 - 이 경우, 아무런 장치가 없다면 (1)과 (2) 동작에서 MEMBER FK의 생 두 번 일어난다고 볼 수 있고, 이것이 패러다임의 가장 큰 문제이다.
 
 
 - 이를 해결하기 위해 연관관계의 주인(Owner)을 정해주는 것이다.
 
 ## 양방향 매핑 규칙
 
 - 객체의 두 관계중 하나를 연관관계의 주인으로 지정한다.
 
 - 연관관계의 주인만이 외래 키를 관리하여 등록 및 수정 작업이 가능토록 한다.
 
 - 주인이 아닌 쪽은 읽기 작업만 가능하다.
 
 - 주인이 아닌 쪽에 mappedBy 속성을 통해 주인의 필드 이름을 지정해준다.
 
 ## 양방향 매핑의 주의
 
 - 외래키가 있는 객체를 주인으로 정하는 것이 편하다. 
 
 - 연관관계의 주인을 기준으로 등록, 수정 기능에 주의하여 구현해야한다.
 
 - 실제로 이 개념을 이해하고 적용하기 어려워, 작동하지 않는 것처럼 보여지는 경우가 많다.
 
 - 잘못된 예시 (MEMBER FK null)
 ~~~
 ...
 
 Team team = new Team();
 team.setName("teamA");
 em.persist(team);

 Member member = new Member();
 member.setName("안녕하세요");
 member.setTeam(team);
 member.setMemberType(MemberType.USER);
 
 team.getMembers().add(member);
 
 em.persist(member);
 
 ...
 ~~~
 - 실제 객체 관점에서는 연관관계의 주인을 정했다고 하더라도, 모든 객체에 값을 설정하는 것을 권장한다.
 ~~~
  ...
  
  team.getMembers().add(member);
  member.setTeam(team);
    
  ...
  ~~~
 
## 양방향 매핑의 장점

- 단방향 매핑만으로도 이미 연관관계 매핑은 된 것이지만 양방향 매핑으로 객체 그래프 탐색이 가능하다.
 
- JPQL을 사용할 때 양방향 탐색할 일이 많다.

- 테이블에 영향을 주지 않기 때문에, 단방향 매핑을 잘하고, 양방향은 필요할 때 추가해도 된다.

## 연관관계 매핑 어노테이션

- @ManyToOne (다대일)

- @OneToMany (일대다)

- @OneToOne  (일대일)

- @ManyToMany(다대다)

- @JoinColumn

- @JoinTable

## 상속 관계 매핑 어노테이션

- @Inheritance

- @DiscriminatorColumn

- @DiscriminatorValue

- @MapperSuperclass

## 복함키 어노테이션

- @IdClass

- @EmbeddedId

- @Embeddable

- @MapsId
 