package org.ximenia.flashcash.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.ximenia.flashcash.model.User;
import org.ximenia.flashcash.repository.UserRepository;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUserName(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository
                .findUserByMails(s);
        if (user.isPresent()) {
            return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword())
        }
        throw new UsernameNotFoundException(s);
        }
    }
}
