package com.example.jpa;

import com.example.jpa.user.Member;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

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
                Member member = new Member();
                member.setId(100L);
                member.setName("안녕하세요");

                em.persist(member);

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
