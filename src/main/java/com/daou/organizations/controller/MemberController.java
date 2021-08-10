package com.daou.organizations.controller;

import com.daou.organizations.domain.Member;
import com.daou.organizations.domain.NodeModel;
import com.daou.organizations.service.member.dto.MemberDto;
import com.daou.organizations.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/org/member")
public class MemberController { // 3. 부서원 관리

    private final MemberService memberService;

    @PostMapping // (1) 부서원 추가
    public NodeModel createMember(@RequestBody MemberDto memberDto) {
        return memberService.createMember(memberDto);
    }

    @PutMapping("/{memberId}") // (2) 부서원 수정
    public NodeModel updateMember(@PathVariable long memberId, @RequestBody MemberDto memberDto) {
        return memberService.updateMember(memberId, memberDto);
    }

    @DeleteMapping("/{memberId}") // (3) 부서원 삭제
    public void deleteMember(@PathVariable long memberId) {
        memberService.deleteMember(memberId);
    }
}
