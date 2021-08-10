package com.daou.organizations.controller;

import com.daou.organizations.domain.NodeModel;
import com.daou.organizations.service.department.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
@CrossOrigin
public class OrganizationController { // 1. 조직도 조회

    private final DepartmentService departmentService;

    /*
           deptCode : 기준 부서 코드
           deptOnly : 부서 정보만 응답 여부
           searchType : 검색 대상 (dept : 부서, member : 부서원)
           searchKeyword : 검색어
     */

    @GetMapping(value = "/organizations")
    public NodeModel getOrganization(String deptCode, boolean deptOnly, String searchType, String searchKeyword) {
        return departmentService.getOrganiztion(deptCode, deptOnly, searchType, searchKeyword);
    }

}

