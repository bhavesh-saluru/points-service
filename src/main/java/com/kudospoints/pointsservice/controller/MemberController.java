package com.kudospoints.pointsservice.controller;

import com.kudospoints.pointsservice.domain.Member;
import com.kudospoints.pointsservice.domain.PointsLedger;
import com.kudospoints.pointsservice.dto.*;
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

    @PostMapping("/{memberId}/transactions")
    public ResponseEntity<PointsLedgerResponse> addPoints(@PathVariable UUID memberId,
                                                          @RequestBody AddPointsRequest request) {
        PointsLedger newLedgerEntry = memberService.addPointsTransaction(memberId, request);
        PointsLedgerResponse response = mapToLedgerResponse(newLedgerEntry);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private PointsLedgerResponse mapToLedgerResponse(PointsLedger ledgerEntry) {
        PointsLedgerResponse response = new PointsLedgerResponse();
        response.setId(ledgerEntry.getId());
        response.setMemberId(ledgerEntry.getMember().getId()); // Note how we get the member ID
        response.setPoints(ledgerEntry.getPoints());
        response.setType(ledgerEntry.getType());
        response.setTransactionId(ledgerEntry.getTransactionId());
        response.setNotes(ledgerEntry.getNotes());
        response.setCreatedAt(ledgerEntry.getCreatedAt());
        return response;
    }

    @GetMapping("/{memberId}/balance")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable UUID memberId) {
        Integer balance = memberService.getMemberBalance(memberId);
        BalanceResponse response = new BalanceResponse(memberId, balance);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{memberId}/transactions")
    public ResponseEntity<List<PointsLedgerResponse>> getTransactionHistory(@PathVariable UUID memberId) {
        List<PointsLedger> history = memberService.getTransactionHistoryForMember(memberId);
        List<PointsLedgerResponse> response = history.stream()
                .map(this::mapToLedgerResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
}
