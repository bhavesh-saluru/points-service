package com.kudospoints.pointsservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class MemberResponse {
    private UUID id;
    private String name;
    private String email;
    private String status;
    private OffsetDateTime createdAt;
}
