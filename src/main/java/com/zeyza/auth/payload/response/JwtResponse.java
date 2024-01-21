package com.zeyza.auth.payload.response;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String message;
}
