package com.project.library.controller;

import com.project.library.entity.Member;
import com.project.library.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    //ADD NEW MEMBER
    @PostMapping
    public Member saveMember(@Valid @RequestBody Member member) {
        return memberService.saveMember(member);
    }

    //GET ALL MEMBERS
    @GetMapping
    public Page<Member> getAllMembers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return memberService.getAllMembers(pageable);
    }

    //GET MEMBER BY ID
    @GetMapping("/{memberId}")
    public Member getMemberById(@PathVariable Long memberId) {
        return memberService.getMemberById(memberId);
    }

    // UPDATE MEMBER
    @PutMapping("/{memberId}")
    public Member updateMember(@PathVariable Long memberId, @Valid @RequestBody Member member) {
        return memberService.updateMember(memberId, member);
    }

    //DELETE MEMBER
    @DeleteMapping("/{memberId}")
    public void deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
    }
}
