package com.codit.blog.domain.dto.userDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDto(@NotBlank(message = "ID는 필수입니다.")
                              @Size(min = 6, max = 30, message = "ID는 6자 이상 30자 이하로 입력해주세요.")
                              String id,

                             @NotBlank(message = "비밀번호는 필수입니다.")
                             @Pattern(
                                     regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*]).+$",
                                     message = "비밀번호는 영문, 숫자, 특수문자(!@#$%^&*)를 각각 최소 1자 이상 포함해야 합니다."
                             )
                              @Size(min = 12, max = 50, message = "비밀번호는 12자 이상 50자 이하로 입력해주세요.")
                              String password,

                             @NotBlank(message = "이메일은 필수입니다.")
                              @Email(message = "이메일 형식이 올바르지 않습니다.")
                              @Size(max = 100, message = "이메일은 100자 이하여야 합니다.")
                              String email,

                             @NotBlank(message = "닉네임은 필수입니다.")
                              @Size(max = 50, message = "닉네임은 50자 이하여야 합니다.")
                              String nickname
) {
}
