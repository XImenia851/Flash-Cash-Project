package org.ximenia.flashcash.service.form;

import lombok.Data;

@Data
public class TransferForm {
    private String recipientEmail;
    private Double amount;
    private String description;
}