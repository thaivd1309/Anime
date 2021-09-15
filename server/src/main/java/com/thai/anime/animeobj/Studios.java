package com.thai.anime.animeobj;


import javax.persistence.*;

@Entity
@Table
public class Studios {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long mal_id;
    private String name;

    public Studios(Long mal_id, String name) {
        this.mal_id = mal_id;
        this.name = name;
    }

    public Studios() {
    }

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
