package com.doug.cashflow.model.db;

/**
 * Created by Doug on 4/30/2017.
 */

public class Field {
    private int _id = 0;
    private String field = "";

    public Field() {
    }

    public Field(int _id, String field) {
        this._id = _id;
        this.field = field;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "<Field.java: _Id: " + _id + " Field: " + field + ">";
    }


}
