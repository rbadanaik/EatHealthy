package com.samaira.dietplan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samaira.dietplan.model.DietPlanEntry;

public interface DietPlanEntryRepository extends JpaRepository<DietPlanEntry, Long> {
    List<DietPlanEntry> findAllByOrderBySectionAscSortOrderAsc();
}
