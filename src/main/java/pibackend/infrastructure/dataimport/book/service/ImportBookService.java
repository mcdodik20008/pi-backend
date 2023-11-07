package pibackend.infrastructure.dataimport.book.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pibackend.domain.book.model.entity.Book;
import pibackend.domain.book.repository.BookRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportBookService {

    private final BookRepository repository;

    public void save(MultipartFile file) {
        try {
            InputStream is = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(is);
            DataFormatter formatter = new DataFormatter();
            Sheet sheet = workbook.getSheet("books");
            Iterator<Row> rows = sheet.iterator();
            List<Book> books = new ArrayList<Book>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Book book = new Book();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            book.setId(formatter.formatCellValue(currentCell));
                            break;
                        case 1:
                            book.setTitle(formatter.formatCellValue(currentCell));
                            break;
                        case 2:
                            book.setSubTitle(formatter.formatCellValue(currentCell));
                            break;
                        case 3:
                            book.setFirstPublishDate(formatter.formatCellValue(currentCell));
                            break;
                        case 4:
                            book.setDescription(formatter.formatCellValue(currentCell));
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                books.add(book);
            }
            workbook.close();
            saveExcelData(books);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveExcelData(List<Book> books) {
        repository.saveAll(books);
    }

}