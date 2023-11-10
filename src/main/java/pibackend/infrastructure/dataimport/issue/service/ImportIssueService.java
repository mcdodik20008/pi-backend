package pibackend.infrastructure.dataimport.issue.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pibackend.domain.book.repository.BookRepository;
import pibackend.domain.customer.repository.CustomerRepository;
import pibackend.domain.issue.model.entity.Issue;
import pibackend.domain.issue.repository.IssueRepository;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportIssueService {

    private final IssueRepository repository;
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;

    public void save(MultipartFile file) {
        try {
            InputStream is = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(is);
            DataFormatter formatter = new DataFormatter();
            Sheet sheet = workbook.getSheet("issues");
            Iterator<Row> rows = sheet.iterator();
            List<Issue> issues = new ArrayList<Issue>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Issue issue = new Issue();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            issue.setBook(bookRepository.findById(formatter.formatCellValue(currentCell)).orElse(null));
                        case 2:
                            issue.setCustomer(customerRepository.findById(formatter.formatCellValue(currentCell)).orElse(null));
                            break;
                        case 4:
                            issue.setDateOfIssue(LocalDate.parse(formatter.formatCellValue(currentCell)));
                            break;
                        case 5:
                            issue.setReturnUntil(LocalDate.parse(formatter.formatCellValue(currentCell)));
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                issues.add(issue);
            }
            workbook.close();
            saveExcelData(issues);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveExcelData(List<Issue> issues) {
        repository.saveAll(issues);
    }

}
