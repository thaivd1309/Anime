package com.thai.anime.controller;

import com.thai.anime.animeobj.WebUser;
import com.thai.anime.repo.WebUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebUserService {
    private final WebUserRepository repository;

    @Autowired
    public WebUserService(WebUserRepository repository) {
        this.repository = repository;
    }

    public List<WebUser> findAllUsers() {
        return repository.findAll();
    }

    public WebUser saveUser(WebUser user) {
//        String email = user.getEmail();
//        WebUser foundedUser = repository.findWebUserByEmail(email);
//        if (foundedUser != null) {
//            throw new IllegalStateException("Username already existed!");
//        }
        repository.save(user);
        return user;
    }
}
