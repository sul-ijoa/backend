package com.teamB.sulijoa_be.user.repository;

import com.teamB.sulijoa_be.user.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	User findByUserID(String userID);
	boolean existsByUserID(String userID);
	boolean existsByNickname(String nickname);
}