package com.daou.organizations.service.department.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@Getter
public class DepartmentDto {
    private String name;
    private String code;
    private boolean root;
    private long upperDepartmentId;
}