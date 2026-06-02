package com.samaira.dietplan.controller;

import com.samaira.dietplan.dto.DietEntryDto;
import com.samaira.dietplan.service.DietPlanService;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/diet-plan")
public class DietPlanController {

    private final DietPlanService dietPlanService;

    public DietPlanController(DietPlanService dietPlanService) {
        this.dietPlanService = dietPlanService;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<DietEntryDto>>> getDietPlan() {
        return ResponseEntity.ok(dietPlanService.getGroupedDietPlan());
    }
}
