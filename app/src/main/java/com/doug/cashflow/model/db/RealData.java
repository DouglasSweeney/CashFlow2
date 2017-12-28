package com.doug.cashflow.model.db;

/**
 * Created by Doug on 4/30/2017.
 */

public class RealData {
    private Integer _id = 0;
    private Integer user_id = 0;
    private String header = "";
    private Integer header_id = 0;
    private String field = "";
    private Integer field_id = 0;
    private Double  value = -1.0;

    public RealData() {
    }

    public RealData(int _id, Integer user_id, String header, Integer header_id, String field, Integer field_id, Double value) {
        this.setId(_id);
        this.setUserId(user_id);
        this.setHeader(header);
        this.setHeaderId(header_id);
        this.setField(field);
        this.setFieldId(field_id);
        this.setValue(value);
    }

    public Integer getId() {
        return _id;
    }

    public void setId(Integer _id) {
        this._id=_id;
    }

    public Integer getUserId() {
        return user_id;
    }

    public void setUserId(Integer user_id) {
        this.user_id=user_id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header=header;
    }

    public Integer getHeaderId() {
        return header_id;
    }

    public void setHeaderId(Integer header_id) {
        this.header_id=header_id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field=field;
    }

    public Integer getFieldId() {
        return field_id;
    }

    public void setFieldId(Integer field_id) {
        this.field_id=field_id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value=value;
    }
}
