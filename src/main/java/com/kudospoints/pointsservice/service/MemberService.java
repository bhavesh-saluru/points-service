package com.kudospoints.pointsservice.service;

import com.kudospoints.pointsservice.domain.Member;
import com.kudospoints.pointsservice.repository.MemberRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    // Constructor Injection: Spring will automatically provide the MemberRepository bean.
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
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
}
