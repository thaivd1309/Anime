package com.thai.anime.repo;

import com.thai.anime.animeobj.WebUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebUserRepository extends JpaRepository<WebUser, Long> {
    WebUser findWebUserByEmail(String email);
}
