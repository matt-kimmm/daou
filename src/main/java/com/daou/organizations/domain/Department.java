package com.daou.organizations.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private long id;

    @Setter
    private String name;

    @Setter
    private String code;

    @Setter
    private boolean root;

    @OneToMany(mappedBy = "upperDepartment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Department> subDepartments = new ArrayList<>();

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DepartmentMember> subMembers = new ArrayList<>();

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="upper_department_id", nullable = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private Department upperDepartment;

    public long getSubDepartmentsCount() { return this.subDepartments.size(); }

    public long getSubMembersCount() { return this.subMembers.size(); }


}
