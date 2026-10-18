package com.landshield.controller;

import com.landshield.model.VerificationCase;
import com.landshield.repository.VerificationCaseRepository;
import com.landshield.service.DemoTransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/officer")
public class OfficerController {

    private final VerificationCaseRepository caseRepository;
    private final DemoTransactionService transactionService;

    public OfficerController(VerificationCaseRepository caseRepository,
                             DemoTransactionService transactionService) {
        this.caseRepository = caseRepository;
        this.transactionService = transactionService;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("cases", caseRepository.findAllByOrderByIdDesc());
        return "officer";
    }

    @PostMapping("/cases/{id}/reviewed")
    public String markReviewed(@PathVariable Long id) {
        VerificationCase item = caseRepository.findById(id).orElseThrow();
        item.reviewStatus = "REVIEWED";
        caseRepository.save(item);
        return "redirect:/officer";
    }

    @PostMapping("/reset-demo")
    public String resetDemo(RedirectAttributes redirectAttributes) {
        int removed = transactionService.resetRuntimeDemoTransactions();
        redirectAttributes.addFlashAttribute("resetMessage",
                "রিসেট সম্পন্ন: " + removed + "টি ডেমো লেনদেন মুছে মূল ডেটা ফিরিয়ে আনা হয়েছে।");
        return "redirect:/officer";
    }
}
