package com.daou.organizations.controller;

import com.daou.organizations.domain.NodeModel;
import com.daou.organizations.service.department.DepartmentService;
import com.daou.organizations.service.department.dto.DepartmentDto;
import com.daou.organizations.service.member.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    MemberService memberService;

    @BeforeEach
    void tearDown() {
        departmentService.deleteAllDepartment();
    }

    /***********************************************
     * 회사 테스트
     ***********************************************/

    @Test
    @DisplayName("회사를 추가한다.")
    public void create_company_200() throws Exception {

        // Given

        // When
        DepartmentDto requestBody = new DepartmentDto("ABC회사", null, true, 0);
        mockMvc.perform(post("/org/dept")
                .content(objectMapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON))

                // Then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(requestBody.getName()));
    }

    @Test
    @DisplayName("회사를 수정한다.")
    public void update_company_200() throws Exception {

        // Given
        // '수정 전 회사 추가'
        DepartmentDto companyDto = new DepartmentDto("수정 전 회사", null, true, 0);
        NodeModel company = departmentService.createDepartment(companyDto);

        // When
        // '수정 전 회사' 를 '수정 후 회사' 로 변경 요청
        DepartmentDto requestBody = new DepartmentDto("수정 후 회사", null, true, 0);

        mockMvc.perform(put("/org/dept/" + company.getId())
                .content(objectMapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(requestBody.getName()));
    }

    @Test
    @DisplayName("회사를 삭제한다.")
    public void delete_company_200() throws Exception {

        // Given
        // '삭제 전 회사' 추가
        DepartmentDto companyDto = new DepartmentDto("삭제 전 회사", null, true, 0);
        NodeModel company = departmentService.createDepartment(companyDto);

        // When
        mockMvc.perform(delete("/org/dept/" + company.getId())
                .content(objectMapper.writeValueAsString(company))
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andDo(print())
                .andExpect(status().isOk());
    }


    /***********************************************
     * 본부 테스트
     ***********************************************/

    @Test
    @DisplayName("본부를_추가한다.")
    public void create_division_200() throws Exception {

        // Given
        // ABC 회사 추가
        DepartmentDto companyDto = new DepartmentDto("ABC회사", null, true, 0);
        NodeModel company = departmentService.createDepartment(companyDto);

        // When
        DepartmentDto requestBody = new DepartmentDto("경영지원본부", "D100", false, company.getId());
        mockMvc.perform(post("/org/dept")
                .content(objectMapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(requestBody.getName()))
                .andExpect(jsonPath("code").value(requestBody.getCode()));
    }

    @Test
    @DisplayName("본부를_수정한다")
    public void update_division_200() throws Exception {

        // Given -  '수정 전 본부' 추가
        DepartmentDto companyDto = new DepartmentDto("ABC회사", null, true, 0);
        NodeModel company = departmentService.createDepartment(companyDto);

        DepartmentDto DivisionDto = new DepartmentDto("수정 전 본부", "D100", false, company.getId());
        NodeModel Division = departmentService.createDepartment(DivisionDto);

        // When - '수정 전 본부' 를 '수정 후 본부' 로 변경
        DepartmentDto requestBody = new DepartmentDto("수정 후 본부", "D100", false, company.getId());
        mockMvc.perform(put("/org/dept/" + Division.getId())
                .content(objectMapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(requestBody.getName()))
                .andExpect(jsonPath("code").value(requestBody.getCode()));
    }

    @Test
    @DisplayName("본부를_삭제한다")
    public void delete_division_200() throws Exception {

        // Given - '삭제 전 본부' 추가
        DepartmentDto companyDto = new DepartmentDto("ABC회사", null, true, 0);
        NodeModel company = departmentService.createDepartment(companyDto);
        DepartmentDto DivisionDto = new DepartmentDto("삭제 전 본부", "D100", false, company.getId());
        NodeModel Division = departmentService.createDepartment(DivisionDto);

        // When
        mockMvc.perform(delete("/org/dept/" + Division.getId())
                .content(objectMapper.writeValueAsString(Division))
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andDo(print())
                .andExpect(status().isOk());
    }


    /***********************************************
     * 부서 테스트
     ***********************************************/

    @Test
    @DisplayName("부서를_추가한다")
    public void create_department_200() throws Exception {

        // Given - ABC 회사 추가, 경영지원 본부 추가
        DepartmentDto companyDto = new DepartmentDto("ABC회사", null, true, 0);
        NodeModel company = departmentService.createDepartment(companyDto);

        DepartmentDto divisionDto = new DepartmentDto("경영지원본부", "D100", false, company.getId());
        NodeModel division = departmentService.createDepartment(divisionDto);

        // When
        DepartmentDto requestBody = new DepartmentDto("총무팀", "D110", false, division.getId());

        mockMvc.perform(post("/org/dept")
                .content(objectMapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(requestBody.getName()))
                .andExpect(jsonPath("code").value(requestBody.getCode()));
    }

    @Test
    @DisplayName("부서를_수정한다")
    public void update_department_200() throws Exception {

        // Given -  '수정 전 부서' 추가
        DepartmentDto companyDto = new DepartmentDto("ABC회사", null, true, 0);
        NodeModel company = departmentService.createDepartment(companyDto);

        DepartmentDto divisionDto = new DepartmentDto("경영지원본부", "D100 ", false, company.getId());
        NodeModel Division = departmentService.createDepartment(divisionDto);

        DepartmentDto DepartmentDto = new DepartmentDto("수정 전 부서", "D110", false, Division.getId());
        NodeModel Department = departmentService.createDepartment(DepartmentDto);

        // When - '수정 전 부서' 를 '수정 후 부서'  변경
        DepartmentDto requestBody = new DepartmentDto("수정 후 부서", "D110", false, Division.getId());
        mockMvc.perform(put("/org/dept/" + Department.getId())
                .content(objectMapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(requestBody.getName()))
                .andExpect(jsonPath("code").value(requestBody.getCode()));
    }

    @Test
    @DisplayName("부서를_삭제한다")
    public void delete_department_200() throws Exception {

        // Given - '삭제 전 부서' 추가
        DepartmentDto companyDto = new DepartmentDto("ABC회사", null, true, 0);
        NodeModel company = departmentService.createDepartment(companyDto);
        DepartmentDto divisionDto = new DepartmentDto("경영지원본부", "D100", false, company.getId());
        NodeModel Division = departmentService.createDepartment(divisionDto);
        DepartmentDto DepartmentDto = new DepartmentDto("삭제 전 부서", "D110", false, Division.getId());
        NodeModel Department = departmentService.createDepartment(DepartmentDto);

        // When
        mockMvc.perform(delete("/org/dept/" + Department.getId())
                .content(objectMapper.writeValueAsString(Department))
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andDo(print())
                .andExpect(status().isOk());
    }

}