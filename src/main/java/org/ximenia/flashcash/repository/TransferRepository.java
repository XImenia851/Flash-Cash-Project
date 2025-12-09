package org.ximenia.flashcash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ximenia.flashcash.model.Transfer;
import org.ximenia.flashcash.model.User;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Integer> {

    // Tous les transferts où l'utilisateur est expéditeur ou destinataire
    List<Transfer> findByFromOrTo(User from, User to);

    // Optionnel : triés par date décroissante
    List<Transfer> findByFromOrToOrderByDateDesc(User from, User to);
}
