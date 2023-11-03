package pibackend.domain.dataimport.issue.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import pibackend.domain.issue.model.entity.Issue;
import pibackend.domain.issue.repository.IssueRepository;

@Service
@RequiredArgsConstructor
public class ImportIssueService {

    private final IssueRepository repository;

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
                            issue.setId((long)currentCell.getNumericCellValue());
                            break;
                        case 1:
                            issue.setDateOfIssue(formatter.formatCellValue(currentCell));
                            break;
                        case 2:
                            issue.setReturnUntil(formatter.formatCellValue(currentCell));
                            break;
                        case 3:
                            issue.setCustomer(formatter.formatCellValue(currentCell));
                            break;
                        case 4:
                            issue.setBook(formatter.formatCellValue(currentCell));
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
        issues = repository.saveAll(issues);
    }    
}
