package com.codit.blog.controller;

import com.codit.blog.domain.dto.UserRequestDto;
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
    public ResponseEntity<String> login(@RequestBody @Valid UserRequestDto userRequestDto) {
        try {
            userService.login(userRequestDto);
            return ResponseEntity.ok("로그인 성공!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: 아이디 또는 비밀번호를 확인해주세요.");
        }
    }
}
