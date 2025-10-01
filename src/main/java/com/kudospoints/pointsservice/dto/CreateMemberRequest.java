package com.kudospoints.pointsservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMemberRequest {
    private String name;
    private String email;
}
