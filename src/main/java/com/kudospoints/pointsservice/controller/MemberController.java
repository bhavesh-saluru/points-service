package com.kudospoints.pointsservice.controller;

import com.kudospoints.pointsservice.domain.Member;
import com.kudospoints.pointsservice.dto.CreateMemberRequest;
import com.kudospoints.pointsservice.dto.MemberResponse;
import com.kudospoints.pointsservice.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<MemberResponse> createMember(@RequestBody CreateMemberRequest request) {
        Member newMember = memberService.createMember(request.getName(), request.getEmail());
        MemberResponse response = mapToMemberResponse(newMember);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getAllMembers() {
        List<Member> membersList = memberService.getAllMembers();
        List<MemberResponse> responseList = membersList.stream()
                .map(this::mapToMemberResponse)
                .toList(); // or .collect(Collectors.toList()) in older Java
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMemberById(@PathVariable UUID id) {
        Member member = memberService.getMemberById(id);
        MemberResponse response = mapToMemberResponse(member);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Helper method to map from our domain object to our response DTO
    private MemberResponse mapToMemberResponse(Member member) {
        MemberResponse response = new MemberResponse();
        response.setId(member.getId());
        response.setName(member.getName());
        response.setEmail(member.getEmail());
        response.setStatus(member.getStatus());
        response.setCreatedAt(member.getCreatedAt());
        return response;
    }
}
