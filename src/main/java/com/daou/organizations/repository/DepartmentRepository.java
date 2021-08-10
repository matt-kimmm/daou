package com.daou.organizations.repository;

import com.daou.organizations.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findOneByRoot(boolean b);

    Department findOneById(long upperDepartmentId);

    Department findOneByCode(String deptCode);
}