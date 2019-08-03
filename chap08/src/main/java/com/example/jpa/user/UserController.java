package com.example.jpa.user;

import com.example.jpa.user.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RestController
public class UserController {


    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    public void init(){
        memberRepository.save(new Member("hello1", 1));
        memberRepository.save(new Member("hello2", 2));
        memberRepository.save(new Member("hello3", 3));
        memberRepository.save(new Member("hello4", 4));
        memberRepository.save(new Member("hello5", 5));
    }

    @RequestMapping("/findName")
    Member member() {
        return memberRepository.findByName("hello1");
    }

    @RequestMapping("/members")
    List<Member> members() {
        return memberRepository.findAll();
    }

    @RequestMapping("/findNamePage")
    Page<Member> findNamePage(@RequestParam("name") String name) {
        PageRequest pageRequest = PageRequest.of(0,10);
        return memberRepository.findByName(name, pageRequest);
    }

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
}
