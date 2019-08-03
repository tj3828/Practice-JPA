package com.example.jpa;

import com.example.jpa.team.Team;
import com.example.jpa.user.Member;
import com.example.jpa.user.MemberType;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

@SpringBootApplication
public class JpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner(EntityManagerFactory emf) {
        return (args) -> {
            EntityManager em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            try {
                Team team = new Team();
                team.setName("teamA");
                em.persist(team);

                Member member = new Member();
                member.setName("안녕하세요");
                member.setTeam(team);
                member.setMemberType(MemberType.USER);

                em.persist(member);
                team.getMembers().add(member);

                String jpql = "select m From Member m join fetch m.team where m.name like '%hello%'";
                em.createQuery(jpql, Member.class)
                        .setFirstResult(10)
                        .setMaxResults(20)
                        .getResultList();

                tx.commit();
            } catch (Exception e) {
                tx.rollback();
            } finally {
                em.close();
            }
            emf.close();
        };

    }
}
