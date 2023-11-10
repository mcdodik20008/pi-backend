package pibackend.infrastructure.export;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pibackend.domain.issue.model.entity.Issue;

public class IssueExcelExporter {
    
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Issue> issues;

    public IssueExcelExporter(List<Issue> issues) {
        this.issues = issues;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Issues");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Book ID", style);
        createCell(row, 1, "Book", style);
        createCell(row, 2, "Customer ID", style);
        createCell(row, 3, "Customer", style);
        createCell(row, 4, "Date of issue", style);
        createCell(row, 5, "Return date", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Timestamp) {
            cell.setCellValue((Timestamp) value);
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

        for (var issue : issues) {
            var row = sheet.createRow(rowCount);
            var cellCount = 0;
            createCell(row, cellCount++, issue.getBook().getId(), style);
            createCell(row, cellCount++, issue.getBook().getTitle(), style);
            createCell(row, cellCount++, issue.getCustomer().getId(), style);
            createCell(row, cellCount++, issue.getCustomer().getName(), style);
            createCell(row, cellCount++, issue.getDateOfIssue(), style);
            createCell(row, cellCount, issue.getReturnUntil(), style);

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
