package com.mds.system.littleshop.repository;

import com.mds.system.littleshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Boolean existsByUserName(String userName);

    Optional<User> findByUserName(String userName);
}
