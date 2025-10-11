package com.kudospoints.pointsservice.service;

import com.kudospoints.pointsservice.domain.Member;
import com.kudospoints.pointsservice.domain.PointsLedger;
import com.kudospoints.pointsservice.dto.AddPointsRequest;
import com.kudospoints.pointsservice.repository.MemberRepository;
import com.kudospoints.pointsservice.repository.PointsLedgerRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PointsLedgerRepository pointsLedgerRepository;

    // Constructor Injection: Spring will automatically provide the MemberRepository bean.
    public MemberService(MemberRepository memberRepository, PointsLedgerRepository pointsLedgerRepository) {
        this.memberRepository = memberRepository;
        this.pointsLedgerRepository = pointsLedgerRepository;
    }

    public Member createMember(String name, String email) {
        // Here is our business logic
        Member newMember = new Member();
        newMember.setId(UUID.randomUUID());
        newMember.setName(name);
        newMember.setEmail(email);
        newMember.setStatus("ACTIVE");
        newMember.setCreatedAt(OffsetDateTime.now());

        // We ask the repository to persist the new member
        return memberRepository.save(newMember);
    }

    public Member getMemberById(UUID id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with ID: " + id));
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
    // Calling findAll() would try to load all 10 million records from the database into your application's memory.
    // This would, without a doubt, crash the application with an OutOfMemoryError.
    // The solution is pagination. Instead of asking for "all members," the client should ask for a specific "page"
    // of results (e.g., "give me the first 20 members," then "give me the next 20 members," and so on).
    // Spring Data JPA has incredible built-in support for this with the Pageable interface.

    public PointsLedger addPointsTransaction(UUID memberId, AddPointsRequest request) {
        // 1. Existence Check (reuse our existing method!)
        Member member = getMemberById(memberId);

        // 2. Create and populate the new ledger entry
        PointsLedger newTransaction = new PointsLedger();
        newTransaction.setId(UUID.randomUUID());
        newTransaction.setPoints(request.getPoints());
        newTransaction.setType(request.getType());
        newTransaction.setTransactionId(request.getTransactionId());
        newTransaction.setNotes(request.getNotes());
        newTransaction.setCreatedAt(OffsetDateTime.now());

        // 3. Link it to the owner
        newTransaction.setMember(member);

        // 4. Persist and return the new entry
        return pointsLedgerRepository.save(newTransaction);
    }
}
