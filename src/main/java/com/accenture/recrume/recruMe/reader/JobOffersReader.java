package com.accenture.recrume.recruMe.reader;

import com.accenture.recrume.recruMe.exception.ExcelFileNotFoundException;
import com.accenture.recrume.recruMe.model.*;
import com.accenture.recrume.recruMe.repository.JobOffersRepository;
import com.accenture.recrume.recruMe.repository.JobSkillsRepository;
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
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

@Data
@Service
public class JobOffersReader {
    private JobOffersRepository jobOffersRepo;
    private SkillsRepository skillsRepo;
    private JobSkillsRepository jobSkillsRepo;
    private SkillService skillService;

    public JobOffersReader() {
    }

    @Autowired
    public JobOffersReader(JobOffersRepository jobOffersRepo, SkillsRepository skillsRepo, JobSkillsRepository jobSkillsRepo, SkillService skillService){
        this.jobOffersRepo = jobOffersRepo;
        this.skillsRepo = skillsRepo;
        this.jobSkillsRepo = jobSkillsRepo;
        this.skillService = skillService;
    }

    public List<JobOffer> readExcel(String excelFileName) throws IOException {
        List<JobOffer> jobOffers = new ArrayList<>();

        FileInputStream excelFile = new FileInputStream(new File(excelFileName) );
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet datatypeSheet = workbook.getSheetAt(1);
        Iterator<Row> row = datatypeSheet.iterator();

        row.next(); //reads the headers
        while (row.hasNext()) {
            Row currentRow = row.next();
            Iterator<Cell> cellIterator = currentRow.iterator();
            Cell companyCell = cellIterator.next();
            Cell titleCell = cellIterator.next();
            Cell regionCell = cellIterator.next();
            Cell educationLevelCell = cellIterator.next();
//            Cell professionalLeveCell = cellIterator.next();


            JobOffer jobOffer = new JobOffer(
                    companyCell.getStringCellValue(),
                    titleCell.getStringCellValue(),
                    Region.valueOf(regionCell.getStringCellValue()),
                    EducationLevel.valueOf(educationLevelCell.getStringCellValue()),
                    null,
                    Status.ACTIVE,
                    GregorianCalendar.getInstance()
            );

            jobOffers.add(jobOffer);
            jobOffersRepo.save(jobOffer);
            while (cellIterator.hasNext())
            {
                JobSkill jobSkill = new JobSkill();
                Skill skill = new Skill(cellIterator.next().getStringCellValue());
                System.out.println(skill);
                System.out.println(skill.getName());
                skillService.skillExist(skill.getName());
                jobSkill.setSkill(skillsRepo.findSkillByName(skill.getName()));
                jobSkill.setJobOffer(jobOffer);
                jobSkillsRepo.save(jobSkill);
            }
        }
        return jobOffers;
    }
}
