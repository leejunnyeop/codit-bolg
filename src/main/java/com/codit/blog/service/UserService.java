package com.codit.blog.service;

import com.codit.blog.domain.dto.UserLoginRequestDto;
import com.codit.blog.domain.dto.UserLoginResponse;
import com.codit.blog.domain.dto.UserRequestDto;

public interface UserService {

    public void create(UserRequestDto userRequestDto);

    public void find(UserRequestDto userRequestDto);

    public UserLoginResponse login(UserLoginRequestDto userLoginRequestDto);

    public void update(UserRequestDto userRequestDto);

    public void delete(UserRequestDto userRequestDto);


}
