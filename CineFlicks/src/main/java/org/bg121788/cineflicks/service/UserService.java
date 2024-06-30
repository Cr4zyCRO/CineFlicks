package org.bg121788.cineflicks.service;

import lombok.Data;
import org.bg121788.cineflicks.dto.UserDTO;
import org.bg121788.cineflicks.entity.User;
import org.bg121788.cineflicks.entity.enums.Role;
import org.bg121788.cineflicks.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Data
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

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

    public void deleteUserById(UUID userId) {
        userRepository.deleteById(userId);
    }

    public void updateUser(UserDTO userDTO, String password) {
        User user = convertToEntity(userDTO);

        boolean isUpdated = updateIfChanged(user::getFirstName, user::setFirstName, userDTO.getFirstName())
                | updateIfChanged(user::getLastName, user::setLastName, userDTO.getLastName())
                | updateIfChanged(user::getUsername, user::setUsername, userDTO.getUsername())
                | updateIfChanged(user::getEmail, user::setEmail, userDTO.getEmail())
                | updatePasswordIfChanged(user, password);

        if(isUpdated){
            userRepository.save(user);
        }
    }

    private boolean updateIfChanged(Supplier<String> getter, Consumer<String> setter, String newValue) {
        if (!Objects.equals(getter.get(), newValue)) {
            setter.accept(newValue);
            return true;
        }
        return false;
    }

    private boolean updatePasswordIfChanged(User user, String newPassword) {
        String encodedPassword = authenticationService.getPasswordEncoder().encode(newPassword);

        if (newPassword.isEmpty()){
            user.setPassword(user.getPassword());
            return true;
        }

        if (!encodedPassword.equals(user.getPassword())) {
            user.setPassword(encodedPassword);
            return true;
        }
        return false;
    }

    public Optional<User> getUserById(UUID userId) {
        return userRepository.findById(userId);
    }
}
