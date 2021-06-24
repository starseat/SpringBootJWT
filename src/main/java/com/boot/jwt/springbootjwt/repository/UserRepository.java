package com.boot.jwt.springbootjwt.repository;

import com.boot.jwt.springbootjwt.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * username 기준으로 User 정보를 가져올때 권한 정보도 같이 가져옴
     * @param username
     * @return
     */
    // @EntityGraph 는 쿼리 수행시 Lazy(지연 로딩) 조회가 아닌 Eager(즉시 로딩) 조회로 jwt_authorities 정보를 같이 가져옴.
    @EntityGraph(attributePaths = "jwt_authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String username);
}
