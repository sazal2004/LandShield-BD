package com.landshield.controller;

import com.landshield.model.VerificationResult;
import com.landshield.service.VerificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class VerificationController {

    private final VerificationService verificationService;

    public VerificationController(VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @PostMapping("/verify-result")
    public String verify(
            @RequestParam String district,
            @RequestParam String upazila,
            @RequestParam String mouza,
            @RequestParam String khatianNo,
            @RequestParam String plotNo,
            @RequestParam String sellerName,
            @RequestParam String buyerName,
            @RequestParam String shareRef,
            @RequestParam double proposedArea,
            Model model) {
        try {
            VerificationResult result = verificationService.verify(
                    district, upazila, mouza, khatianNo, plotNo,
                    sellerName, buyerName, shareRef, proposedArea);
            model.addAttribute("result", result);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "result";
    }
}
