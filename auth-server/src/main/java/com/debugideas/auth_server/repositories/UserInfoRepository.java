package com.debugideas.auth_server.repositories;

import com.debugideas.auth_server.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> getByUsername(String username);
}
