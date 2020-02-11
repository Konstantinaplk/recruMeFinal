package com.accenture.recrume.recruMe.repository;

import com.accenture.recrume.recruMe.model.Applicant;
import com.accenture.recrume.recruMe.model.EducationLevel;
import com.accenture.recrume.recruMe.model.ProfessionalLevel;
import com.accenture.recrume.recruMe.model.Region;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@Service
public class ApplicantsReader {
    private ApplicantsRepository applicantsRepo;
    private  final int NULL_YOB = 0;

    public ApplicantsReader() {
    }

    @Autowired
    public ApplicantsReader(ApplicantsRepository applicantsRepo){
        this.applicantsRepo = applicantsRepo;
    }

    public List<Applicant> readExcel(String excelFileName) throws IOException {
        List<Applicant> applicants = new ArrayList<>();
        FileInputStream excelFile = new FileInputStream(new File(excelFileName));
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet datatypeSheet = workbook.getSheetAt(0);
        Iterator<Row> row = datatypeSheet.iterator();

        row.next(); //reads the headers
        while (row.hasNext()) {
            Row currentRow = row.next();
            Iterator<Cell> cellIterator = currentRow.iterator();

            Cell firstNameCell = cellIterator.next();
            Cell lastNameCell = cellIterator.next();
            Cell addressCell = cellIterator.next();
            Cell regionCell = cellIterator.next();
            Cell educationLevelCell = cellIterator.next();
            Cell professionalLevelCell = cellIterator.next();

            Applicant applicant = new Applicant(
                    firstNameCell.getStringCellValue(),
                    lastNameCell.getStringCellValue(),
                    addressCell.getStringCellValue(),
                    Region.valueOf(regionCell.getStringCellValue()),
                    EducationLevel.valueOf(educationLevelCell.getStringCellValue()),
                    ProfessionalLevel.valueOf(professionalLevelCell.getStringCellValue()),
                    NULL_YOB
                    );

            applicants.add(applicant);
            applicantsRepo.save(applicant);
        }
        return applicants;
    }
}
