package org.ximenia.flashcash.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.ximenia.flashcash.model.User;
import org.ximenia.flashcash.repository.UserRepository;
import org.ximenia.flashcash.service.form.SignInForm;
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

        userRepository.save(user);
    }

    // ---------------------------------------- SIGN IN SERVICE--------------------------------------------
    public void registration(SignInForm form) {
        User user = new User();
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));

        userRepository.save(user);
    }
}