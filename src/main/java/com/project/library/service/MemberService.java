package com.project.library.service;

import com.project.library.entity.Member;
import com.project.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    // CONSTRUCTOR INJECTION
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // ADD NEW MEMBER
    public Member saveMember(Member member){
        return memberRepository.save(member);
    }

    //  GET ALL MEMBERS
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    //  GET MEMBER BY ID
    public Optional<Member> getMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //  DELETE MEMBER
    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}
