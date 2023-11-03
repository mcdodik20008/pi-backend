package pibackend.domain.dataimport.bookcover.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import pibackend.domain.bookcovers.model.entity.BookCover;
import pibackend.domain.bookcovers.repository.BookCoverRepository;

@Service
@RequiredArgsConstructor
public class ImportBookCoverService {

    private final BookCoverRepository repository;

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
                            cover.setId(formatter.formatCellValue(currentCell));
                            break;
                        case 1:
                            cover.setName(formatter.formatCellValue(currentCell));
                            break;
                        case 2:
                            cover.setAddress(formatter.formatCellValue(currentCell));
                            break;
                        case 3:
                            cover.setZip(formatter.formatCellValue(currentCell));
                            break;
                        case 4:
                            cover.setCity(formatter.formatCellValue(currentCell));
                            break;
                        case 5:
                            cover.setPhone(formatter.formatCellValue(currentCell));
                            break;
                        case 6:
                            cover.setEmail(formatter.formatCellValue(currentCell));
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
