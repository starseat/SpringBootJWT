package com.boot.jwt.springbootjwt.repository;

import com.boot.jwt.springbootjwt.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {

}
