package org.bg121788.cineflicks.service;

import lombok.Data;
import org.bg121788.cineflicks.dto.UserDTO;
import org.bg121788.cineflicks.entity.User;
import org.bg121788.cineflicks.entity.enums.Role;
import org.bg121788.cineflicks.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(UserDTO userDTO){
        User newUser = new User();
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setUsername(userDTO.getUsername());
        newUser.setEmail(userDTO.getEmail());
        newUser.setRole(Role.valueOf(userDTO.getRole().toUpperCase()));
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(newUser);
    }


    public UserDTO findByUsername(String username) {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole().toString());
        return userDTO;
    }

    public User convertToEntity(UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findByUsername(userDTO.getUsername());
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new IllegalArgumentException("User not found: " + userDTO.getUsername());
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
