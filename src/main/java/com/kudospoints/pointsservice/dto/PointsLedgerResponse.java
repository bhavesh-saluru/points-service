package com.kudospoints.pointsservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class PointsLedgerResponse {
    private UUID id;
    private UUID memberId;
    private int points;
    private String type;
    private String transactionId;
    private String notes;
    private OffsetDateTime createdAt;
}
