package com.samaira.dietplan.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.samaira.dietplan.model.DietPlanEntry;
import com.samaira.dietplan.model.MealType;
import com.samaira.dietplan.model.SectionType;
import com.samaira.dietplan.repository.DietPlanEntryRepository;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedDietPlan(DietPlanEntryRepository dietPlanEntryRepository) {
        return args -> {
            if (dietPlanEntryRepository.count() > 0) {
                return;
            }

            dietPlanEntryRepository.save(new DietPlanEntry(SectionType.MORNING, MealType.BREAKFAST, "Oatmeal with fruits", 1));
            dietPlanEntryRepository.save(new DietPlanEntry(SectionType.MORNING, MealType.SNACK, "Yogurt with nuts", 2));
            dietPlanEntryRepository.save(new DietPlanEntry(SectionType.MORNING, MealType.DRINK, "Green tea", 3));

            dietPlanEntryRepository.save(new DietPlanEntry(SectionType.AFTERNOON, MealType.LUNCH, "Grilled chicken salad", 1));
            dietPlanEntryRepository.save(new DietPlanEntry(SectionType.AFTERNOON, MealType.SNACK, "Fruit smoothie", 2));
            dietPlanEntryRepository.save(new DietPlanEntry(SectionType.AFTERNOON, MealType.DRINK, "Herbal tea", 3));

            dietPlanEntryRepository.save(new DietPlanEntry(SectionType.EVENING, MealType.SNACK, "Sprouts chaat", 1));
            dietPlanEntryRepository.save(new DietPlanEntry(SectionType.EVENING, MealType.DRINK, "Lemon water", 2));

            dietPlanEntryRepository.save(new DietPlanEntry(SectionType.NIGHT, MealType.DINNER, "Paneer and sauteed vegetables", 1));
            dietPlanEntryRepository.save(new DietPlanEntry(SectionType.NIGHT, MealType.DRINK, "Warm turmeric milk", 2));
        };
    }
}
