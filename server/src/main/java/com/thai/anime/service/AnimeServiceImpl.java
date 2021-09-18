package com.thai.anime.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.thai.anime.animeobj.Anime;
import com.thai.anime.animeobj.AnimeOverview;
import com.thai.anime.animeobj.Genre;
import com.thai.anime.animeobj.Studios;
import com.thai.anime.repo.AnimeRepo;
import com.thai.anime.repo.GenreRepo;
import com.thai.anime.repo.StudioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AnimeServiceImpl implements AnimeService {
    private final AnimeRepo animeRepo;
    private final SendHttpGETRequest sendHttpGETRequest;
    private final GenreRepo genreRepo;
    private final StudioRepo studioRepo;
    private final Gson gson = new Gson();
    String searchUrl = "https://api.jikan.moe/v3/search/anime";
    String animeUrl = "https://api.jikan.moe/v3/anime";

    @Autowired
    public AnimeServiceImpl(AnimeRepo animeRepo, SendHttpGETRequest sendHttpGETRequest, GenreRepo genreRepo, StudioRepo studioRepo) {
        this.animeRepo = animeRepo;
        this.sendHttpGETRequest = sendHttpGETRequest;
        this.genreRepo = genreRepo;
        this.studioRepo = studioRepo;
    }

    @Override
    public void saveToFavourite(Long mal_id) throws IOException {
        Optional<Anime> exist = animeRepo.findBySaveToAndMal_id("FAVOURITE", mal_id);
        if (exist.isPresent()) {
            throw new IllegalStateException("Anime already in favourites.");
        }
        Anime anime = refreshGenresAndStudios(getAnimeById(mal_id));
        anime.setSaveTo("FAVOURITE");
        animeRepo.save(anime);
    }

    @Override
    public Anime refreshGenresAndStudios(Anime anime) {
        Collection<Genre> genres = anime.getGenres();
        Collection<Studios> studios = anime.getStudios();
        anime.setGenres(new LinkedList<>());
        anime.setStudios(new LinkedList<>());
        for (Genre genre : genres) {
            saveGenre(genre);
            addGenre(anime, genre.getMal_id());
        }
        for (Studios studio : studios) {
            saveStudio(studio);
            addStudio(anime, studio.getMal_id());
        }
        return anime;
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
    public void saveToWatchLater(Long mal_id) throws IOException {
        Optional<Anime> exist = animeRepo.findBySaveToAndMal_id("WATCH LATER", mal_id);
        if (exist.isPresent()) {
            throw new IllegalStateException("Anime already in watch later.");
        }
        Anime anime = refreshGenresAndStudios(getAnimeById(mal_id));
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

    @Override
    public Boolean isInFavourite(Long id) {
        Optional<Anime> exist = animeRepo.findBySaveToAndMal_id("FAVOURITE", id);
        return exist.isPresent();
    }

    @Override
    public Boolean isInWatchLater(Long id) {
        Optional<Anime> exist = animeRepo.findBySaveToAndMal_id("WATCH LATER", id);
        return exist.isPresent();
    }

    @Override
    public void saveGenre(Genre genre) {
        Optional<Genre> exist = genreRepo.findById(genre.getMal_id());
        if (exist.isEmpty()) {
            genreRepo.save(genre);
        }
    }

    @Override
    public void saveStudio(Studios studios) {
        Optional<Studios> exist = studioRepo.findById(studios.getMal_id());
        if (exist.isEmpty()) {
            studioRepo.save(studios);
        }
    }

    @Override
    public void addGenre(Anime anime, Long genre_id) {
        Optional<Genre> genre = genreRepo.findById(genre_id);
        if (genre.isPresent())
            anime.getGenres().add(genre.get());
    }

    @Override
    public void addStudio(Anime anime, Long studio_id) {
        Optional<Studios> studio = studioRepo.findById(studio_id);
        if (studio.isPresent())
            anime.getStudios().add(studio.get());
    }

    @Override
    public int countFavourite() {
        return animeRepo.countBySaveTo("FAVOURITE");
    }

    @Override
    public int countWatchLater() {
        return animeRepo.countBySaveTo("WATCH LATER");
    }
}
