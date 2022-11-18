package com.credit.webcredit.repository;

import com.credit.webcredit.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends JpaRepository<Users, Long> {
    @Query("SELECT u FROM Users u WHERE u.phone = :phone")
    Users getUserByPhone(@Param("phone") String phone);

    @Query("SELECT u.email FROM Users u WHERE u.email = :email")
    String sendEmailPassword(@Param("email")String email);
}
