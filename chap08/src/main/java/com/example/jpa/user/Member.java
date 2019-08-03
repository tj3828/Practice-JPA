package com.example.jpa.user;

import com.example.jpa.team.Team;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "USERNAME")
    private String name;

    private int age;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Member() {

    }

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public void setMemberType(MemberType memberType) {
        this.memberType = memberType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
