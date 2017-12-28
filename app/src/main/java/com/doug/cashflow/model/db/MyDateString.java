package com.doug.cashflow.model.db;

import java.util.Date;

/**
 * Created by Doug on 4/26/2017.
 */

public class MyDateString {
    private Integer _id;
    private Integer user_id;
    private String  field;
    private String  date;

    public MyDateString() {
    }

    public MyDateString(int _id, Integer user_id, String field, String date) {
        this.setId(_id);
        this.setUserId(user_id);
        this.setField(field);
        this.setDate(date);
    }

    public Integer getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public Integer getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "<MyDateString: " + "UserId: " + _id + " MyDateString: " + date.toString() + ">";
    }
}
