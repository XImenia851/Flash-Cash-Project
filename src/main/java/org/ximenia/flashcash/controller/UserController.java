package org.ximenia.flashcash.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // CORRECTION ICI
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.ximenia.flashcash.model.User;
import org.ximenia.flashcash.service.LinkService;
import org.ximenia.flashcash.service.SessionService;
import org.ximenia.flashcash.service.TransferService;
import org.ximenia.flashcash.service.UserService;
import org.ximenia.flashcash.service.form.SignUpForm;

@Controller
public class UserController {
    private final LinkService linkService;
    private final UserService userService;
    private final TransferService transferService;
    private final SessionService sessionService;

    public UserController(LinkService linkService, UserService userService, TransferService transferService, SessionService sessionService) {
        this.linkService = linkService;
        this.userService = userService;
        this.transferService = transferService;
        this.sessionService = sessionService;
    }

    @GetMapping("/")
    public ModelAndView home(Model model) {
        User user = sessionService.sessionUser();
        return new ModelAndView("home", "user", user);
    }

    @GetMapping("/signup")
    public ModelAndView showSignUpForm() {
        return new ModelAndView("signup", "signUpForm", new SignUpForm());
    }

    @PostMapping("/signup") // CORRECTION : ajout du chemin
    public ModelAndView processRequest(@ModelAttribute("signUpForm") SignUpForm form) {
        userService.registration(form);
        return new ModelAndView("redirect:/signin");
    }
}