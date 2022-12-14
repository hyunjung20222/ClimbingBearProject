package com.example.climbingBear.domain.user.service;


import com.example.climbingBear.domain.user.dto.*;
import com.example.climbingBear.domain.user.entity.User;
import com.example.climbingBear.domain.user.exception.ExistSameIdException;
import com.example.climbingBear.domain.user.exception.ExistSameNickException;
import com.example.climbingBear.domain.user.exception.NoExistUserException;
import com.example.climbingBear.domain.user.repository.UserRepository;
import com.example.climbingBear.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    // 회원가입
    public SignupResDto signup (SignupReqDto dto) throws Exception {

        isExistResDto checkedId;
        checkedId = checkId(dto.getId());
        isExistResDto checkedNickname;
        checkedNickname = checkNickname(dto.getNickname());

        if (checkedId.getIsExist()){
            throw new ExistSameIdException();
        }
        if (checkedNickname.getIsExist()){
            throw new ExistSameNickException();
        }

        User user = dto.toUserEntity();
        String accessToken = jwtProvider.getAccessToken(user.getUserSeq());
        String refreshToken = jwtProvider.getRefreshToken();
        user.updateRefreshToken(refreshToken);
        userRepository.save(user);
        return SignupResDto.of(accessToken, refreshToken);
    }

    // accessToken 조회
    public GetAccessTokenResponseDto getAccessToken(String refreshToken) {
        User user = userRepository.findByRefreshToken(refreshToken).orElseThrow(() ->
                new NoExistUserException());
        return new GetAccessTokenResponseDto(jwtProvider.getAccessToken(user.getUserSeq()));
    }

    // 로그인
    public LoginResDto login(LoginReqDto dto){
        User user = userRepository.findByIdAndPw(dto.getId(), dto.getPw()).orElseThrow(() ->
                new NoExistUserException());
        String refreshToken = jwtProvider.getRefreshToken();
        user.updateRefreshToken(refreshToken);
        System.out.println(user);
        return new LoginResDto(jwtProvider.getAccessToken(user.getUserSeq()), jwtProvider.getRefreshToken(), user.getNickname());
    }

    // 닉네임 중복 확인
    public isExistResDto checkNickname(String nickname) throws Exception {
        isExistResDto isExist = new isExistResDto();
        if(userRepository.existsUserByNickname(nickname)){
            isExist.setIsExist(true);
        }else{
            isExist.setIsExist(false);
        }
        return isExist;
    }

    // 아이디 중복확인
    public isExistResDto checkId(String id) throws Exception {
        isExistResDto isExist = new isExistResDto();
        if(userRepository.existsUserById(id)){
            isExist.setIsExist(true);
        }else{
            isExist.setIsExist(false);
        }
        return isExist;
    }

    // 사용자 전체 조회
    public List<UserListResDto> findAllUser() {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.ASC, "userSeq"));
        return users.stream().map(UserListResDto::new).collect(Collectors.toList());
    }
}
