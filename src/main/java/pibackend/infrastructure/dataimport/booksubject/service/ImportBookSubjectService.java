package pibackend.infrastructure.dataimport.booksubject.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pibackend.domain.book.repository.BookRepository;
import pibackend.domain.booksubject.model.entity.BookSubject;
import pibackend.domain.booksubject.repository.BookSubjectRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportBookSubjectService {

    private final BookSubjectRepository repository;
    private final BookRepository bookRepository;

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
                            subject.setId((long) currentCell.getNumericCellValue());
                            break;
                        case 1:
                            subject.setSubject(formatter.formatCellValue(currentCell));
                            break;
                        case 2:
                            subject.setBook(bookRepository.findById(formatter.formatCellValue(currentCell)).orElse(null));
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