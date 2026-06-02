package com.samaira.dietplan.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.samaira.dietplan.dto.DietEntryDto;
import com.samaira.dietplan.model.DietPlanEntry;
import com.samaira.dietplan.model.SectionType;
import com.samaira.dietplan.repository.DietPlanEntryRepository;

@Service
public class DietPlanService {

    private final DietPlanEntryRepository dietPlanEntryRepository;

    public DietPlanService(DietPlanEntryRepository dietPlanEntryRepository) {
        this.dietPlanEntryRepository = dietPlanEntryRepository;
    }

    public Map<String, List<DietEntryDto>> getGroupedDietPlan() {
        Map<String, List<DietEntryDto>> grouped = new LinkedHashMap<>();
        grouped.put("Morning", new ArrayList<>());
        grouped.put("Afternoon", new ArrayList<>());
        grouped.put("Evening", new ArrayList<>());
        grouped.put("Night", new ArrayList<>());

        for (DietPlanEntry entry : dietPlanEntryRepository.findAllByOrderBySectionAscSortOrderAsc()) {
            grouped.get(toTitle(entry.getSection())).add(new DietEntryDto(
                    toTitle(entry.getMealType().name()),
                    entry.getItem()
            ));
        }

        return grouped;
    }

    private String toTitle(SectionType sectionType) {
        return toTitle(sectionType.name());
    }

    private String toTitle(String value) {
        String lower = value.toLowerCase();
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }
}
