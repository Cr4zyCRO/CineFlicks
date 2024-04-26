package org.bg121788.cineflicks.repository;

import org.bg121788.cineflicks.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

    User getUserByUsername(String username);

    List<User> findAllByUsername(String username);

    User getUserByEmail(String email);
}
