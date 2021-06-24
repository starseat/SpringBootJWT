package com.boot.jwt.springbootjwt.dto;

import lombok.*;

/**
 * Token 정보 Response 용도
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    private String token;
}
