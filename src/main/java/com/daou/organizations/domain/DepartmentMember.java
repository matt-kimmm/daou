package com.daou.organizations.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
public class DepartmentMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="department_member_id")
    long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="department_id")
    @Setter
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    @Setter
    private Member member;

}