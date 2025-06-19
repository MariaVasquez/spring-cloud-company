package com.debugideas.auth_server.controllers;

import com.debugideas.auth_server.dtos.TokenDto;
import com.debugideas.auth_server.dtos.UserInfoDto;
import com.debugideas.auth_server.services.UserInfoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/auth")
@AllArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;

    @PostMapping(path = "login")
    public ResponseEntity<TokenDto> jwtCreate(@RequestBody UserInfoDto userInfoDto){
        return ResponseEntity.ok(userInfoService.login(userInfoDto));
    }

    @PostMapping(path = "jwt")
    public ResponseEntity<TokenDto> jwtValidate(@RequestHeader String accessToken){
        return ResponseEntity.ok(userInfoService.validToken(TokenDto.builder().accessToken(accessToken).build()));
    }
}
