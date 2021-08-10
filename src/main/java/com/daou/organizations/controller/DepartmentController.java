package com.daou.organizations.controller;

import com.daou.organizations.domain.Department;
import com.daou.organizations.domain.NodeModel;
import com.daou.organizations.service.department.dto.DepartmentDto;
import com.daou.organizations.service.department.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping(value = "/org/dept")
public class DepartmentController { // 2. 부서 관리

    private final DepartmentService departmentService;

    @PostMapping // (1) 부서 추가
    public NodeModel createDepartment(@RequestBody DepartmentDto departmentDto) {

        NodeModel department = departmentService.createDepartment(departmentDto);
        return department;
    }

    @PutMapping("/{deptId}") // (2) 부서 수정
    public NodeModel updateDepartment(@PathVariable long deptId, @RequestBody DepartmentDto departmentDto) {
        return departmentService.updateDepartment(deptId, departmentDto);
    }

    @DeleteMapping("/{deptId}") // (3) 부서 삭제
    public void deleteDepartment(@PathVariable long deptId) {
        departmentService.deleteDepartment(deptId);
    }

}
