package com.accenture.recrume.recruMe.repository;

import com.accenture.recrume.recruMe.exception.ExcelFileNotFoundException;
import com.accenture.recrume.recruMe.model.EducationLevel;
import com.accenture.recrume.recruMe.model.JobOffer;
import com.accenture.recrume.recruMe.model.Region;
import com.accenture.recrume.recruMe.model.Status;
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

    public JobOffersReader() {
    }

    @Autowired
    public JobOffersReader(JobOffersRepository jobOffersRepo){
        this.jobOffersRepo = jobOffersRepo;
    }

    public List<JobOffer> readExcel(String excelFileName) throws IOException {
        List<JobOffer> jobOffers = new ArrayList<>();
        FileInputStream excelFile = new FileInputStream(new File(excelFileName));
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet datatypeSheet = workbook.getSheetAt(0);
        Iterator<Row> row = datatypeSheet.iterator();

        row.next(); //reads the headers
        while (row.hasNext()) {
            Row currentRow = row.next();
            Iterator<Cell> cellIterator = currentRow.iterator();

            Cell titleCell = cellIterator.next();
            Cell educationLevelCell = cellIterator.next();
            Cell companyCell = cellIterator.next();
            Cell regionCell = cellIterator.next();


            JobOffer jobOffer = new JobOffer(
                    titleCell.getStringCellValue(),
                    EducationLevel.valueOf(educationLevelCell.getStringCellValue()),
                    companyCell.getStringCellValue(),
                    Status.ACTIVE,
                    Region.valueOf(regionCell.getStringCellValue()),
                    GregorianCalendar.getInstance()
            );

            jobOffers.add(jobOffer);
            jobOffersRepo.save(jobOffer);
        }
        return jobOffers;
    }
}
