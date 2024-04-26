package org.bg121788.cineflicks.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.bg121788.cineflicks.entity.enums.Role;

import java.util.UUID;
@Data
@Getter
@Entity
@Table(name = "user", schema = "public")
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "first_name")
    private  String firstName;

    @Column(name = "last_name")
    private  String lastName;

    @Column(name = "username", unique = true)
    private  String username;

    @Column(name = "email", unique = true)
    private  String email;

    @Column(name = "password")
    private  String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

}
