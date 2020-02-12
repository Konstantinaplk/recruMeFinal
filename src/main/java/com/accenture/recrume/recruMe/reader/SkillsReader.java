package com.accenture.recrume.recruMe.reader;

import com.accenture.recrume.recruMe.model.Skill;
import com.accenture.recrume.recruMe.repository.SkillsRepository;
import com.accenture.recrume.recruMe.service.SkillService;
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
    private SkillsRepository skillsRepo;
    private SkillService skillService;

    public SkillsReader() {
    }

    @Autowired
    public SkillsReader(SkillsRepository skillsRepo, SkillService skillService){
        this.skillsRepo = skillsRepo;
        this.skillService = skillService;
    }

    public List<String> readExcel(String excelFileName) throws IOException {
        List<String> skills = new ArrayList<>();
        FileInputStream excelFile = new FileInputStream(new File(excelFileName));
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet datatypeSheet = workbook.getSheetAt(2);
        Iterator<Row> row = datatypeSheet.iterator();

        row.next(); //reads the headers
        while (row.hasNext()) {
            Row currentRow = row.next();
            Iterator<Cell> cellIterator = currentRow.iterator();
            Cell nameCell = cellIterator.next();
            Skill skill = new Skill(
                    nameCell.getStringCellValue()
            );
            skillService.skillExist(skill.getName());
            skills.add(skill.getName());
        }
        return skills;
    }

}
