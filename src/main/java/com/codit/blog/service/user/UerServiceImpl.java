package com.codit.blog.service.user;

import com.codit.blog.domain.dto.userDto.UserLoginRequestDto;
import com.codit.blog.domain.dto.userDto.UserLoginResponse;
import com.codit.blog.domain.dto.userDto.UserRequestDto;
import com.codit.blog.domain.entity.User;
import com.codit.blog.domain.mapper.UserMapper;
import com.codit.blog.jwt.JwtUtil;
import com.codit.blog.repository.UserRepository;
import com.codit.blog.util.ImageValidator;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UerServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private static Boolean check = Boolean.TRUE;

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
    public UserLoginResponse login(UserLoginRequestDto userLoginRequestDto) {
        User user = userRepository.findById(userLoginRequestDto.uuid())
                .orElseThrow(() -> new IllegalArgumentException("없는 회원 입니다. 확인 부탁드립니다"));

        if(!BCrypt.checkpw(userLoginRequestDto.password(), user.getPassword())){
            check = false;
            return UserMapper.toUserLoginResponse(check, null);
        }
        String generateToken = jwtUtil.generateToken(user.getId().toString());
        return UserMapper.toUserLoginResponse(check, generateToken);
    }

    @Override
    public void update(UserRequestDto userRequestDto) {

    }

    @Override
    public void delete(UserRequestDto userRequestDto) {

    }
}
