package org.ximenia.flashcash.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.ximenia.flashcash.service.LinkService;
import org.ximenia.flashcash.service.SessionService;
import org.ximenia.flashcash.service.TransferService;
import org.ximenia.flashcash.service.UserService;
import org.ximenia.flashcash.service.form.TransferForm;

@Controller
public class TransferController {

    private final LinkService linkService;
    private final UserService userService;
    private final TransferService transferService;
    private final SessionService sessionService;

    public TransferController(LinkService linkService, UserService userService,
                              TransferService transferService, SessionService sessionService) {
        this.linkService = linkService;
        this.userService = userService;
        this.transferService = transferService;
        this.sessionService = sessionService;
    }

    @GetMapping("/transfer")
    public String showTransferPage(Model model) {
        model.addAttribute("transferForm", new TransferForm());
        model.addAttribute("contacts", linkService.getContacts(sessionService.sessionUser()));
        model.addAttribute("transfers", transferService.getTransferHistory());
        model.addAttribute("user", userService.getCurrentUser());
        return "transfer";
    }

    @PostMapping("/transfer")
    public ModelAndView processTransfer(@ModelAttribute("transferForm") TransferForm form) {
        transferService.makeTransfer(form);
        return new ModelAndView("redirect:/transfer?success");
    }
}