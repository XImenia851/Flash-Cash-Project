package org.ximenia.flashcash.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.ximenia.flashcash.service.LinkService;
import org.ximenia.flashcash.service.SessionService;
import org.ximenia.flashcash.service.TransferService;
import org.ximenia.flashcash.service.UserService;
import org.ximenia.flashcash.service.form.AddContactForm;

@Controller
public class ContactController {

    private final LinkService linkService;
    private final UserService userService;
    private final TransferService transferService;
    private final SessionService sessionService;

    public ContactController(LinkService linkService, UserService userService,
                             TransferService transferService, SessionService sessionService) {
        this.linkService = linkService;
        this.userService = userService;
        this.transferService = transferService;
        this.sessionService = sessionService;
    }

    @GetMapping("/contact")
    public ModelAndView showContactForm() {
        ModelAndView mav = new ModelAndView("contact");
        mav.addObject("contactForm", new AddContactForm());
        mav.addObject("contacts", linkService.getContacts(sessionService.sessionUser()));
        return mav;
    }

    @PostMapping("/contact")
    public ModelAndView processAddContact(@ModelAttribute("contactForm") AddContactForm form) {
        linkService.addContact(form);
        return new ModelAndView("redirect:/contact?success");
    }
}
