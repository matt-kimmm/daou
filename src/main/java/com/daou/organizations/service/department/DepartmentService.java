package com.daou.organizations.service.department;

import com.daou.organizations.service.department.dto.DepartmentDto;
import com.daou.organizations.domain.NodeModel;

public interface DepartmentService {
    NodeModel getOrganiztion(String deptCode, boolean deptOnly, String searchType, String searchKeyword);
    NodeModel createDepartment(DepartmentDto departmentDto);
    NodeModel updateDepartment(long departmentId, DepartmentDto departmentDto);
    void deleteDepartment(long departmentId);
    void deleteAllDepartment();
}