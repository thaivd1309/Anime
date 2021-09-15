package com.thai.anime.service;

import com.thai.anime.animeobj.Anime;
import com.thai.anime.animeobj.AnimeOverview;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface AnimeService {
    //favourite
    void saveToFavourite(Anime anime);
    List<AnimeOverview> getAllFavourite(int page);
    void deleteFromFavourite(Long id);
    //watch later
    void saveToWatchLater(Anime anime);
    List<AnimeOverview> getAllWatchLater(int page);
    void deleteFromWatchLater(Long id);
    //search
    Anime getAnimeById(Long id) throws IOException;
    List<AnimeOverview> getSearchedOverviews(String query) throws IOException;
}
