package com.thai.anime.service;

import com.thai.anime.animeobj.Anime;
import com.thai.anime.animeobj.AnimeOverview;
import com.thai.anime.repo.AnimeRepo;
import com.thai.anime.repo.GenreRepo;
import com.thai.anime.repo.StudioRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AnimeServiceImplTest {

    AnimeService animeService;

    @Autowired
    AnimeRepo animeRepo;
    @Autowired
    GenreRepo genreRepo;
    @Autowired
    StudioRepo studioRepo;

    SendHttpGETRequest sendHttpGETRequest;

    @BeforeEach
    void init() {
        sendHttpGETRequest = new SendHttpGETRequest();
        animeService = new AnimeServiceImpl(animeRepo, sendHttpGETRequest, genreRepo, studioRepo);
    }

    @Test
    void getSearchedOverviews() throws IOException {
        List<AnimeOverview> list = animeService.getSearchedOverviews("fate");
        Iterator<AnimeOverview> iterator = list.iterator();
        AnimeOverview overview = (AnimeOverview) iterator.next();
        assertEquals(10087, overview.getId());
    }

    @Test
    void getAllFavourite() throws IOException {
        animeService.saveToFavourite(new Long(1));
        Anime expected = animeService.getAnimeById(new Long(1));
        List<AnimeOverview> favourite = animeService.getAllFavourite(0);
        assert favourite.size() == 1;
        AnimeOverview actual = favourite.iterator().next();
        assertEquals(expected.getMal_id(), actual.getId());
    }

    @Test
    void deleteFromWatchLater() throws IOException {
        animeService.saveToWatchLater(new Long(1));
        animeService.deleteFromWatchLater(new Long(1));
        assert animeRepo.count() == 0;
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            animeService.deleteFromWatchLater(new Long(1));
        });
        assertTrue(exception.getMessage().contains("Anime is not in watch later."));

    }
}