package org.ximenia.flashcash.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.ximenia.flashcash.model.User;
import org.ximenia.flashcash.model.UserAccount;
import org.ximenia.flashcash.repository.UserRepository;
import org.ximenia.flashcash.service.form.SignUpForm;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // -------------------------------------- SIGN UP SERVICE -------------------------------------------------
    public void registration(SignUpForm form) {
        User user = new User();
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());

        // Créer un compte bancaire pour le nouvel utilisateur
        UserAccount account = new UserAccount();
        account.setAmount(0.0);
        account.setIban("FR76" + System.currentTimeMillis()); // IBAN temporaire
        user.setUserAccount(account);

        userRepository.save(user);
    }

    // ---------------------------------------- SIGN IN SERVICE--------------------------------------------
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    //-------------PROFIL DE USER -------------------
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }
}