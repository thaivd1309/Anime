package com.thai.anime.controller;

import com.thai.anime.animeobj.Anime;
import com.thai.anime.animeobj.AnimeOverview;
import com.thai.anime.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/anime")
public class AnimeController {
    private final AnimeService animeService;

    @Autowired
    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @GetMapping("/search")
    @ModelAttribute
    public ResponseEntity<List<AnimeOverview>> getSearched(@RequestParam String query) throws IOException {
        return ResponseEntity.ok().body(animeService.getSearchedOverviews(query));
    }

    @PostMapping("/favourite")
    public void saveToFavourite(@RequestParam Long id) throws IOException {
        animeService.saveToFavourite(id);
    }

    @PostMapping("/watchLater")
    public void saveToWatchLater(@RequestParam Long id) throws IOException {
        animeService.saveToWatchLater(id);
    }

    @GetMapping("/favourite")
    public ResponseEntity<List<AnimeOverview>> getAllFavourite(@RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok().body(animeService.getAllFavourite(page));
    }

    @GetMapping("/watchLater")
    public ResponseEntity<List<AnimeOverview>> getAllWatchLater(@RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok().body(animeService.getAllFavourite(page));
    }

    @DeleteMapping("/favourite/{mal_id}")
    public void deleteFromFavourite(@PathVariable("mal_id") Long mal_id) {
        animeService.deleteFromFavourite(mal_id);
    }

    @DeleteMapping("/watchlater/{mal_id}")
    public void deleteFromWatchLater(@PathVariable("mal_id") Long mal_id) {
        animeService.deleteFromWatchLater(mal_id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anime> getAnime(@PathVariable("id") Long id) throws IOException {
        return ResponseEntity.ok().body(animeService.getAnimeById(id));
    }

}
