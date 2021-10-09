package com.thai.anime.controller;

import com.thai.anime.animeobj.Role;
import com.thai.anime.animeobj.WebUser;
import com.thai.anime.service.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

//    @ModelAttribute(value = "webuser")
//    public WebUser user(String name, String email, String password) {
//        return new WebUser(name, email, password);
//    }

    @GetMapping
    public ResponseEntity<List<WebUser>> getAllUsers() {
        return ResponseEntity.ok().body(webUserService.findAllUsers());
    }

    @PostMapping(value = "/register")
    public ResponseEntity<WebUser> registerNewUser(@RequestBody WebUser webuser, BindingResult bindingResult) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user/register").toUriString());
        webUserService.addRoleToUser(webuser, "ROLE_USER");
        return ResponseEntity.created(uri).body(webUserService.saveUser(webuser));
    }

    @PostMapping("/role")
    public void saveRole(@RequestBody Role role) {
        webUserService.saveRole(role);
    }
}
