package com.codit.blog.service.user;

import com.codit.blog.domain.dto.userDto.UserLoginRequestDto;
import com.codit.blog.domain.dto.userDto.UserLoginResponse;
import com.codit.blog.domain.dto.userDto.UserRequestDto;

public interface UserService {

    public void create(UserRequestDto userRequestDto);

    public UserLoginResponse login(UserLoginRequestDto userLoginRequestDto);



}
