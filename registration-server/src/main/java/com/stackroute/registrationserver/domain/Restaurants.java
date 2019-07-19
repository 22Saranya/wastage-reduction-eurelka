package com.stackroute.registrationserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFCell;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurants {
    private String restaurantName;
    private String username;
    private String password;
    private String email;
    private long mobile;
    private String certificateNo;
    private String certificateName;
    private String location;
    private String address;
    private String role;
}
