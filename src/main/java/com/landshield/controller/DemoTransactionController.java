package com.landshield.controller;

import com.landshield.model.CompletionResult;
import com.landshield.service.DemoTransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DemoTransactionController {

    private final DemoTransactionService transactionService;

    public DemoTransactionController(DemoTransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/demo/complete")
    public String complete(
            @RequestParam Long propertyId,
            @RequestParam String sellerName,
            @RequestParam String buyerName,
            @RequestParam double proposedArea,
            @RequestParam String shareRef,
            Model model) {
        try {
            CompletionResult result = transactionService.completeTransaction(
                    propertyId, sellerName, buyerName, proposedArea, shareRef);
            model.addAttribute("completion", result);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "transaction-success";
    }
}
