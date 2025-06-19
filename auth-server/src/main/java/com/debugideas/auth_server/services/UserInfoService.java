package com.debugideas.auth_server.services;

import com.debugideas.auth_server.dtos.TokenDto;
import com.debugideas.auth_server.dtos.UserInfoDto;
public interface UserInfoService {

    TokenDto login(UserInfoDto userInfoDto);
    TokenDto validToken(TokenDto tokenDto);
}
