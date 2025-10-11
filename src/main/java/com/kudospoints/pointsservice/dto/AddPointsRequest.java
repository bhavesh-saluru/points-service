package com.kudospoints.pointsservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddPointsRequest {
    private int points; // Can be positive (earn) or negative (redeem)
    private String type; // e.g., "EARN_PROMOTION", "REDEEM_VOUCHER"
    private String transactionId;
    private String notes;
}
