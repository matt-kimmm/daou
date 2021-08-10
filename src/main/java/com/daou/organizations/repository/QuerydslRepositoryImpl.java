package com.daou.organizations.repository;

import com.daou.organizations.domain.Department;
import com.daou.organizations.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.daou.organizations.domain.QDepartment.department;
import static com.daou.organizations.domain.QMember.member;

@RequiredArgsConstructor
@Repository
public class QuerydslRepositoryImpl implements QuerydslRepository{
    private final JPAQueryFactory query;

    @Override
    public List<Department> findDepartmentsByKeyword(String keyword) {
        List<Department> findDepartments = query
                .select(department)
                .from(department)
                .where(department.name.containsIgnoreCase(keyword))
                .fetch();

        return findDepartments;
    }

    @Override
    public List<Member> findMemberByKeyword(String keyword) {
        List<Member> findMembers = query
                .select(member)
                .from(member)
                .where(member.name.containsIgnoreCase(keyword))
                .fetch();

        return findMembers;
    }
}
