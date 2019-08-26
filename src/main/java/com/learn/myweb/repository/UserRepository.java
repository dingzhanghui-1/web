package com.learn.myweb.repository;

import com.learn.myweb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findByMobileAndPassword(String mobile, String password);

}
