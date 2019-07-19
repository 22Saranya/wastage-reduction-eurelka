package com.stackroute.SeedData;

import com.stackroute.controller.CharityController;
import com.stackroute.domain.CharitySeed;
import com.stackroute.repository.CharitySeedRepository;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileInputStream;
@Component
public class CharitySeedData implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    CharitySeedRepository charitySeedRepository;

//    @Autowired
//    CharityController charityController;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

         CharitySeed charitySeed = new CharitySeed();
        try {

            FileInputStream file = new FileInputStream(new File("charity-logs-server/CharityList.xlsx"));
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
//            System.out.println( workbook.getSheetAt(0).getRow(1).getCell(1));

//            //Iterate through each rows one by one
//            Iterator<Row> rowIterator = sheet.iterator();
//            while (rowIterator.hasNext()) {
//                Row row = rowIterator.next();
//                //For each row, iterate through all the columns
//                Iterator<Cell> cellIterator = row.cellIterator();
////                wb.getSheetAt(0).getRow(9).getCell(4);
//
//
//                while (cellIterator.hasNext()) {
//                    Cell cell = cellIterator.next();
//                    //Check the cell type and format accordingly
//                    switch (cell.getCellType()) {
//                        case Cell.CELL_TYPE_NUMERIC:
//                            System.out.print(cell.getNumericCellValue()+"\n");
//                            break;
//                        case Cell.CELL_TYPE_STRING:
//                            System.out.print(cell.getStringCellValue() + "\n");
//                            break;
//                    }
//                }

            System.out.println(sheet.getRow(1).getLastCellNum());


//            for (int i = 1; i < sheet.getLastRowNum(); i++) {
//                int j = 0;
//
//                charitySeed.setCharity_name(workbook.getSheetAt(0).getRow(i).getCell(j + 1) + "\n");
//                charitySeed.setImg_url(workbook.getSheetAt(0).getRow(i).getCell(j + 4) + "\n");
//                charitySeed.setPhonenumber(workbook.getSheetAt(0).getRow(i).getCell(j+3)+ "\n");
//                charitySeed.setAddress(workbook.getSheetAt(0).getRow(i).getCell(j + 2) + "\n");
//
//
//                charitySeedRepository.save(charitySeed);
//                System.out.println(charitySeed);
//
//            }
            for (int i = 1; i < sheet.getLastRowNum()+1; i++) {

                   int j=0;
                    charitySeed.setCharity_name(workbook.getSheetAt(0).getRow(i).getCell(j) + "\n");
                    charitySeed.setAddress(workbook.getSheetAt(0).getRow(i).getCell(j +1) + "\n");
                    charitySeed.setPhonenumber(workbook.getSheetAt(0).getRow(i).getCell(j +2) + "\n");
                    charitySeed.setImg_url(workbook.getSheetAt(0).getRow(i).getCell(j+3 ) + "\n");


                    charitySeedRepository.save(charitySeed);
                    System.out.println(charitySeed);


            }


            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
