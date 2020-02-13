package com.accenture.recrume.reader;

import com.accenture.recrume.model.*;
import com.accenture.recrume.repository.AppSkillsRepository;
import com.accenture.recrume.repository.ApplicantsRepository;
import com.accenture.recrume.repository.SkillsRepository;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Data
public class ApplicantsReader {
    private static final int SHEET_TO_READ = 0;

    @Autowired
    private ApplicantsRepository applicantsRepo;
    @Autowired
    private SkillsRepository skillsRepo;
    @Autowired
    private AppSkillsRepository appSkillsRepo;

    private Sheet datatypeSheet;
    private List<Applicant> applicants = new ArrayList<>();

    public void openFile(String excelFileName) throws IOException {
        File file = ResourceUtils.getFile("classpath:"+excelFileName);
        FileInputStream excelFile = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(excelFile);
        datatypeSheet = workbook.getSheetAt(SHEET_TO_READ);
    }

    private Applicant createObject(Row row) {
        Applicant applicant = new Applicant();
        AppSkill appSkill = new AppSkill();
        Iterator<Cell> cellIterator = row.iterator();
        applicant.setFirstName(cellIterator.next().getStringCellValue());
        applicant.setLastName(cellIterator.next().getStringCellValue());
        applicant.setAddress(cellIterator.next().getStringCellValue());
        applicant.setRegion(Region.valueOf(cellIterator.next().getStringCellValue()));
        applicant.setEducationLevel(EducationLevel.valueOf(cellIterator.next().getStringCellValue()));
        applicant.setProfessionalLevel(ProfessionalLevel.valueOf(cellIterator.next().getStringCellValue()));
        applicant.setYob(0);
        applicantsRepo.save(applicant);
        while (cellIterator.hasNext())
        {
            appSkill.setSkill(skillsRepo.findSkillByName(cellIterator.next().getStringCellValue()));
            appSkill.setApplicant(applicant);
            appSkillsRepo.save(appSkill);
        }
        return applicant;
    }

    public void readData() {
        Iterator<Row> rows = datatypeSheet.iterator();
        rows.next(); //reads the headers
        while (rows.hasNext()) {
            Row currentRow = rows.next();
            Applicant applicant = createObject(currentRow);
            applicants.add(applicant);
        }
    }

    public List<Applicant> getData(String filename) throws IOException {
        openFile(filename);
        readData();
        return applicants;
    }
}
