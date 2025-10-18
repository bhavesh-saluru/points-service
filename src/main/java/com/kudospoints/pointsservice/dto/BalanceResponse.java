package com.kudospoints.pointsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class BalanceResponse {
    private UUID memberId;
    private int balance;
}
