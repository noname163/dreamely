package com.nash.assignment.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AccountRespone {
    private String email;
    private String phoneNumber;
    private String description;
    private String fullName;
    private String username;
}
