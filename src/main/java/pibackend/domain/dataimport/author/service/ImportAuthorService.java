package pibackend.domain.dataimport.author.service;

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
import pibackend.domain.author.model.entity.Author;
import pibackend.domain.author.repository.AuthorRepository;

@Service
@RequiredArgsConstructor
public class ImportAuthorService {

    private final AuthorRepository repository;

    public void save(MultipartFile file) {
        try {
            InputStream is = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(is);
            DataFormatter formatter = new DataFormatter();
            Sheet sheet = workbook.getSheet("authors");
            Iterator<Row> rows = sheet.iterator();
            List<Author> authors = new ArrayList<Author>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Author author = new Author();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            author.setUuid(formatter.formatCellValue(currentCell));
                            break;
                        case 1:
                            author.setName(formatter.formatCellValue(currentCell));
                            break;
                        case 2:
                            author.setBio(formatter.formatCellValue(currentCell));
                            break;
                        case 3:
                            author.setBirthDate(formatter.formatCellValue(currentCell));
                            break;
                        case 4:
                            author.setDeathDate(formatter.formatCellValue(currentCell));
                            break;
                        case 5:
                            author.setWikipedia(formatter.formatCellValue(currentCell));
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                authors.add(author);
            }
            workbook.close();
            saveExcelData(authors);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveExcelData(List<Author> authors) {
        repository.saveAll(authors);
    }
    
}
