package com.thai.anime;

import com.thai.anime.animeobj.Anime;
import com.thai.anime.repo.AnimeRepo;
import com.thai.anime.service.AnimeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AnimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimeApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(AnimeService animeService) throws Exception {
		return (String[] args) -> {
			animeService.saveToFavourite(new Long(1));
			animeService.saveToFavourite(new Long(10059));
			animeService.saveToFavourite(new Long(10087));
			animeService.saveToFavourite(new Long(33047));
			animeService.saveToFavourite(new Long(366));
			animeService.saveToFavourite(new Long(	38084));
			animeService.saveToFavourite(new Long(28701));
			animeService.saveToFavourite(new Long(	11741));
			animeService.saveToFavourite(new Long(27525));
			animeService.saveToFavourite(new Long(	22297));
			animeService.saveToFavourite(new Long(	14829));
		};
	}
}
