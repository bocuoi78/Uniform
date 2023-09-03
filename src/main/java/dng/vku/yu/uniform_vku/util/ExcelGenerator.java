package dng.vku.yu.uniform_vku.util;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import dng.vku.yu.uniform_vku.model.entity.Student;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelGenerator {

    private List<Student> studentList;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelGenerator(List <Student> studentList) {
        this.studentList = studentList;
        workbook = new XSSFWorkbook();
    }
    private void writeHeader() {
        sheet = workbook.createSheet("Pán áo đồng phục VKU");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "ID", style);
        createCell(row, 1, "Name", style);
        createCell(row, 2, "Phone", style);
        createCell(row, 3, "Class", style);
        createCell(row, 4, "Gender", style);
        createCell(row, 5, "Birthday", style);
        createCell(row, 6, "Size", style);
        createCell(row, 7, "Received", style);
        createCell(row, 8, "Created On", style);
        createCell(row, 9, "Update On", style);
        createCell(row, 10, "Note", style);
    }
    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
    if (valueOfCell == null) {
        cell.setCellValue("");
    } else if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else if (valueOfCell instanceof Date) {
            cell.setCellValue((Date) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }
    private void write() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (Student record: studentList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, record.getId(), style);
            createCell(row, columnCount++, record.getName(), style);
            createCell(row, columnCount++, record.getPhone(), style);
            createCell(row, columnCount++, record.getClass_name(), style);
            createCell(row, columnCount++, record.getGender(), style);
            createCell(row, columnCount++, record.getBirthday(), style);
            createCell(row, columnCount++, record.getSize(), style);
            createCell(row, columnCount++, record.getReceived(), style);
            createCell(row, columnCount++, record.getCreatedOn(), style);
            createCell(row, columnCount++, record.getUpdatedOn(), style);
            createCell(row, columnCount++, record.getNote(), style);
        }
    }
    public void generateExcelFile(HttpServletResponse response) throws IOException {
        writeHeader();
        write();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}