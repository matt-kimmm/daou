package com.daou.organizations.service.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@Getter
public class MemberDto {

    private String name;

    private Boolean manager;

    private List<Long> departmentIds = new ArrayList <>();
}
