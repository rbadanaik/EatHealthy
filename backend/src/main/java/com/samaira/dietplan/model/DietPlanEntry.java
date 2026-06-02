package com.samaira.dietplan.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "diet_plan_entries")
public class DietPlanEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SectionType section;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MealType mealType;

    @Column(nullable = false)
    private String item;

    @Column(nullable = false)
    private Integer sortOrder;

    protected DietPlanEntry() {
    }

    public DietPlanEntry(SectionType section, MealType mealType, String item, Integer sortOrder) {
        this.section = section;
        this.mealType = mealType;
        this.item = item;
        this.sortOrder = sortOrder;
    }

    public Long getId() {
        return id;
    }

    public SectionType getSection() {
        return section;
    }

    public MealType getMealType() {
        return mealType;
    }

    public String getItem() {
        return item;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSection(SectionType section) {
        this.section = section;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}
