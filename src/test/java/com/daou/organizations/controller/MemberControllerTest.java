package com.daou.organizations.controller;

import com.daou.organizations.domain.NodeModel;
import com.daou.organizations.repository.DepartmentMemberRepository;
import com.daou.organizations.service.department.DepartmentService;
import com.daou.organizations.service.department.dto.DepartmentDto;
import com.daou.organizations.service.member.MemberService;
import com.daou.organizations.service.member.dto.MemberDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    MemberService memberService;

    @Autowired
    DepartmentMemberRepository departmentMemberRepository;

    @BeforeEach
    void tearDown() {
        departmentService.deleteAllDepartment();
        departmentMemberRepository.deleteAll();
        memberService.deleteAllMember();
    }


    /***********************************************
     * 멤버 테스트
     ***********************************************/

    @Test
    @DisplayName("멤버를_추가한다")
    public void create_member_200() throws Exception {

        // Given - ABC 회사 추가, 경영지원 본부 추가
        DepartmentDto companyDto = new DepartmentDto("ABC회사", null, true, 0);
        NodeModel company = departmentService.createDepartment(companyDto);

        List<Long> departmentIds = new ArrayList<>();
        departmentIds.add(company.getId());

        // When
        MemberDto requestBody = new MemberDto("김다우", true, departmentIds);

        mockMvc.perform(post("/org/member")
                .content(objectMapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(requestBody.getName()))
                .andExpect(jsonPath("manager").value(requestBody.getManager()));
    }

    @Test
    @DisplayName("멤버를_수정한다")
    public void update_member_200() throws Exception {

        // Given
        // 회사 추가
        DepartmentDto companyDto = new DepartmentDto("ABC회사", null, true, 0);
        NodeModel company = departmentService.createDepartment(companyDto);

        List<Long> departmentIds = new ArrayList<>();
        departmentIds.add(company.getId());

        MemberDto memberDto = new MemberDto("수정 전 멤버", true, departmentIds);
        NodeModel member = memberService.createMember(memberDto);

        // When - '수정 전 멤버' 를 '수정 후 멤버' 변경
        MemberDto requestBody = new MemberDto("수정 후 멤버", true, departmentIds);

        mockMvc.perform(put("/org/member/" + member.getId())
                .content(objectMapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(requestBody.getName()))
                .andExpect(jsonPath("manager").value(requestBody.getManager()));
    }

    @Test
    @DisplayName("멤버를 삭제한다.")
    public void delete_member_200() throws Exception {

        // Given
        // 회사 추가
        DepartmentDto companyDto = new DepartmentDto("ABC회사", null, true, 0);
        NodeModel company = departmentService.createDepartment(companyDto);

        List<Long> departmentIds = new ArrayList<>();
        departmentIds.add(company.getId());

        MemberDto memberDto = new MemberDto("김다우", true, departmentIds);
        NodeModel member = memberService.createMember(memberDto);

        // When
        mockMvc.perform(delete("/org/member/" + member.getId()))
                // Then
                .andDo(print())
                .andExpect(status().isOk());
    }

}