package org.ximenia.flashcash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ximenia.flashcash.model.Link;
import org.ximenia.flashcash.model.User;
import java.util.List;
import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    List<Link> findByUser1OrUser2(User user1, User user2);
    Optional<Link> findByUser1AndUser2(User user1, User user2);
}