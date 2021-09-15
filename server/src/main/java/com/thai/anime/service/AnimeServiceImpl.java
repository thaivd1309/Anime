package com.thai.anime.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.thai.anime.animeobj.Anime;
import com.thai.anime.animeobj.AnimeOverview;
import com.thai.anime.repo.AnimeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnimeServiceImpl implements AnimeService {
    private final AnimeRepo animeRepo;
    private final SendHttpGETRequest sendHttpGETRequest;
    private final Gson gson = new Gson();
    String searchUrl = "https://api.jikan.moe/v3/search/anime";
    String animeUrl = "https://api.jikan.moe/v3/anime";

    @Autowired
    public AnimeServiceImpl(AnimeRepo animeRepo, SendHttpGETRequest sendHttpGETRequest) {
        this.animeRepo = animeRepo;
        this.sendHttpGETRequest = sendHttpGETRequest;
    }

    @Override
    public void saveToFavourite(Anime anime) {
        Optional<Anime> exist = animeRepo.findBySaveToAndTitle("FAVOURITE", anime.getTitle());
        if (exist.isPresent()) {
            throw new IllegalStateException("Anime already in favourites.");
        }
        anime.setSaveTo("FAVOURITE");
        animeRepo.save(anime);
    }

    @Override
    public List<AnimeOverview> getAllFavourite(int pageNumber) {
        Pageable page = PageRequest.of(pageNumber, 10);
        List<Anime> animes = animeRepo.findAllBySaveTo("FAVOURITE", page);
         return animes.stream().map(Anime::getOverview).collect(Collectors.toList());
    }

    @Override
    public void deleteFromFavourite(Long id) {
        Optional<Anime> exist = animeRepo.findBySaveToAndMal_id("FAVOURITE", id);
        if (exist.isEmpty()) {
            throw new IllegalStateException("Anime is not in favourites.");
        }
        animeRepo.delete(exist.get());
    }

    @Override
    public void saveToWatchLater(Anime anime) {
        Optional<Anime> exist = animeRepo.findBySaveToAndTitle("WATCH LATER", anime.getTitle());
        if (exist.isPresent()) {
            throw new IllegalStateException("Anime already in watch later.");
        }
        anime.setSaveTo("WATCH LATER");
        animeRepo.save(anime);
    }

    @Override
    public List<AnimeOverview> getAllWatchLater(int pageNumber) {
        Pageable page = PageRequest.of(pageNumber, 10);
        List<Anime> animes = animeRepo.findAllBySaveTo("WATCH LATER", page);
        return animes.stream().map(Anime::getOverview).collect(Collectors.toList());
    }

    @Override
    public void deleteFromWatchLater(Long id) {
        Optional<Anime> exist = animeRepo.findBySaveToAndMal_id("WATCH LATER", id);
        if (exist.isEmpty()) {
            throw new IllegalStateException("Anime is not in watch later.");
        }
        animeRepo.delete(exist.get());
    }

    @Override
    public Anime getAnimeById(Long id) throws IOException {
        String json = sendHttpGETRequest.send(animeUrl + "/" + id.toString());
        return gson.fromJson(json, Anime.class);
    }

    @Override
    public List<AnimeOverview> getSearchedOverviews(String query) throws IOException {
        String jsonString = sendHttpGETRequest.send(searchUrl + "?q=" + URLEncoder.encode(query, StandardCharsets.UTF_8.toString()) + "&limit=15");
        JsonObject json = gson.fromJson(jsonString, JsonObject.class);
        JsonElement body = json.get("results");
        Anime[] listOfAnimes = gson.fromJson(body, Anime[].class);
        List<AnimeOverview> listOfOverviews = new LinkedList<>();
        for (Anime anime : listOfAnimes) {
            listOfOverviews.add(anime.getOverview());
        }
//        System.out.println(listOfOverviews.stream().map(AnimeOverview::getTitle).collect(Collectors.toList()));
        return listOfOverviews;
    }
}
