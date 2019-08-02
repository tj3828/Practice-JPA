# Chap04

- 연관관계 매핑

- 단방향 매핑
## 객체를 테이블에 맞추어 모델링

~~~
Member findMember = em.find(Member.class, member.getId());
Long teamId = findMember.getTeamId();

Team findTeam = em.find(Team.class,team.getId());
~~~

- 연관관계가 없기 때문에 하나하나 씩 따로 조회해야한다.

- 객체 지향적인 방법이 아닌 데이터 중심 모델링이라고 볼 수 있다.

- 이러한 구조에서는 협력 관계를 만들 수 없다.

## 단방향 매핑

- Member의 teamId를 Team 객체를 참조하는 필드로 변경한다.

- 객체에서는 Member에서 Team을 참조할 수 있지만, Team에서는 Member를 참조할 수 없다.

~~~
@Entity 
public class Member {
    ...
    
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;
    
    ...
}
~~~

 - 하나의 팀에는 여러명의 Member가 존재하는 것이기 때문에 @ManyToOne과 참조할 FK의 이름을 @JoinColumn을 통해 지정해준다.
 
 - 위와 같은 경우에는 Member 객체의 Team 필드가 DB의 MEMBER 테이블의 TEAM_ID(FK)와 연관관계 매핑된다.
 
 ~~~
 Member member = new Member();
 member.setName("안녕하세요");
 member.setTeam(team); // Team 객체를 저장
 member.setMemberType(MemberType.USER);

 em.persist(member);

 Member findMember = em.find(Member.class, member.getId());
 Team findTeam = findMember.getTeam(); // 참조를 사용하여 연관관계 조회
~~~



                
