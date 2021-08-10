package com.daou.organizations.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
public class NodeModel {

    private long id;

    private String type;

    @Setter
    private String name;

    private String code;

    private Boolean manager;

    @Setter
    private NodeModel upperNode;

    @Setter
    private List<NodeModel> children = new ArrayList<>();

    public void convertDepartmentToNodeModel(Department department) {
        this.id = department.getId();
        this.type = department.isRoot() ? "Company" : department.getSubDepartmentsCount() > 0 ? "Division" : "Department";
        this.name = department.getName();
        this.code = department.getCode();
    }

    public void convertMemberToNodeModel(Member member) {
        this.id = member.getId();
        this.type = "Member";
        this.name = member.getName();
        this.manager = (member.getManager() == true) ? member.getManager() : null;
    }

    public void addChildren(NodeModel childTree) {
        children.add(childTree);
    }
}



