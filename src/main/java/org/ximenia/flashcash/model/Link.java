package org.ximenia.flashcash.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @ManyToOne
    @JoinColumn(name = "user1_id")
    private User user1;
    @ManyToOne
    @JoinColumn(name = "user2_id")
    private User user2;

}
