package com.thai.anime.repo;

import com.thai.anime.animeobj.Anime;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimeRepo extends JpaRepository<Anime, Long> {

    List<Anime> findAllBySaveTo(String type, Pageable page);
    Optional<Anime> findBySaveToAndTitle(String type, String title);

    @Query("SELECT a FROM Anime a WHERE a.saveTo = :type AND a.mal_id = :mal_id")
    Optional<Anime> findBySaveToAndMal_id(@Param("type") String type, @Param("mal_id") Long mal_id);

//    @Query("DELETE FROM Anime a WHERE a.saveTo = :type AND a.mal_id = :mal_id")
//    void deleteBySaveToAndMal_id(@Param("type") String type, @Param("mal_id") Long mal_id);
}
