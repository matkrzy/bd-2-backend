package com.photos.api.repositories;

import com.photos.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Micha Królewski on 2018-04-07.
 * @version 1.0
 */

@Component
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
