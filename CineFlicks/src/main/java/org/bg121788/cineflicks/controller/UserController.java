package org.bg121788.cineflicks.controller;

import lombok.Data;
import org.bg121788.cineflicks.service.MovieService;
import org.bg121788.cineflicks.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Data
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final MovieService movieService;

    @GetMapping
    public ModelAndView testFunction(){
        return new ModelAndView() ;
    }
}
