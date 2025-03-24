package com.codit.blog.service;

import com.codit.blog.domain.dto.UserRequestDto;
import com.codit.blog.domain.entity.User;
import com.codit.blog.domain.mapper.UserMapper;
import com.codit.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UerServiceImpl implements UserService{

    private final UserRepository userRepository;


    @Override
    public void create(UserRequestDto userRequestDto){
        if(!userRepository.findByEmail(userRequestDto.email()).isPresent()){
            throw new IllegalArgumentException("존재하는 이메일 입니다. 확인 부탁드립니다");
        }
        String bcryptPassword = BCrypt.hashpw(userRequestDto.password(), BCrypt.gensalt());
        User user = UserMapper.toUser(userRequestDto, bcryptPassword);
        userRepository.save(user);
    }

    @Override
    public void find(UserRequestDto userRequestDto) {

    }

    @Override
    public void login(UserRequestDto userRequestDto) {

    }

    @Override
    public void update(UserRequestDto userRequestDto) {

    }

    @Override
    public void delete(UserRequestDto userRequestDto) {

    }
}
