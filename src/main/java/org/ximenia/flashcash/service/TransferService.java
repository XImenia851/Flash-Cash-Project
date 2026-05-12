package org.ximenia.flashcash.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.ximenia.flashcash.model.Transfer;
import org.ximenia.flashcash.model.User;
import org.ximenia.flashcash.repository.LinkRepository;
import org.ximenia.flashcash.repository.TransferRepository;
import org.ximenia.flashcash.repository.UserRepository;
import org.ximenia.flashcash.repository.UserAccountRepository;
import org.ximenia.flashcash.service.form.TransferForm;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransferService {

    private static final double FEE_RATE = 0.005; // 0.5% fee like PayPal

    private final TransferRepository transferRepository;
    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;
    private final LinkRepository linkRepository;
    private final SessionService sessionService;

    public TransferService(TransferRepository transferRepository,
                           UserRepository userRepository,
                           UserAccountRepository userAccountRepository,
                           LinkRepository linkRepository,
                           SessionService sessionService) {
        this.transferRepository = transferRepository;
        this.userRepository = userRepository;
        this.userAccountRepository = userAccountRepository;
        this.linkRepository = linkRepository;
        this.sessionService = sessionService;
    }

    @Transactional
    public void makeTransfer(TransferForm form) {
        User sender = sessionService.sessionUser();

        // Find recipient by email, throw if not found
        User recipient = userRepository.findByEmail(form.getRecipientEmail())
                .orElseThrow(() -> new RuntimeException("Destinataire introuvable"));

        // Check that recipient is in sender's contacts (both directions)
        boolean isContact =
                linkRepository.findByUser1AndUser2(sender, recipient).isPresent() ||
                        linkRepository.findByUser1AndUser2(recipient, sender).isPresent();

        if (!isContact) {
            throw new IllegalArgumentException("Ce destinataire n'est pas dans vos contacts");
        }

        // Calculate fee and total amount to deduct from sender
        double amountBeforeFee = form.getAmount();
        double fee = amountBeforeFee * FEE_RATE;
        double amountAfterFee = amountBeforeFee - fee;

        // Check sender has enough balance (amount + fee)
        if (sender.getUserAccount().getAmount() < amountBeforeFee) {
            throw new IllegalArgumentException("Solde insuffisant");
        }

        // Debit sender, credit recipient — using our fixed plus/minus methods
        sender.getUserAccount().minus(amountBeforeFee);
        recipient.getUserAccount().plus(amountAfterFee);

        // Persist both account changes
        userAccountRepository.save(sender.getUserAccount());
        userAccountRepository.save(recipient.getUserAccount());

        // Record the transfer
        Transfer transfer = new Transfer();
        transfer.setFrom(sender);
        transfer.setTo(recipient);
        transfer.setAmountBeforeFee(amountBeforeFee);
        transfer.setAmountAfterFee(amountAfterFee);
        transfer.setDate(LocalDateTime.now());
        transferRepository.save(transfer);
    }

    // Returns all transfers for the current user, sorted by date descending
    public List<Transfer> getTransferHistory() {
        User user = sessionService.sessionUser();
        return transferRepository.findByFromOrToOrderByDateDesc(user, user);
    }
}