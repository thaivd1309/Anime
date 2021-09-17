package com.thai.anime.service;

import com.thai.anime.animeobj.Anime;
import com.thai.anime.animeobj.AnimeOverview;
import com.thai.anime.animeobj.Genre;
import com.thai.anime.animeobj.Studios;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface AnimeService {
    //favourite
    void saveToFavourite(Long mal_id) throws IOException;
    List<AnimeOverview> getAllFavourite(int page);
    void deleteFromFavourite(Long id);
    //watch later
    void saveToWatchLater(Long mal_id) throws IOException;
    List<AnimeOverview> getAllWatchLater(int page);
    void deleteFromWatchLater(Long id);
    //search
    Anime getAnimeById(Long id) throws IOException;
    List<AnimeOverview> getSearchedOverviews(String query) throws IOException;
    //check
    Boolean isInFavourite(Long id);
    Boolean isInWatchLater(Long id);
    //helper
    void saveGenre(Genre genre);
    void saveStudio(Studios studios);
    void addGenre(Anime anime, Long genre_id);
    void addStudio(Anime anime, Long studio_id);
    Anime refreshGenresAndStudios(Anime anime);
}
