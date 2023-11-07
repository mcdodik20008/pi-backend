package pibackend.infrastructure.export;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pibackend.domain.author.model.entity.Author;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AuthorExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Author> listAuthors;

    public AuthorExcelExporter(List<Author> listAuthors) {
        this.listAuthors = listAuthors;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Authors");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Name", style);
        createCell(row, 1, "BIO", style);
        createCell(row, 2, "Birth date", style);
        createCell(row, 3, "Death date", style);
        createCell(row, 4, "Wikipedia", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (var author : listAuthors) {
            var row = sheet.createRow(rowCount);
            var cellCount = 0;
            createCell(row, cellCount++, author.getName(), style);
            createCell(row, cellCount++, author.getBio(), style);
            createCell(row, cellCount++, author.getBirthDate(), style);
            createCell(row, cellCount++, author.getDeathDate(), style);
            createCell(row, cellCount, author.getWikipedia(), style);

            rowCount++;
        }

    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}