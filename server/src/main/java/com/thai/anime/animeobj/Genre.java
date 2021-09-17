package com.thai.anime.animeobj;

import javax.persistence.*;

@Entity
@Table
public class Genre {

    @Id
    private Long mal_id;
    private String name;

    public Genre(Long mal_id, String name) {
        this.mal_id = mal_id;
        this.name = name;
    }

    public Genre() {}

    public Long getMal_id() {
        return mal_id;
    }

    public void setMal_id(Long mal_id) {
        this.mal_id = mal_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
