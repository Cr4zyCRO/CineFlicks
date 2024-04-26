package org.bg121788.cineflicks.controller;

import lombok.RequiredArgsConstructor;
import org.bg121788.cineflicks.dto.UserDTO;
import org.bg121788.cineflicks.service.AuthenticationService;
import org.bg121788.cineflicks.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping("/register")
    public ModelAndView showRegistrationForm() {
        ModelAndView modelAndView = new ModelAndView("/authentication/register");
        modelAndView.addObject("userDTO", new UserDTO());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@ModelAttribute UserDTO userDTO, @RequestParam String confirmPassword){
        ModelAndView modelAndView;

        Map<String,String> processedInfo = authenticationService.processRegister(userDTO, confirmPassword);

        if (processedInfo.get("success").equals("false")){
            modelAndView = new ModelAndView("/authentication/register");
            modelAndView.addObject("error", processedInfo.get("error"));
        }else {
            userService.registerUser(userDTO);
            modelAndView = new ModelAndView("redirect:/auth/login");
        }

        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView showLoginForm() {
        return new ModelAndView("/authentication/login");
    }

}
