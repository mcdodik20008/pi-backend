package pibackend.domain.dataimport.booksubject.service;

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
import pibackend.domain.booksubject.model.entity.BookSubject;
import pibackend.domain.booksubject.repository.BookSubjectRepository;

@Service
@RequiredArgsConstructor
public class ImportBookSubjectService {

    private final BookSubjectRepository repository;
    
    public void save(MultipartFile file) {
        try {
            InputStream is = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(is);
            DataFormatter formatter = new DataFormatter();
            Sheet sheet = workbook.getSheet("book_subjects");
            Iterator<Row> rows = sheet.iterator();
            List<BookSubject> subjects = new ArrayList<BookSubject>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                BookSubject subject = new BookSubject();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            subject.setId(formatter.formatCellValue(currentCell));
                            break;
                        case 1:
                            subject.setName(formatter.formatCellValue(currentCell));
                            break;
                        case 2:
                            subject.setAddress(formatter.formatCellValue(currentCell));
                            break;
                        case 3:
                            subject.setZip(formatter.formatCellValue(currentCell));
                            break;
                        case 4:
                            subject.setCity(formatter.formatCellValue(currentCell));
                            break;
                        case 5:
                            subject.setPhone(formatter.formatCellValue(currentCell));
                            break;
                        case 6:
                            subject.setEmail(formatter.formatCellValue(currentCell));
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                subjects.add(subject);
            }
            workbook.close();
            saveExcelData(subjects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveExcelData(List<BookSubject> subjects) {
        repository.saveAll(subjects);
    }

    
}