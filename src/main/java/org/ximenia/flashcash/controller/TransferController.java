package org.ximenia.flashcash.controller;

import org.springframework.stereotype.Controller;
import org.ximenia.flashcash.service.LinkService;
import org.ximenia.flashcash.service.SessionService;
import org.ximenia.flashcash.service.TransferService;
import org.ximenia.flashcash.service.UserService;

@Controller
public class TransferController {
    private final LinkService linkService;
    private final UserService userService;
    private final TransferService transferService;
    private final SessionService sessionService;

    public TransferController(LinkService linkService, UserService userService, TransferService transferService, SessionService sessionService) {
        this.linkService = linkService;
        this.userService = userService;
        this.transferService = transferService;
        this.sessionService = sessionService;
    }
}
