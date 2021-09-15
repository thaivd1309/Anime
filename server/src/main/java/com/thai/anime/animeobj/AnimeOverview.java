package com.thai.anime.animeobj;

public class AnimeOverview {
    private Long mal_id;
    private String title;
    private String image_url;

    public AnimeOverview(Long mal_id, String title, String image_url) {
        this.mal_id = mal_id;
        this.title = title;
        this.image_url = image_url;
    }

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

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
