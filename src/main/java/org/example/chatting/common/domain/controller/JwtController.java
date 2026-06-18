package org.example.chatting.common.domain.controller;

import lombok.RequiredArgsConstructor;
import org.example.chatting.common.utils.JwtUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JwtController {

    private final JwtUtil jwtUtil;

    @GetMapping("/api/jwt/{userId}")
    public String generateToke(@PathVariable Long userId) {
        return jwtUtil.generateToken(userId);
    }
}
