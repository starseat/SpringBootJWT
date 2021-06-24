package com.boot.jwt.springbootjwt.service;

import com.boot.jwt.springbootjwt.dto.UserDto;
import com.boot.jwt.springbootjwt.entity.Authority;
import com.boot.jwt.springbootjwt.entity.User;
import com.boot.jwt.springbootjwt.repository.UserRepository;
import com.boot.jwt.springbootjwt.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

/**
 * 회원 가입을 위한 service
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 회원 가입 수행
     * @param userDto
     * @return
     */
    @Transactional
    public User signup(UserDto userDto) {
        // DB 에 사용자 있는지 검사
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        // 권한 정보 생성
        // 빌더 패턴의 장점
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .activated(true)

                .authorities(Collections.singleton(authority))
                .build()
        ;

        return userRepository.save(user);
    }

    /**
     * username 기준으로 정보 획득
     * @param username
     * @return
     */
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String username) {
        return userRepository.findOneWithAuthoritiesByUsername(username);
    }


    /**
     * 현재 SecurityContext에 저장된 username 정보 획득
     * @return
     */
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesFromSecurityContext() {
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }
}
