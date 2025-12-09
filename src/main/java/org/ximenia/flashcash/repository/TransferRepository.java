package org.ximenia.flashcash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ximenia.flashcash.model.Transfer;
import org.ximenia.flashcash.model.User;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Integer> {
    List<Transfer> findByFromOrToOrder(User from, User to);
}