package org.bg121788.cineflicks.service;

import ch.qos.logback.core.encoder.Encoder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.bg121788.cineflicks.dto.UserDTO;
import org.bg121788.cineflicks.repository.UserRepository;
import org.bg121788.cineflicks.security.SecurityConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Data
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final SecurityConfiguration securityConfiguration;

    public Map<String, String> processRegister(UserDTO user, String confirmPassword){
        Map<String, String> process = new HashMap<>();

        process.put("success", "true");
        process.put("error", null);

        if(!user.getPassword().equals(confirmPassword)){
            process.replace("success", process.get("success"), "false");
            process.replace("error", process.get("error"), "Provided passwords do not match.");
        }
        if (userRepository.getUserByUsername(user.getUsername()) != null){
            process.replace("success", process.get("success"), "false");
            process.replace("error", process.get("error"), "Provided username is already taken.");
        }
        if (userRepository.getUserByEmail(user.getEmail()) != null){
            process.replace("success", process.get("success"), "false");
            process.replace("error", process.get("error"),  "Provided email is already taken.");
        }
        return process;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
    }

    public PasswordEncoder getPasswordEncoder() {
        return securityConfiguration.passwordEncoder();
    }
}
