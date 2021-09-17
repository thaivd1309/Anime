package com.thai.anime.repo;

import com.thai.anime.animeobj.Studios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioRepo extends JpaRepository<Studios, Long> {
}
