package com.example.jpa.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByName(String name);
    Page<Member> findByName(String name, Pageable pageable);

    Member findByAge(int i);

}
