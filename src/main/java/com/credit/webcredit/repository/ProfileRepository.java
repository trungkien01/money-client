package com.credit.webcredit.repository;

import com.credit.webcredit.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("select e from Profile e where e.userId = :id ")
    Profile getImgUser(Long id);
}
