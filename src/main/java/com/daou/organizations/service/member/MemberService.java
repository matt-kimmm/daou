package com.daou.organizations.service.member;

import com.daou.organizations.domain.NodeModel;
import com.daou.organizations.service.member.dto.MemberDto;

public interface MemberService {

    NodeModel createMember(MemberDto memberDto);
    NodeModel updateMember(long memberId, MemberDto memberDto);
    void deleteMember(long memberId);
    void deleteAllMember();
}
