package com.codit.blog.controller;

import com.codit.blog.domain.dto.userDto.UserLoginRequestDto;
import com.codit.blog.domain.dto.userDto.UserLoginResponse;
import com.codit.blog.domain.dto.userDto.UserRequestDto;
import com.codit.blog.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<String> signup(@RequestBody @Valid UserRequestDto userRequestDto) {
        try {
            userService.create(userRequestDto);
            return ResponseEntity.ok("회원 가입이 완료되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 가입 중 오류가 발생했습니다.");
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequestDto dto) {
        UserLoginResponse response = userService.login(dto);

        if (!response.success()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
