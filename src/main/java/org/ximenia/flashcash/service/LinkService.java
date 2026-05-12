package org.ximenia.flashcash.service;

import org.springframework.stereotype.Service;
import org.ximenia.flashcash.model.Link;
import org.ximenia.flashcash.model.User;
import org.ximenia.flashcash.repository.LinkRepository;
import org.ximenia.flashcash.repository.UserRepository;
import org.ximenia.flashcash.service.form.AddContactForm;
import java.util.List;

@Service
public class LinkService {

    private final LinkRepository linkRepository;
    private final UserRepository userRepository;
    private final SessionService sessionService;

    public LinkService(LinkRepository linkRepository, UserRepository userRepository, SessionService sessionService) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
        this.sessionService = sessionService;
    }

    // Adds a new contact link between the logged-in user and another user found by email
    public void addContact(AddContactForm form) {
        // Get the currently logged-in user from the security context
        User currentUser = sessionService.sessionUser();

        // Find the target user by email, throw if not found
        User contact = userRepository.findByEmail(form.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        // Prevent a user from adding themselves as a contact
        if (currentUser.getId().equals(contact.getId())) {
            throw new IllegalArgumentException("Vous ne pouvez pas vous ajouter vous-même");
        }

        // Check both directions: A->B and B->A to avoid duplicate links
        boolean alreadyLinked =
                linkRepository.findByUser1AndUser2(currentUser, contact).isPresent() ||
                        linkRepository.findByUser1AndUser2(contact, currentUser).isPresent();

        if (alreadyLinked) {
            throw new IllegalArgumentException("Ce contact existe déjà");
        }

        // Create and save the link
        Link link = new Link();
        link.setUser1(currentUser);
        link.setUser2(contact);
        linkRepository.save(link);
    }

    // Returns all contacts of a given user, regardless of which side of the link they are on
    public List<User> getContacts(User currentUser) {
        // Fetch all links where the user appears as user1 OR user2
        List<Link> links = linkRepository.findByUser1OrUser2(currentUser, currentUser);

        // For each link, return the "other" user (not the current one)
        return links.stream()
                .map(link -> link.getUser1().equals(currentUser) ? link.getUser2() : link.getUser1())
                .toList();
    }
}