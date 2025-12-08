package org.ximenia.flashcash.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @ManyToMany
    User user1;
    @ManyToMany
    User user2;
}
