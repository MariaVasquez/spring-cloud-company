package com.debugideas.auth_server.services.impl;

import com.debugideas.auth_server.dtos.TokenDto;
import com.debugideas.auth_server.dtos.UserInfoDto;
import com.debugideas.auth_server.entities.UserInfo;
import com.debugideas.auth_server.helpers.JwtHelper;
import com.debugideas.auth_server.repositories.UserInfoRepository;
import com.debugideas.auth_server.services.UserInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtHelper jwtHelper;

    private static final String USER_EXCEPTION_MSG = "Error to authentication user";

    @Override
    public TokenDto login(UserInfoDto userInfoDto) {
        log.info("Init login: {}", userInfoDto.getUsername());
        UserInfo userFromDB = userInfoRepository.getByUsername(userInfoDto.getUsername())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, USER_EXCEPTION_MSG));

        validPassword(userInfoDto, userFromDB);

        return TokenDto
                .builder()
                .accessToken(jwtHelper.createToken(userFromDB.getUsername()))
                .build();
    }

    @Override
    public TokenDto validToken(TokenDto tokenDto) {
        log.info("Validate token: {}", tokenDto.getAccessToken());
        if(jwtHelper.validateToken(tokenDto.getAccessToken())){
            return TokenDto.builder().accessToken(tokenDto.getAccessToken()).build();
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, USER_EXCEPTION_MSG);
    }

    private void validPassword(UserInfoDto userInfoDto, UserInfo info){
        if (!passwordEncoder.matches(userInfoDto.getPassword(), info.getPassword())){
            log.error("Error validate password: {}", userInfoDto.getUsername());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, USER_EXCEPTION_MSG);
        }
    }
}
