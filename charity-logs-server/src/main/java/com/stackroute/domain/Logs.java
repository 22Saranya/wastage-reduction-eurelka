package com.stackroute.domain;

import com.stackroute.rabbitmq.model.CharityStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Logs {

    @Id
    private String id;

    private String date;

    private int rating;

    private CharityStatus charityStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public CharityStatus getCharityStatus() {
        return charityStatus;
    }

    public void setCharityStatus(CharityStatus charityStatus) {
        this.charityStatus = charityStatus;
    }
}
