package com.thai.anime.controller;

import com.thai.anime.animeobj.*;
import com.thai.anime.form.LoginForm;
import com.thai.anime.form.RegisterForm;
import com.thai.anime.service.AnimeService;
import com.thai.anime.service.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class MainController {
    private final AnimeService animeService;
    private final WebUserService webUserService;

    @Autowired
    public MainController(AnimeService animeService, WebUserService webUserService) {
        this.animeService = animeService;
        this.webUserService = webUserService;
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
        return "detail";
    }

    @PostMapping("/favourite")
    public String saveToFavourite(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            animeService.saveToFavourite(id);
            redirectAttributes.addFlashAttribute("success","Anime has been saved to Favourite.");
        }
        catch (IllegalStateException | IOException exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return "redirect:/anime/" + id;
    }

    @PostMapping("/watchlater")
    public String saveToWatchLater(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            animeService.saveToWatchLater(id);
            redirectAttributes.addFlashAttribute("success","Anime has been saved to Watch Later.");
        }
        catch (IllegalStateException | IOException exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return "redirect:/anime/" + id;
    }

    @GetMapping("/favourite")
    public String getFavourite(Model model, @RequestParam(defaultValue = "1") int page) {
        List<AnimeOverview> animes = animeService.getAllFavourite(page-1);
        model.addAttribute("animes", animes);
        int pageNum = (int)Math.ceil((double) animeService.countFavourite() / 10);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("page", page);
        return "favourite";
    }

    @GetMapping("/watchlater")
    public String getWatchLater(Model model, @RequestParam(defaultValue = "1") int page) {
        List<AnimeOverview> animes = animeService.getAllWatchLater(page-1);
        model.addAttribute("animes", animes);
        int pageNum = (int)Math.ceil((double) animeService.countWatchLater() / 10);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("page", page);
        return "watchlater";
    }

    @PostMapping("/favourite/delete")
    public String deleteFavourite(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        System.out.println("DELETE CALLED.");
        try {
            animeService.deleteFromFavourite(id);
            redirectAttributes.addFlashAttribute("success", "Anime deleted from Favourite.");
        }
        catch (IllegalStateException exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return "redirect:/anime/" + id;
    }

    @PostMapping("/watchlater/delete")
    public String deleteWatchLater(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            animeService.deleteFromWatchLater(id);
            redirectAttributes.addFlashAttribute("success", "Anime deleted from WatchLater.");
        }
        catch (IllegalStateException exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return "redirect:/anime/" + id;
    }

    @GetMapping("/login")
    public String loginPage(@ModelAttribute LoginForm loginForm,
                              Model model,
                              @RequestParam(required = false) boolean error) {
        if (isAuthenticated()) {
            return "redirect:/";
        }
        if (error) {
            model.addAttribute("errorMsg", "Login credentials are not correct.");
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(@ModelAttribute RegisterForm registerForm) {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute RegisterForm registerForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        if(!registerForm.getPassword().equals(registerForm.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("error", "Password and Confirm password do not match.");
            return "redirect:/register";
        }
        WebUser webUser = new WebUser(registerForm.getName(), registerForm.getEmail(), registerForm.getPassword());
        try {
            webUserService.saveUser(webUser);
            redirectAttributes.addFlashAttribute("success", "Registered successfully.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
