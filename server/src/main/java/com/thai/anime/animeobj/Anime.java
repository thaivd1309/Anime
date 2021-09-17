package com.thai.anime.animeobj;


import javax.persistence.*;
import java.util.Collection;

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

    @Transient
    private String image_url_lg;

    private String status;
    private String premiered;
    private String rating;
    private double score;
    private String saveTo;

    @ManyToMany
    private Collection<Studios> studios;

    @ManyToMany
    private Collection<Genre> genres;

    @Column(length = 2000)
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
                  Collection<Studios> studios,
                  Collection<Genre> genres,
                  String synopsis)
    {
        this.mal_id = mal_id;
        this.title = title;
        this.episodes = episodes;
        this.url = url;
        this.image_url = image_url;
        this.status = status;
        this.premiered = premiered;
        this.rating = rated;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rated) {
        this.rating = rated;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Collection<Studios> getStudios() {
        return studios;
    }

    public void setStudios(Collection<Studios> studios) {
        this.studios = studios;
    }

    public Collection<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Collection<Genre> genres) {
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

    public String getImage_url_lg() {
        image_url_lg = new StringBuilder(image_url).insert(image_url.length() - 4, "l").toString();
        return image_url_lg;
    }

    public void setImage_url_lg(String image_url_lg) {
        this.image_url_lg = image_url_lg;
    }
}
