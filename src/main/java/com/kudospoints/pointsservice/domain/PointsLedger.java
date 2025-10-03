package com.kudospoints.pointsservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "points_ledger")
public class PointsLedger {

    @Id
    private UUID id;

    @Column(nullable = false)
    private Integer points;

    @Column(nullable = false)
    private String type;

    @Column(name = "transaction_id", unique = true)
    private String transactionId;

    private String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    // This is the link back to the Member
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
