package com.daou.organizations.repository;

import com.daou.organizations.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findOneById(long memberId);
}
