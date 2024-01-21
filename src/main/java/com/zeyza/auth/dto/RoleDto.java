package com.zeyza.auth.dto;

import com.zeyza.auth.enums.ERole;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleDto {
    private ERole name;
}