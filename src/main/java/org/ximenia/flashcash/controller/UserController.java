package org.ximenia.flashcash.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.ximenia.flashcash.model.User;
import org.ximenia.flashcash.service.SessionService;
import org.ximenia.flashcash.service.UserService;
import org.ximenia.flashcash.service.form.SignInForm;
import org.ximenia.flashcash.service.form.SignUpForm;

@Controller
public class UserController {

    private final UserService userService;
    private final SessionService sessionService;

    public UserController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping("/")
    public ModelAndView homeRedirect(SessionStatus sessionStatus) {
        User user = sessionService.sessionUser();
        return new ModelAndView("redirect:/profil");
    }

    // ----------------------------SIGN UP (GET)
    @GetMapping("/signup")
    public ModelAndView showSignUpForm() {
        return new ModelAndView("signup", "signUpForm", new SignUpForm());
    }

    // ----------------------------------------SIGN UP (POST)
    @PostMapping("/signup")
    public ModelAndView processSignUp(@ModelAttribute("signUpForm") SignUpForm form) {
        userService.registration(form);
        return new ModelAndView("redirect:/signin?success");
    }

    // --------------------------------------SIGN IN (GET)
    @GetMapping("/signin")
    public ModelAndView showSignInForm() {
        return new ModelAndView("signin", "signInForm", new SignInForm());
    }

//    @PostMapping("/signin")
//    public ModelAndView processSignIn(@ModelAttribute("signInForm") SignInForm form) {
//        userService.registration(form);
//        return new ModelAndView("redirect:/profil");
//    }

//     -------------------------------------------PROFIL (GET)
    @GetMapping("/profil")
//    public ModelAndView showProfil() {
//        return new ModelAndView("profil");
//    }
    public String profil(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "profil";
    }
}
