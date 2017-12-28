package com.doug.cashflow.model.db;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Doug on 5/1/2017.
 */

public class CashFlowDb2 {
    public static long updateIntegerDataTable(Integer userId, String header, Integer headerId, String field, Integer fieldId, Integer value) {
        ContentValues cv = new ContentValues();

        cv.put(CashFlowDb.INTEGER_DATA_VALUE, value);

        return CashFlowDb.dbWrite.update(CashFlowDb.INTEGER_DATA_TABLE, cv, null, null);

    }

    private static Header makeHeader(String headerString) {
        String where = CashFlowDb.HEADERS_HEADER + " = ?";
        String[] whereArgs = {headerString};
        Header header;

        Cursor cursor = CashFlowDb.dbRead.query(CashFlowDb.HEADERS_TABLE, null, where, whereArgs, null, null, null);

        cursor.moveToFirst();
        header = CashFlowDb.getHeaderFromCursor(cursor);

        cursor.close();

        return header;
    }

    private static Field makeField(String fieldString) {
        String where = CashFlowDb.FIELDS_FIELD + " = ?";
        String[] whereArgs = {fieldString};
        Field field;

        Cursor cursor = CashFlowDb.dbRead.query(CashFlowDb.FIELDS_TABLE, null, where, whereArgs, null, null, null);

        cursor.moveToFirst();
        field = CashFlowDb.getFieldFromCursor(cursor);

        cursor.close();

        return field;
    }

    // update the realData table
    public static long updateDataTable(Integer userId, String headerString, String fieldString, double value) {
        Double result;
        ContentValues cv = new ContentValues();

        Header header = makeHeader(headerString);
        Field field = makeField(fieldString);
        String where = CashFlowDb.REAL_DATA_USER_ID + " = ? AND " + CashFlowDb.REAL_DATA_HEADER_ID + " = ? AND " + CashFlowDb.REAL_DATA_FIELD_ID + " = ?";
        String[] whereArgs = {String.valueOf(userId), String.valueOf(header.getId()), String.valueOf(field.getId())};

        cv.put(CashFlowDb.REAL_DATA_VALUE, value);

        int rowCount = CashFlowDb.dbWrite.update(CashFlowDb.REAL_DATA_TABLE, cv, where, whereArgs);

        return rowCount;
    }

    // update the integerData table
    public static long updateDataTable(Integer userId, String headerString, String fieldString, Integer value) {
        Double result;
        ContentValues cv = new ContentValues();

        Header header = makeHeader(headerString);
        Field field = makeField(fieldString);
        String where = CashFlowDb.INTEGER_DATA_USER_ID + " = ? AND " + CashFlowDb.INTEGER_DATA_HEADER_ID + " = ? AND " + CashFlowDb.INTEGER_DATA_FIELD_ID + " = ?";
        String[] whereArgs = {String.valueOf(userId), String.valueOf(header.getId()), String.valueOf(field.getId())};

        cv.put(CashFlowDb.INTEGER_DATA_VALUE, value);

        int rowCount = CashFlowDb.dbWrite.update(CashFlowDb.INTEGER_DATA_TABLE, cv, where, whereArgs);

        return rowCount;
    }
}
