package com.codit.blog.service;

import com.codit.blog.domain.dto.UserRequestDto;

public interface UserService {

    public void create(UserRequestDto userRequestDto);

    public void find(UserRequestDto userRequestDto);

    public void login(UserRequestDto userRequestDto);

    public void update(UserRequestDto userRequestDto);

    public void delete(UserRequestDto userRequestDto);


}
