package com.doug.cashflow.model.db;

/**
 * Created by Doug on 4/25/2017.
 */

public class Header {
    private int _id = 0;
    private String header = "";

    public Header() {
    }

    public Header(int _id, String header) {
        this.setId(_id);
        this.setHeader(header);
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return "<Header.java: Id: " + _id + " Header: " + header + ">";
    }

}
