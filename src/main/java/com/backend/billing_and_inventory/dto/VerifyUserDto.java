package com.backend.billing_and_inventory.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VerifyUserDto {
    private String username;
    private String password;
}
