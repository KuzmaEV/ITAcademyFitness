package by.mk_jd2_92_22.reportService.minio;

import by.mk_jd2_92_22.reportService.model.*;
import by.mk_jd2_92_22.reportService.service.utils.CalculatorCPFC;
import by.mk_jd2_92_22.reportService.service.utils.ProviderMicroservice;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ReportHandler  {

    private final ProviderMicroservice getFromAnotherService;
    private final CalculatorCPFC calculate;

    public ReportHandler(ProviderMicroservice getFromAnotherService, CalculatorCPFC calculateCPFC) {
        this.getFromAnotherService = getFromAnotherService;
        this.calculate = calculateCPFC;
    }

    public byte[] generateReport(Report report) {

        final List<JournalFood> journalFoods = this.getFromAnotherService.getJournalFoodEntries(report);
        final DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd.MM.yy");
        final DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("hh:mm");

        Workbook workbook = new XSSFWorkbook();

        Font font = workbook.createFont();
        font.setBold(true);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.index);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFont(font);

        Sheet sheet = workbook.createSheet("Report journal food");

        Row headerRow = sheet.createRow(0);

//    CellStyle rowStyle = headerRow.getRowStyle();
//    rowStyle.setFillBackgroundColor(IndexedColors.DARK_BLUE.getIndex());
//    rowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//    rowStyle.setAlignment(HorizontalAlignment.CENTER);
//    rowStyle.setFont(font);



//        headerRow.createCell(0).setCellValue("№");
//        headerRow.createCell(1).setCellValue("Date/Time");
//        headerRow.createCell(2).setCellValue("Name");
//        headerRow.createCell(3).setCellValue("Type");
//        headerRow.createCell(4).setCellValue("Weight");
//        headerRow.createCell(5).setCellValue("Calories");
//        headerRow.createCell(6).setCellValue("Proteins");
//        headerRow.createCell(7).setCellValue("Fats");
//        headerRow.createCell(8).setCellValue("Carbohydrates");



        Cell cell = headerRow.createCell(0);
        cell.setCellValue("№");
        cell.setCellStyle(cellStyle);

        cell = headerRow.createCell(1);
        cell.setCellValue("Date");
        cell.setCellStyle(cellStyle);

        cell = headerRow.createCell(2);
        cell.setCellValue("Time");
        cell.setCellStyle(cellStyle);

        cell = headerRow.createCell(3);
        cell.setCellValue("Name");
        cell.setCellStyle(cellStyle);

        cell = headerRow.createCell(4);
        cell.setCellValue("Type");
        cell.setCellStyle(cellStyle);

        cell = headerRow.createCell(5);
        cell.setCellValue("Weight");
        cell.setCellStyle(cellStyle);

        cell = headerRow.createCell(6);
        cell.setCellValue("Calories");
        cell.setCellStyle(cellStyle);

        cell = headerRow.createCell(7);
        cell.setCellValue("Proteins");
        cell.setCellStyle(cellStyle);

        cell = headerRow.createCell(8);
        cell.setCellValue("Fats");
        cell.setCellStyle(cellStyle);

        cell = headerRow.createCell(9);
        cell.setCellValue("Carbs");
        cell.setCellStyle(cellStyle);


        int rowNum = 1;
        for (JournalFood journalFood : journalFoods) {

            int foodWeight = journalFood.getWeight();
            Product product = journalFood.getProduct();
            Recipe recipe = journalFood.getDish();

            FoodCPFC foodInfo;
            String foodType;
            String foodName;

            if (product != null) {
                foodInfo = this.calculate.calculateProduct(product, foodWeight);
                foodName = product.getTitle();
                foodType = "Product";
            } else if (recipe != null) {
                foodInfo = this.calculate.calculateRecipe(recipe, foodWeight);
                foodName = recipe.getTitle();
                foodType = "Recipe";
            } else {
                throw new IllegalArgumentException("В записе журнала питания нет продукта и блюда");
            }

            Row row = sheet.createRow(rowNum);

            row.createCell(0).setCellValue(rowNum++);
            row.createCell(1).setCellValue(journalFood.getDtSupply().format(formatDate));
            row.createCell(2).setCellValue(journalFood.getDtSupply().format(formatTime));

            row.createCell(3).setCellValue(foodName);
            row.createCell(4).setCellValue(foodType);
            row.createCell(5).setCellValue(foodWeight);

            row.createCell(6).setCellValue(foodInfo.getCalories());
            row.createCell(7).setCellValue(foodInfo.getProteins())  ;
            row.createCell(8).setCellValue(foodInfo.getFats());
            row.createCell(9).setCellValue(foodInfo.getCarbohydrates());
        }

        Row row = sheet.createRow(rowNum);

        row.createCell(0).setCellValue("Total:");

        row.createCell(5).setCellFormula("SUM(F2:F" + rowNum + ")");
        row.createCell(6).setCellFormula("SUM(G2:G" + rowNum + ")");
        row.createCell(7).setCellFormula("SUM(H2:H" + rowNum + ")");
        row.createCell(8).setCellFormula("SUM(I2:I" + rowNum + ")");
        row.createCell(9).setCellFormula("SUM(J2:J" + rowNum + ")");





            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()){
                workbook.write(byteArrayOutputStream);
                workbook.close();

            return byteArrayOutputStream.toByteArray();

    } catch (IOException e) {
        e.printStackTrace();
        throw new IllegalArgumentException("Не удалось записать Отчет ");
    }
    }
}
