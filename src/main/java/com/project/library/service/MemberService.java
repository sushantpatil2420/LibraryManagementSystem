package com.project.library.service;

import com.project.library.entity.Member;
import com.project.library.exception.ResourceNotFoundException;
import com.project.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Member not found"));
    }

    // UPDATE MEMBER
    public Member updateMember(Long memberId, Member updatedMember) {
        Member existingMember = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Member not found"));

        existingMember.setMemberName(updatedMember.getMemberName());
        existingMember.setMemberEmail(updatedMember.getMemberEmail());
        existingMember.setMemberPhone(updatedMember.getMemberPhone());
        existingMember.setMembershipDate(updatedMember.getMembershipDate());

        return memberRepository.save(existingMember);

    }

    //  DELETE MEMBER
    public void deleteMember(Long memberId) {
        if (!memberRepository.existsById(memberId)){
            throw new ResourceNotFoundException("Member not found");
        }

        memberRepository.deleteById(memberId);
    }
}
