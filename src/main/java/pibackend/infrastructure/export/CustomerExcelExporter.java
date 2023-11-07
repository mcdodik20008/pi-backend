package pibackend.infrastructure.export;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pibackend.domain.customer.model.entity.Customer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CustomerExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Customer> listCustomers;

    public CustomerExcelExporter(List<Customer> listCustomers) {
        this.listCustomers = listCustomers;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Customers");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Name", style);
        createCell(row, 1, "Address", style);
        createCell(row, 2, "ZIP", style);
        createCell(row, 3, "City", style);
        createCell(row, 4, "Phone", style);
        createCell(row, 5, "Email", style);

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

        for (var customer : listCustomers) {
            var row = sheet.createRow(rowCount);
            var cellCount = 0;
            createCell(row, cellCount++, customer.getName(), style);
            createCell(row, cellCount++, customer.getAddress(), style);
            createCell(row, cellCount++, customer.getZip(), style);
            createCell(row, cellCount++, customer.getCity(), style);
            createCell(row, cellCount++, customer.getPhone(), style);
            createCell(row, cellCount, customer.getEmail(), style);

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