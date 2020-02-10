package com.accenture.recrume.recruMe.repository;

import com.accenture.recrume.recruMe.model.Skill;
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
public class SkillsReader {

    @Autowired
    private SkillsRepository skillsRepo;

    public SkillsReader() {
    }

    public SkillsReader(SkillsRepository skillsRepo){
        this.skillsRepo = skillsRepo;
    }

    public List<Skill> readExcel(String excelFileName) throws IOException {
        List<Skill> skills = new ArrayList<>();
        FileInputStream excelFile = new FileInputStream(new File(excelFileName));
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet datatypeSheet = workbook.getSheetAt(0);
        Iterator<Row> row = datatypeSheet.iterator();

        row.next(); //reads the headers
        while (row.hasNext()) {
            Row currentRow = row.next();
            Iterator<Cell> cellIterator = currentRow.iterator();

            Cell nameCell = cellIterator.next();


            Skill skill = new Skill(
                    nameCell.getStringCellValue()
            );

            skills.add(skill);
            skillsRepo.save(skill);
        }
        return skills;
    }

}
