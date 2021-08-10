package com.daou.organizations.service.department.impl;

import com.daou.organizations.domain.Department;
import com.daou.organizations.domain.DepartmentMember;
import com.daou.organizations.domain.Member;
import com.daou.organizations.service.department.DepartmentService;
import com.daou.organizations.service.department.dto.DepartmentDto;
import com.daou.organizations.domain.NodeModel;
import com.daou.organizations.repository.DepartmentMemberRepository;
import com.daou.organizations.repository.DepartmentRepository;
import com.daou.organizations.repository.QuerydslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final QuerydslRepository querydslRepository;
    private final DepartmentMemberRepository departmentMemberRepository;

    @Override
    public NodeModel getOrganiztion(String deptCode, boolean deptOnly, String searchType, String searchKeyword) {
        if(StringUtils.isEmpty(searchType) || searchKeyword == null || StringUtils.isEmpty(searchKeyword) || searchKeyword == null) {
            // searchType이나 searchKeyword값이 없으면 아래로 향하는 노드 출력

            return getTopToBottom(deptCode, deptOnly); // (1) 전체, (2) deptOnly, (3) deptCode 조회
        } else {
            return getBottomToTopByKeyword(searchType, searchKeyword); // (4)부서, (5) 부서원 키워드 조회
        }
    }

    public NodeModel getBottomToTopByKeyword(String searchType, String searchKeyword) {
        NodeModel root = new NodeModel();
        List<NodeModel> children = new ArrayList<>();
        if("dept".equals(searchType)) { // (4) 부서 키워드 검색
            children = getBottomToTopByDept(searchKeyword);
        } else { // (5) 부서원 키워드 검색
            children = getBottomToTopByMember(searchKeyword);
        }
        root.setName("searchType=" + searchKeyword + ", searchKeyword" + searchKeyword);
        root.setChildren(children);
        return root;
    }

    public List<NodeModel> getBottomToTopByDept(String searchKeyword){
        List<Department> tailDepartments = querydslRepository.findDepartmentsByKeyword(searchKeyword); // 부서 키워드 조회
        List<NodeModel> tailDepartmentsNodeModels = new ArrayList<>();

        for(Department department: tailDepartments){
            NodeModel departmentNode = convertDepartmentToNodeModel(null,department);
            tailDepartmentsNodeModels.add(departmentNode);
        }
        return tailDepartmentsNodeModels;
    }

    public NodeModel convertDepartmentToNodeModel(NodeModel childTree, Department department){
        NodeModel currentNode = new NodeModel();
        currentNode.convertDepartmentToNodeModel(department);
        if(childTree != null) {
            currentNode.addChildren(childTree);
        }
        if(department.getUpperDepartment() == null){
            return currentNode;
        } else {
            return convertDepartmentToNodeModel(currentNode, department.getUpperDepartment());
        }
    }

    public List<NodeModel> getBottomToTopByMember(String searchKeyword) {

        List<Member> tailMembers = querydslRepository.findMemberByKeyword(searchKeyword); //부서원 키워드 조회

        // todo : tailMembers의 상위 department, member 조회.... (현재는 대상 노드만 출력)
        return tailMembers.stream()
                .map(tailMember -> {
                    NodeModel nodeModel = new NodeModel();
                    nodeModel.convertMemberToNodeModel(tailMember);
                    return nodeModel;
                })
                .collect(Collectors.toList());
    }

    public NodeModel getTopToBottom(String deptCode, boolean deptOnly) {
        Department root;
        if(StringUtils.isEmpty(deptCode) || deptCode == null) {
            // (1) 전체조회
            root = departmentRepository.findOneByRoot(true);
        } else {
            // (3) deptCode 하위 조회
            root = departmentRepository.findOneByCode(deptCode);
        }
        NodeModel organizations = new NodeModel();
        organizations.convertDepartmentToNodeModel(root);
        List<NodeModel> children = deptOnly ? getDepartmentNodes(root, deptOnly) :
                Stream.concat(getDepartmentNodes(root, deptOnly).stream()
                , getMemberNodes(root).stream()).collect(Collectors.toList());
        // (2) deptOnly 조건
        organizations.setChildren(children);

        return organizations;
    }

    public List<NodeModel> getDepartmentNodes(Department parent, boolean deptOnly) {
        List<NodeModel> departmentNodes = new ArrayList<>();
        if(parent.getSubDepartmentsCount() > 0) {
            for(Department subDepartment : parent.getSubDepartments()) {
                NodeModel subDepartmentNodeModel = new NodeModel();
                subDepartmentNodeModel.convertDepartmentToNodeModel(subDepartment);
                List<NodeModel> children =
                        deptOnly ? getDepartmentNodes(subDepartment, deptOnly) :
                        Stream.concat(getDepartmentNodes(subDepartment, deptOnly).stream()
                        , getMemberNodes(subDepartment).stream()).collect(Collectors.toList());
                // (2) deptOnly 조건
                subDepartmentNodeModel.setChildren(children);
                departmentNodes.add(subDepartmentNodeModel);
            }
        }
        return departmentNodes;
    }

    public List<NodeModel> getMemberNodes(Department parent) {
        List<NodeModel> memberNodes = new ArrayList<>();
        if(parent.getSubMembersCount() > 0) {
            for(DepartmentMember subMember : parent.getSubMembers()) {
                NodeModel subMemberNodeModel = new NodeModel();
                subMemberNodeModel.convertMemberToNodeModel(subMember.getMember());
                memberNodes.add(subMemberNodeModel);
            }
        }
        return memberNodes;
    }

    @Override
    public NodeModel createDepartment(DepartmentDto departmentDto) {
        Department department = new Department();
        department.setCode(departmentDto.getCode());
        department.setName(departmentDto.getName());
        department.setRoot(departmentDto.isRoot());
        Department upperDepartment = departmentRepository.findOneById(departmentDto.getUpperDepartmentId());
        if(upperDepartment != null) {
            department.setUpperDepartment(upperDepartment); // 부모가 있으면 부모 객체 set
        }

        Department savedDepartment = departmentRepository.save(department);
        NodeModel currentNode = new NodeModel();
        currentNode.convertDepartmentToNodeModel(savedDepartment);
        return currentNode;
    }

    @Override
    public NodeModel updateDepartment(long departmentId, DepartmentDto departmentDto) {
        Department department = departmentRepository.findOneById(departmentId);
        if(!StringUtils.isEmpty(departmentDto.getCode()) && departmentDto.getCode() != null && department.getCode() != departmentDto.getCode()) {
            department.setCode(departmentDto.getCode());
        }

        if(!StringUtils.isEmpty(departmentDto.getName()) && departmentDto.getName() != null && department.getName() != departmentDto.getName()) {
            department.setName(departmentDto.getName());
        }

        if(!StringUtils.isEmpty(departmentDto.getCode()) && departmentDto.getCode() != null && department.isRoot() != departmentDto.isRoot()) {
            department.setRoot(departmentDto.isRoot());
        }

        if ((department.getUpperDepartment() != null) && department.getUpperDepartment().getId() != departmentDto.getUpperDepartmentId()) {
            Department upperDepartment = departmentRepository.findOneById(departmentDto.getUpperDepartmentId());
            if (upperDepartment != null) {
                department.setUpperDepartment(upperDepartment);
            }
        }


        Department updatedDepartment = departmentRepository.save(department);
        NodeModel currentNode = new NodeModel();
        currentNode.convertDepartmentToNodeModel(updatedDepartment);
        return currentNode;
    }

    @Transactional
    @Override
    public void deleteDepartment(long departmentId) {
        departmentMemberRepository.deleteByDepartmentId(departmentId);
        departmentRepository.deleteById(departmentId);
    }

    @Override
    public void deleteAllDepartment() {
        departmentRepository.deleteAll();
    }
}
