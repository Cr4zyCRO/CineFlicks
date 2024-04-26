package org.bg121788.cineflicks.service;

import lombok.Data;
import org.bg121788.cineflicks.dto.UserDTO;
import org.bg121788.cineflicks.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Data
@Service
public class AuthenticationService {
    private final UserRepository userRepository;

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
}
