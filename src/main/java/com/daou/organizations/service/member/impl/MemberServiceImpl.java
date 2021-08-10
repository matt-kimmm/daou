package com.daou.organizations.service.member.impl;

import com.daou.organizations.domain.Department;
import com.daou.organizations.domain.DepartmentMember;
import com.daou.organizations.domain.Member;
import com.daou.organizations.domain.NodeModel;
import com.daou.organizations.service.member.MemberService;
import com.daou.organizations.service.member.dto.MemberDto;
import com.daou.organizations.repository.DepartmentMemberRepository;
import com.daou.organizations.repository.DepartmentRepository;
import com.daou.organizations.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final DepartmentRepository departmentRepository;
    private final MemberRepository memberRepository;
    private final DepartmentMemberRepository departmentMemberRepository;

    @Override
    public NodeModel createMember(MemberDto memberDto) {
        Member member = new Member();
        member.setName(memberDto.getName());
        member.setManager(memberDto.getManager());
        Member savedMember = memberRepository.save(member);
        createDepartmentMembers(memberDto, savedMember);

        NodeModel nodeModel = new NodeModel();
        nodeModel.convertMemberToNodeModel(savedMember);
        return nodeModel;
    }

    @Override
    public NodeModel updateMember(long memberId, MemberDto memberDto) {

        Member member = memberRepository.findOneById(memberId);

        if(!StringUtils.isEmpty(memberDto.getName()) && memberDto.getName() != null && member.getName() != memberDto.getName()) {
            member.setName(memberDto.getName());
        }

        if(member.getManager() != memberDto.getManager()) {
            member.setManager(memberDto.getManager());
        }

        for (DepartmentMember departmentMember : member.getDepartments()) {
            departmentMemberRepository.delete(departmentMember);
        }

        member.getDepartments().clear();

        departmentMemberRepository.deleteByMemberId(member.getId());

        Member updatedMember = memberRepository.save(member);
        createDepartmentMembers(memberDto, updatedMember);

        NodeModel nodeModel = new NodeModel();
        nodeModel.convertMemberToNodeModel(updatedMember);
        return nodeModel;
    }

    private void createDepartmentMembers(MemberDto memberDto, Member member) {
        for(long departmentId : memberDto.getDepartmentIds()) {
            Department department  = departmentRepository.findOneById(departmentId);
            DepartmentMember departmentMember = new DepartmentMember();
            departmentMember.setMember(member);
            departmentMember.setDepartment(department);
            departmentMemberRepository.save(departmentMember);
        }
    }

    @Override
    @Transactional
    public void deleteMember(long memberId) {
        departmentMemberRepository.deleteByMemberId(memberId);
        memberRepository.deleteById(memberId);
    }

    @Override
    public void deleteAllMember() {
        memberRepository.deleteAll();
    }
}
