package org.ximenia.flashcash.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.ximenia.flashcash.model.User;
import org.ximenia.flashcash.service.UserService;
import org.ximenia.flashcash.service.form.SignInForm;
import org.ximenia.flashcash.service.form.SignUpForm;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ModelAndView homeRedirect() {
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

    // -------------------------------------------PROFIL (GET)
    @GetMapping("/profil")
    public String profil(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "profil";
    }
}
