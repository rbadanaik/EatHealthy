package com.samaira.dietplan.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.samaira.dietplan.model.DietPlanEntry;
import com.samaira.dietplan.model.MealType;
import com.samaira.dietplan.model.SectionType;
import com.samaira.dietplan.repository.DietPlanEntryRepository;

@Service
public class ExcelService {

    private final DietPlanEntryRepository dietPlanEntryRepository;
    private final DataFormatter dataFormatter = new DataFormatter();

    public ExcelService(DietPlanEntryRepository dietPlanEntryRepository) {
        this.dietPlanEntryRepository = dietPlanEntryRepository;
    }

    public byte[] exportDietPlan() {
        try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            XSSFSheet sheet = workbook.createSheet("DietPlan");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Section");
            header.createCell(1).setCellValue("MealType");
            header.createCell(2).setCellValue("Item");
            header.createCell(3).setCellValue("SortOrder");

            List<DietPlanEntry> entries = dietPlanEntryRepository.findAllByOrderBySectionAscSortOrderAsc();
            int rowNumber = 1;
            for (DietPlanEntry entry : entries) {
                Row row = sheet.createRow(rowNumber++);
                row.createCell(0).setCellValue(entry.getSection().name());
                row.createCell(1).setCellValue(entry.getMealType().name());
                row.createCell(2).setCellValue(entry.getItem());
                row.createCell(3).setCellValue(entry.getSortOrder());
            }

            for (int i = 0; i < 4; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to export Excel", ex);
        }
    }

    public int importDietPlan(MultipartFile file) {
        List<DietPlanEntry> imported = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream(); XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                String section = getCellText(row.getCell(0));
                String mealType = getCellText(row.getCell(1));
                String item = getCellText(row.getCell(2));
                String sortOrderValue = getCellText(row.getCell(3));

                if (section.isBlank() || mealType.isBlank() || item.isBlank() || sortOrderValue.isBlank()) {
                    continue;
                }

                DietPlanEntry entry = new DietPlanEntry(
                        SectionType.valueOf(section.trim().toUpperCase()),
                        MealType.valueOf(mealType.trim().toUpperCase()),
                        item.trim(),
                        Integer.parseInt(sortOrderValue.trim())
                );
                imported.add(entry);
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid Excel format. Expected columns: Section, MealType, Item, SortOrder", ex);
        }

        if (imported.isEmpty()) {
            throw new IllegalArgumentException("No valid rows found in uploaded file");
        }

        dietPlanEntryRepository.deleteAllInBatch();
        dietPlanEntryRepository.saveAll(imported);
        return imported.size();
    }

    private String getCellText(Cell cell) {
        if (cell == null) {
            return "";
        }
        CellType cellType = cell.getCellType();
        return switch (cellType) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> dataFormatter.formatCellValue(cell);
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }
}
