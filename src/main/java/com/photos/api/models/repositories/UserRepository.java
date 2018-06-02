package com.photos.api.models.repositories;

import com.photos.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


/**
 * @author Micha Królewski on 2018-04-07.
 * @version 1.0
 */

@Component
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByUserID(Long id);
}
