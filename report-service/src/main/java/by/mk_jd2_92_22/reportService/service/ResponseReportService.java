package by.mk_jd2_92_22.reportService.service;

import by.mk_jd2_92_22.reportService.model.*;
import by.mk_jd2_92_22.reportService.service.api.IResponseReportService;
import by.mk_jd2_92_22.reportService.service.utils.CalculateCPFC;
import by.mk_jd2_92_22.reportService.service.utils.GetFromAnotherService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ResponseReportService implements IResponseReportService {

    private final GetFromAnotherService getFromAnotherService;
    private final CalculateCPFC calculate;

    public ResponseReportService(GetFromAnotherService getFromAnotherService, CalculateCPFC calculateCPFC) {
        this.getFromAnotherService = getFromAnotherService;
        this.calculate = calculateCPFC;
    }
    @Override
    public byte[] generateReport(Report report, HttpHeaders token) {

        final List<JournalFood> journalFoods = this.getFromAnotherService.getJournalFoods(token, report);

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
        cell.setCellValue("Date/Time");
        cell.setCellStyle(cellStyle);

        cell = headerRow.createCell(2);
        cell.setCellValue("Name");
        cell.setCellStyle(cellStyle);

        cell = headerRow.createCell(3);
        cell.setCellValue("Type");
        cell.setCellStyle(cellStyle);

        cell = headerRow.createCell(4);
        cell.setCellValue("Weight");
        cell.setCellStyle(cellStyle);

        cell = headerRow.createCell(5);
        cell.setCellValue("Calories");
        cell.setCellStyle(cellStyle);

        cell = headerRow.createCell(6);
        cell.setCellValue("Proteins");
        cell.setCellStyle(cellStyle);

        cell = headerRow.createCell(7);
        cell.setCellValue("Fats");
        cell.setCellStyle(cellStyle);

        cell = headerRow.createCell(8);
        cell.setCellValue("Carbohydrates");
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
                foodInfo = this.calculate.productCPFC(product, foodWeight);
                foodName = product.getTitle();
                foodType = "Product";
            } else if (recipe != null) {
                foodInfo = this.calculate.recipeCPFC(recipe, foodWeight);
                foodName = recipe.getTitle();
                foodType = "Recipe";
            } else {
                throw new IllegalArgumentException("В записе журнала питания нет продукта и блюда");
            }

            Row row = sheet.createRow(rowNum);

            row.createCell(0).setCellValue(rowNum++);
            row.createCell(1).setCellValue(journalFood.getDtSupply());
            row.createCell(2).setCellValue(foodName);
            row.createCell(3).setCellValue(foodType);
            row.createCell(4).setCellValue(foodWeight);
            row.createCell(5).setCellValue(foodInfo.getCalories());
            row.createCell(6).setCellValue(foodInfo.getProteins());
            row.createCell(7).setCellValue(foodInfo.getFats());
            row.createCell(8).setCellValue(foodInfo.getCarbohydrates());
        }

        Row row = sheet.createRow(rowNum);

        row.createCell(0).setCellValue(rowNum);
        row.createCell(0).setCellValue("Total:");
        row.createCell(4).setCellFormula("SUM(E2:E" + (rowNum - 1) + ")");
        row.createCell(5).setCellValue("SUM(F2:F" + (rowNum - 1) + ")");
        row.createCell(6).setCellValue("SUM(G2:G" + (rowNum - 1) + ")");
        row.createCell(7).setCellValue("SUM(H2:H" + (rowNum - 1) + ")");
        row.createCell(8).setCellValue("SUM(I2:I" + (rowNum - 1) + ")");



        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            workbook.write(byteArrayOutputStream);//TODO write Workbook
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Не удалось записать Отчет ");
        }


        return byteArrayOutputStream.toByteArray();
    }
}
