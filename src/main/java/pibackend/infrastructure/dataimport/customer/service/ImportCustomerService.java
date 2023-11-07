package pibackend.infrastructure.dataimport.customer.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pibackend.domain.customer.model.entity.Customer;
import pibackend.domain.customer.repository.CustomerRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportCustomerService {

    private final CustomerRepository repository;

    public void save(MultipartFile file) {
        try {
            InputStream is = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(is);
            DataFormatter formatter = new DataFormatter();
            Sheet sheet = workbook.getSheet("customers");
            Iterator<Row> rows = sheet.iterator();
            List<Customer> customers = new ArrayList<Customer>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Customer customer = new Customer();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            customer.setId(formatter.formatCellValue(currentCell));
                            break;
                        case 1:
                            customer.setName(formatter.formatCellValue(currentCell));
                            break;
                        case 2:
                            customer.setAddress(formatter.formatCellValue(currentCell));
                            break;
                        case 3:
                            customer.setZip(formatter.formatCellValue(currentCell));
                            break;
                        case 4:
                            customer.setCity(formatter.formatCellValue(currentCell));
                            break;
                        case 5:
                            customer.setPhone(formatter.formatCellValue(currentCell));
                            break;
                        case 6:
                            customer.setEmail(formatter.formatCellValue(currentCell));
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                customers.add(customer);
            }
            workbook.close();
            saveExcelData(customers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveExcelData(List<Customer> customers) {
        repository.saveAll(customers);
    }

}
