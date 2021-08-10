package com.daou.organizations.controller;

import com.daou.organizations.domain.NodeModel;
import com.daou.organizations.service.department.DepartmentService;
import com.daou.organizations.service.department.dto.DepartmentDto;
import com.daou.organizations.service.member.MemberService;
import com.daou.organizations.service.member.dto.MemberDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class OrganizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    MemberService memberService;

    @BeforeAll
    static void setUp() {
        // 조직도 작성

    }

    @Test
    @DisplayName("조직도 전체 조회")
    public void get_all_organization_200() {

        // Given
        DepartmentDto companyDto = new DepartmentDto("ABC회사",null,true,0);
        NodeModel company = departmentService.createDepartment(companyDto);

        DepartmentDto divisionDto = new DepartmentDto("SW개발본부", "D100", false, company.getId());
        NodeModel division = departmentService.createDepartment(divisionDto);

        DepartmentDto departmentDto110 = new DepartmentDto("플랫폼개발부", "D110", false, division.getId());
        NodeModel department110 = departmentService.createDepartment(departmentDto110);

        DepartmentDto departmentDto111 = new DepartmentDto("비즈플랫폼팀", "D111", false, department110.getId());
        NodeModel department111 = departmentService.createDepartment(departmentDto111);

        DepartmentDto departmentDto112 = new DepartmentDto("비즈서비스팀", "D112", false, department110.getId());
        NodeModel department112 = departmentService.createDepartment(departmentDto112);

        DepartmentDto departmentDto113 = new DepartmentDto("그룹웨어개발팀", "D113", false, department110.getId());
        NodeModel department113 = departmentService.createDepartment(departmentDto113);

        DepartmentDto departmentDto120 = new DepartmentDto("비즈니스서비스개발부", "D120", false, division.getId());
        NodeModel department120 = departmentService.createDepartment(departmentDto120);

        DepartmentDto departmentDto121 = new DepartmentDto("플랫폼서비스팀", "D121", false, department120.getId());
        NodeModel department121 = departmentService.createDepartment(departmentDto121);

        DepartmentDto departmentDto122 = new DepartmentDto("모바일개발팀", "D122", false, department120.getId());
        NodeModel department122 = departmentService.createDepartment(departmentDto122);


        List<Long> companyIds = new ArrayList<>();
        companyIds.add(company.getId());

        MemberDto memberDto1 = new MemberDto("김다우", true, companyIds);
        NodeModel member1 = memberService.createMember(memberDto1);

        List<Long> divisionIds = new ArrayList<>();
        divisionIds.add(division.getId());

        MemberDto memberDto2 = new MemberDto("김개발", true, divisionIds);
        NodeModel member2 = memberService.createMember(memberDto2);

        List<Long> departmentIds1 = new ArrayList<>();
        departmentIds1.add(department111.getId());

        MemberDto memberDto3 = new MemberDto("장명부", false, departmentIds1);
        NodeModel member3 = memberService.createMember(memberDto3);

        MemberDto memberDto4 = new MemberDto("최동원", false, departmentIds1);
        NodeModel member4 = memberService.createMember(memberDto4);




    }

    @Test
    @DisplayName("조직도 부만서 조회")
    public void get_dept_only_organization_200() {

    }

    @Test
    @DisplayName("조직도 부서 코드로 조회")
    public void get_organization_by_dept_code_200() {

    }

    @Test
    @DisplayName("조직도 멤버 조회")
    public void get_organization_by_member_search_200() {

    }

    @Test
    @DisplayName("조직도 부서로 조회")
    public void get_organization_by_dept_search_200() {

    }

}