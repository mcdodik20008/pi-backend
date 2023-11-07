package pibackend.infrastructure.dataimport.bookcover.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pibackend.domain.book.repository.BookRepository;
import pibackend.domain.bookcovers.model.entity.BookCover;
import pibackend.domain.bookcovers.repository.BookCoverRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportBookCoverService {

    private final BookCoverRepository repository;
    private final BookRepository bookRepository;

    public void save(MultipartFile file) {
        try {
            InputStream is = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(is);
            DataFormatter formatter = new DataFormatter();
            Sheet sheet = workbook.getSheet("book_covers");
            Iterator<Row> rows = sheet.iterator();
            List<BookCover> covers = new ArrayList<BookCover>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                BookCover cover = new BookCover();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            cover.setId((long) currentCell.getNumericCellValue());
                            break;
                        case 1:
                            cover.setCoverFile((int) currentCell.getNumericCellValue());
                            break;
                        case 2:
                            cover.setBook(bookRepository.findById(formatter.formatCellValue(currentCell)).orElse(null));
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                covers.add(cover);
            }
            workbook.close();
            saveExcelData(covers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveExcelData(List<BookCover> covers) {
        repository.saveAll(covers);
    }
}
