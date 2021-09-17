package com.thai.anime.controller;

import com.thai.anime.animeobj.Anime;
import com.thai.anime.animeobj.AnimeOverview;
import com.thai.anime.animeobj.Genre;
import com.thai.anime.animeobj.Studios;
import com.thai.anime.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return "home";
    }

    @GetMapping("/anime/{id}")
    public String getAnimeDetail(@PathVariable Long id, Model model) throws IOException {
        Anime anime = animeService.getAnimeById(id);
        model.addAttribute("anime", anime);
        model.addAttribute("isInFavourite", animeService.isInFavourite(id));
        model.addAttribute("isInWatchLater", animeService.isInWatchLater(id));
        String genres = anime.getGenres().stream().map(Genre::getName).collect(Collectors.joining(", "));
        String studios = anime.getStudios().stream().map(Studios::getName).collect(Collectors.joining(", "));
        model.addAttribute("animeGenres", genres);
        model.addAttribute("animeStudios", studios);
        return "detail";
    }

    @PostMapping("/favourite")
    public String saveToFavourite(@RequestBody Anime anime, RedirectAttributes redirectAttributes) {
        System.out.println(anime);
        try {
            animeService.saveToFavourite(anime);
            redirectAttributes.addFlashAttribute("success", anime.getTitle() + " has been saved to Favourite.");
        }
        catch (IllegalStateException exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return "redirect:/anime/" + anime.getMal_id().toString();
    }

    @PostMapping("/watchlater")
    public String saveToWatchLater(Anime anime, RedirectAttributes redirectAttributes) {
        try {
            animeService.saveToWatchLater(anime);
            redirectAttributes.addFlashAttribute("success", anime.getTitle() + " has been saved to Favourite.");
        }
        catch (IllegalStateException exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return "redirect:/anime/" + anime.getMal_id().toString();
    }

//    @GetMapping("/test")
//    public String test(Model model) {
//        Long id = new Long(5);
//        model.addAttribute("id", id);
//        return "home";
//    }
}
