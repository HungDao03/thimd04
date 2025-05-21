package com.thimd4.controller;

import com.thimd4.model.Promotion;
import com.thimd4.service.IPromotionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;

@Controller
@RequestMapping("/promotions")
public class PromotionController {
    private static final Logger logger = LoggerFactory.getLogger(PromotionController.class);

    @Autowired
    private IPromotionService promotionService;

    // 1. GET /promotions với param tìm kiếm (discount, start, end)
    @GetMapping
    public String list(@RequestParam(required = false) Double discount,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                       Model model) {

        model.addAttribute("promotions", promotionService.search(discount, start, end));
        return "list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        logger.info("Showing create form");
        model.addAttribute("promotion", new Promotion());
        return "create";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        logger.info("Showing edit form for promotion id: {}", id);
        Promotion promotion = promotionService.findById(id);
        if (promotion == null) {
            logger.warn("Promotion not found with id: {}", id);
            return "redirect:/promotions";
        }
        model.addAttribute("promotion", promotion);
        return "edit";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("promotion") @Valid Promotion p, BindingResult result, Model model) {
        logger.info("Saving promotion: {}", p);
        if (result.hasErrors()) {
            logger.warn("Validation errors: {}", result.getAllErrors());
            return p.getId() != null ? "edit" : "create";
        }
        if (p.getEndDate().isBefore(p.getStartDate().plusDays(1))) {
            result.rejectValue("endDate", null, "Ngày kết thúc phải sau ngày bắt đầu ít nhất 1 ngày.");
        }
        if (result.hasErrors()) {
            logger.warn("Date validation error");
            return p.getId() != null ? "edit" : "create";
        }
        promotionService.save(p);
        return "redirect:/promotions";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        logger.info("Deleting promotion with id: {}", id);
        promotionService.delete(id);
        return "redirect:/promotions";
    }
}
