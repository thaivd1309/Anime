package com.thai.anime.controller;

import com.thai.anime.animeobj.AnimeOverview;
import com.thai.anime.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

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


//    @GetMapping("/fragment")
//    public String getNavBar() {
//        return "fragment/navbar";
//    }
}
