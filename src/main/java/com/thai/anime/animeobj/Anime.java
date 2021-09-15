package com.thai.anime.animeobj;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Anime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long mal_id;
    private String title;
    private Long episodes;
    private String url;
    private String image_url;
    private String status;
    private String premiered;
    private String rated;
    private double score;
    private String saveTo;

    @ManyToMany
    private List<Studios> studios;

    @ManyToMany
    private List<Genre> genres;

    private String synopsis;
    public Anime(Long mal_id,
                  String title,
                  Long episodes,
                  String url,
                  String image_url,
                  String status,
                  String premiered,
                  String rated,
                  double score,
                  List<Studios> studios,
                  List<Genre> genres,
                  String synopsis)
    {
        this.mal_id = mal_id;
        this.title = title;
        this.episodes = episodes;
        this.url = url;
        this.image_url = image_url;
        this.status = status;
        this.premiered = premiered;
        this.rated = rated;
        this.score = score;
        this.studios = studios;
        this.genres = genres;
        this.synopsis = synopsis;
    }

    public Anime(){}

    public Long getMal_id() {
        return mal_id;
    }

    public void setMal_id(Long mal_id) {
        this.mal_id = mal_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Long episodes) {
        this.episodes = episodes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPremiered() {
        return premiered;
    }

    public void setPremiered(String premiered) {
        this.premiered = premiered;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public List<Studios> getStudios() {
        return studios;
    }

    public void setStudios(List<Studios> studios) {
        this.studios = studios;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public AnimeOverview getOverview() {
        return new AnimeOverview(mal_id, title, image_url);
    }

    public String getSaveTo() {
        return saveTo;
    }

    public void setSaveTo(String saveTo) {
        this.saveTo = saveTo;
    }
}
