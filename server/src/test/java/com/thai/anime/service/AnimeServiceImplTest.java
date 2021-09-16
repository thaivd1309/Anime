package com.thai.anime.service;

import com.thai.anime.animeobj.Anime;
import com.thai.anime.animeobj.AnimeOverview;
import com.thai.anime.repo.AnimeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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

    SendHttpGETRequest sendHttpGETRequest;

    @BeforeEach
    void init() {
        sendHttpGETRequest = new SendHttpGETRequest();
        animeService = new AnimeServiceImpl(animeRepo, sendHttpGETRequest);
    }

    @Test
    void getSearchedOverviews() throws IOException {
        List<AnimeOverview> list = animeService.getSearchedOverviews("fate");
        Iterator<AnimeOverview> iterator = list.iterator();
        AnimeOverview overview = (AnimeOverview) iterator.next();
        assertEquals(10087, overview.getId());
    }

    @Test
    void getAllFavourite() {
        Anime anime = new Anime(new Long(1),"Fate", new Long(12), null, null, null, null, null, 8.0, null, null, null);
        animeService.saveToFavourite(anime);
        AnimeOverview expected = anime.getOverview();
        List<AnimeOverview> favourite = animeService.getAllFavourite(0);
        assert favourite.size() == 1;
        AnimeOverview actual = favourite.iterator().next();
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    void deleteFromWatchLater() {
        Anime anime = new Anime(new Long(1),"Fate", new Long(12), null, null, null, null, null, 8.0, null, null, null);
        animeService.saveToWatchLater(anime);
        animeService.deleteFromWatchLater(new Long(1));
        assert animeRepo.count() == 0;
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            animeService.deleteFromWatchLater(new Long(1));
        });
        assertTrue(exception.getMessage().contains("Anime is not in watch later."));

    }
}