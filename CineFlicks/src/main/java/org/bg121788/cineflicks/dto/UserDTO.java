package org.bg121788.cineflicks.dto;

import lombok.Data;
import lombok.ToString;
import org.bg121788.cineflicks.entity.enums.Role;

@ToString
@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String role = Role.USER.toString();


}
