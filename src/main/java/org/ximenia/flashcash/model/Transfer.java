package org.ximenia.flashcash.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User from;
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User to;
    private Double amountBeforeFee;
    private Double amountAfterFee;
}
