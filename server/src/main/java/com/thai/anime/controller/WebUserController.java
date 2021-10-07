package com.thai.anime.controller;

import com.thai.anime.animeobj.Role;
import com.thai.anime.animeobj.WebUser;
import com.thai.anime.service.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/user")
public class WebUserController {
    private final WebUserService webUserService;

    @Autowired
    public WebUserController(WebUserService webUserService) {
        this.webUserService = webUserService;
    }

    @GetMapping
    public ResponseEntity<List<WebUser>> getAllUsers() {
        return ResponseEntity.ok().body(webUserService.findAllUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<WebUser> registerNewUser(@RequestBody WebUser user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user/register").toUriString());
        webUserService.addRoleToUser(user, "USER");
        return ResponseEntity.created(uri).body(webUserService.saveUser(user));
    }

    @PostMapping("/role")
    public void saveRole(@RequestBody Role role) {
        webUserService.saveRole(role);
    }
}
