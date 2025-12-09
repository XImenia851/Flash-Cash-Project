package org.ximenia.flashcash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ximenia.flashcash.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findUserByEmail(String email); // email correspond au champ 'email' dans User
}
