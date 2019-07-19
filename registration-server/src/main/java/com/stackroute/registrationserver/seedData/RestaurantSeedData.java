package com.stackroute.registrationserver.seedData;
import com.stackroute.registrationserver.domain.Restaurants;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.springframework.stereotype.Component;
import com.stackroute.registrationserver.controller.RegistrationController;
import com.stackroute.registrationserver.domain.Charities;
import com.stackroute.registrationserver.service.RabbitService;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileInputStream;

@Component
public class RestaurantSeedData implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    RegistrationController registrationController;

    @Autowired
    private RabbitService rabbitService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Restaurants restaurants=new Restaurants();
        try {

            FileInputStream file = new FileInputStream(new File("registration-server/charityseed.xlsx"));
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(1);

            System.out.println("");

            for (int i = 1; i < sheet.getLastRowNum()+1; i++) {
                int j = 0;
                restaurants.setRestaurantName(workbook.getSheetAt(1).getRow(i).getCell(j ).toString());
                restaurants.setUsername(workbook.getSheetAt(1).getRow(i).getCell(j + 1).toString());
                restaurants.setPassword(workbook.getSheetAt(1).getRow(i).getCell(j + 2).toString());
                restaurants.setEmail(workbook.getSheetAt(1).getRow(i).getCell(j + 3).toString());
//                restaurants.setMobile(Long.parseLong(workbook.getSheetAt(1).getRow(i).getCell(j + 4).toString());
//                restaurants.setCertificateNo(workbook.getSheetAt(1).getRow(i).getCell(j + 5).toString());
                restaurants.setCertificateName(workbook.getSheetAt(1).getRow(i).getCell(j + 6).toString());
//                restaurants.setLocation(workbook.getSheetAt(1).getRow(i).getCell(j + 7).toString());
                restaurants.setAddress(workbook.getSheetAt(1).getRow(i).getCell(j + 8).toString());
                restaurants.setRole(workbook.getSheetAt(0).getRow(i).getCell(j + 9).toString());
                System.out.println(restaurants);

                registrationController.updateRestaurant(restaurants);
                rabbitService.sendToRestaurantRabbitMq(restaurants);
//                Cell cell=workbook.getSheetAt(1).getRow(i).getCell(j );
//                switch (cell.getCellType()) {
//                   case Cell.CELL_TYPE_NUMERIC:
//                           System.out.print(cell.getNumericCellValue()+"\n");
//
//                            break;
//                        case Cell.CELL_TYPE_STRING:
//                            System.out.print(cell.getStringCellValue() + "\n");
//                            break;
//                    }
/*
                if(restaurants instanceof String)
                   cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
                restaurants.setRestaurantName(workbook.getSheetAt(1).getRow(i).getCell(j ).toString());  */



            }

            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
