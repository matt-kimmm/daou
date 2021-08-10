package com.daou.organizations.repository;

import com.daou.organizations.domain.DepartmentMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface DepartmentMemberRepository extends JpaRepository<DepartmentMember, Long> {

    @Transactional
    void deleteByMemberId(long memberId);

    @Transactional
    void deleteByDepartmentId(long departmentId);
}
