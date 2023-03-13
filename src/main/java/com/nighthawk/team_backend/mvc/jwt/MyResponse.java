package com.nighthawk.team_backend.mvc.jwt;

import org.springframework.http.ResponseCookie;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data // getters and setters
@AllArgsConstructor
public class MyResponse {
    private Long data;
    private ResponseCookie cookie;
}
