package com.codit.blog.service.user;

import com.codit.blog.domain.dto.userDto.UserLoginRequestDto;
import com.codit.blog.domain.dto.userDto.UserLoginResponse;
import com.codit.blog.domain.dto.userDto.UserRequestDto;
import com.codit.blog.domain.entity.User;
import com.codit.blog.domain.mapper.UserMapper;
import com.codit.blog.jwt.JwtUtil;
import com.codit.blog.repository.UserRepository;
import com.codit.blog.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UerServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserUtil userUtil;

    @Override
    public void create(UserRequestDto userRequestDto) {
        userUtil.validateEmailNotDuplicate(userRequestDto.email());
        String bcryptPassword = BCrypt.hashpw(userRequestDto.password(), BCrypt.gensalt());
        User user = UserMapper.toUser(userRequestDto, bcryptPassword);
        userRepository.save(user);
    }

    @Override
    public UserLoginResponse login(UserLoginRequestDto userLoginRequestDto) {
        User user = userUtil.getUserOrThrow(userLoginRequestDto.uuid());

        if (!BCrypt.checkpw(userLoginRequestDto.password(), user.getPassword())) {
            return UserMapper.toUserLoginResponse(false, null);
        }

        String generateToken = jwtUtil.generateToken(user.getId().toString());
        return UserMapper.toUserLoginResponse(true, generateToken);
    }
}
