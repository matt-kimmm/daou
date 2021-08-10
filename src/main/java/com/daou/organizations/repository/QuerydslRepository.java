package com.daou.organizations.repository;

import com.daou.organizations.domain.Department;
import com.daou.organizations.domain.Member;

import java.util.List;

public interface QuerydslRepository {

    List<Department> findDepartmentsByKeyword(String keyword);

    List<Member> findMemberByKeyword(String keyword);
}
