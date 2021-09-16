package com.thai.anime.controller;

import com.thai.anime.animeobj.Anime;
import com.thai.anime.animeobj.AnimeOverview;
import com.thai.anime.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {
    private final AnimeService animeService;

    @Autowired
    public MainController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @GetMapping("/")
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/search")
    public String getSearchResults(@RequestParam String query, Model model) throws IOException {
        List<AnimeOverview> animeOverviews = animeService.getSearchedOverviews(query);
        model.addAttribute("searchResults", animeOverviews);
//        System.out.println(animeOverviews.stream().map(AnimeOverview::getId).collect(Collectors.toList()));
        return "home";
    }

    @GetMapping("/anime/{id}")
    public String getAnimeDetail(@PathVariable Long id, Model model) throws IOException {
        Anime anime = animeService.getAnimeById(id);
        model.addAttribute("anime", anime);
        model.addAttribute("isInFavourite", animeService.isInFavourite(id));
        model.addAttribute("isInWatchLater", animeService.isInWatchLater(id));
        return "detail";
    }

//    @GetMapping("/test")
//    public String test(Model model) {
//        Long id = new Long(5);
//        model.addAttribute("id", id);
//        return "home";
//    }
}
