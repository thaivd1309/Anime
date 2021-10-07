package com.thai.anime.service;

import com.thai.anime.animeobj.Role;
import com.thai.anime.animeobj.WebUser;
import com.thai.anime.repo.RoleRepo;
import com.thai.anime.repo.WebUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class WebUserService implements UserDetailsService {
    private final WebUserRepository repository;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public WebUserService(WebUserRepository repository, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        return user;
    }

    public void saveRole(Role role) {
        Optional<Role> exist = roleRepo.findByName(role.getName());
        if(exist.isPresent()) {
            throw new IllegalStateException("Role has already existed in database.");
        }
        roleRepo.save(role);
    }

    private Role getRoleByName(String name) {
        Optional<Role> exist = roleRepo.findByName(name);
        if(exist.isEmpty()) {
            throw new IllegalStateException("Role does not exist.");
        }
        return exist.get();
    }

    public void addRoleToUser(WebUser user, String roleName) {
        Role role = getRoleByName(roleName);
        user.addRole(role);
    }

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
        WebUser webUser = repository.findWebUserByEmail(user);
        if(webUser == null) {
            throw new UsernameNotFoundException("Email not registered.");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        webUser.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new User(webUser.getEmail(), webUser.getPassword(), authorities);
    }
}
