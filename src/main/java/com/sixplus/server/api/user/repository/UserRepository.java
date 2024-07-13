package com.sixplus.server.api.user.repository;

import com.sixplus.server.api.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
//    TB_USER findByUserId(String userId);
//    TB_USER findByUserIdAndUserPw(String userId, String userPw);
//    TB_USER findByUserEmail(String userEmail);
//    TB_USER findByUserPhone(String userPhone);
//    TB_USER findByUserEmailAndUserPhone(String userEmail, String userPhone);
//    TB_USER findByUserEmailAndUserPw(String userEmail, String userPw);
//    TB_USER findByUserPhoneAndUserPw(String userPhone, String userPw);
//    TB_USER findByUserIdAndUserEmail(String userId, String userEmail);
//    TB_USER findByUserIdAndUserPhone(String userId, String userPhone);
//    TB_USER findByUserIdAndUserPwAndUserEmail(String userId, String userPw, String userEmail);
//    TB_USER findByUserIdAndUserPwAndUserPhone(String userId, String userPw, String userPhone);
//    TB_USER findByUserEmailAndUserPwAndUserPhone(String userEmail, String userPw, String userPhone);
//    TB_USER findByUserIdAndUserEmailAndUserPhone(String userId, String userEmail, String userPhone);
//    TB_USER findByUserIdAndUserPwAndUserEmailAndUserPhone(String userId, String userPw, String userEmail, String userPhone);
}



