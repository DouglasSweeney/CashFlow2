package com.doug.cashflow.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.doug.cashflow.controller.LoginLab;
import com.doug.cashflow.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CashFlowDb extends CashFlowDb2 {
    private static final String TAG = "CashFlowDb";

    public static final Integer MY_2017_doug_USER_ID = 1;

    private static final String DB_NAME = "cashFlow.db";
    private static final int    DB_VERSION = 8;

    // users table constants
    private static final String USER_TABLE = "users";

    private static final String USER_ID = "id";
    private static final int    USER_ID_COL = 0;

    private static final String USER_EMAIL = "email";
    private static final int    USER_EMAIL_COL = 1;

    private static final String USER_PASSWORD = "password";
    private static final int    USER_PASSWORD_COL = 2;

    private static final String CREATE_USERS_TABLE =
            "CREATE TABLE " + USER_TABLE + " (" +
                    USER_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_EMAIL + " TEXT NOT NULL UNIQUE, " +
                    USER_PASSWORD + " TEXT NOT NULL);";

    private static final String DROP_USER_TABLE =
            "DROP TABLE IF EXISTS " + USER_TABLE + ";";

    // headers table constants
    protected static final String HEADERS_TABLE = "headers";

    private static final String HEADERS_ID = "id";
    private static final int    HEADERS_ID_COL = 0;

    protected  static final String HEADERS_HEADER = "header";
    private static final int    HEADERS_HEADER_COL = 1;

    private static final String CREATE_HEADERS_TABLE =
            "CREATE TABLE " + HEADERS_TABLE + " (" +
                    HEADERS_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    HEADERS_HEADER + " TEXT NOT NULL);";

    protected  static final String DROP_HEADERS_TABLE =
            "DROP TABLE IF EXISTS " + HEADERS_TABLE + ";";

    // fields table constants
    protected static final String FIELDS_TABLE = "fields";

    private static final String FIELDS_ID = "id";
    private static final int    FIELDS_ID_COL = 0;

    protected static final String FIELDS_FIELD = "field";
    private static final int    FIELDS_HEADER_COL = 1;

    private static final String CREATE_FIELDS_TABLE =
            "CREATE TABLE " + FIELDS_TABLE + " (" +
                    FIELDS_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FIELDS_FIELD + " TEXT NOT NULL);";

    private static final String DROP_FIELDS_TABLE =
            "DROP TABLE IF EXISTS " + FIELDS_TABLE + ";";

    // dates table constants
    private static final String DATES_TABLE = "dates";

    private static final String DATES_ID = "id";
    private static final int    DATES_ID_COL = 0;

    private static final String DATES_USER_ID = "user_id";
    private static final int    DATES_USER_ID_COL = 1;

    private static final String DATES_FIELD = "field";
    private static final int    DATES_FIELD_COL = 2;

    private static final String DATES_DATE = "date";
    private static final int    DATES_DATE_COL = 3;

    private static final String CREATE_DATES_TABLE =
            "CREATE TABLE " + DATES_TABLE + " (" +
                    DATES_ID      + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DATES_USER_ID + " INTEGER NOT NULL, " +
                    DATES_FIELD   + " INTEGER NOT NULL, " +
                    DATES_DATE    + " TEXT NOT NULL, "    +
                    "CONSTRAINT multi_field UNIQUE (" + DATES_USER_ID + ", " + DATES_FIELD + ") ON CONFLICT ABORT);";

    private static final String DROP_DATES_TABLE =
            "DROP TABLE IF EXISTS " + DATES_TABLE + ";";

    // Real data table constants
    protected static final String REAL_DATA_TABLE = "realData";

    protected  static final String REAL_DATA_ID = "id";
    private static final int    REAL_DATA_ID_COL = 0;

    protected static final String REAL_DATA_USER_ID = "user_id";
    private static final int    REAL_DATA_USER_ID_COL = 1;

    private static final String REAL_DATA_HEADER = "header";
    private static final int    REAL_DATA_HEADER_COL = 2;

    protected  static final String REAL_DATA_HEADER_ID = "header_id";
    private static final int    REAL_DATA_HEADER_ID_COL = 3;

    private static final String REAL_DATA_FIELD = "field";
    private static final int    REAL_DATA_FIELD_COL = 4;

    protected  static final String REAL_DATA_FIELD_ID = "field_id";
    private static final int    REAL_DATA_FIELD_ID_COL = 5;

    protected  static final String REAL_DATA_VALUE = "value";
    private    static final int    REAL_DATA_VALUE_COL = 6;

    private static final String CREATE_REAL_DATA_TABLE =
            "CREATE TABLE " + REAL_DATA_TABLE + " (" +
                    REAL_DATA_ID         + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    REAL_DATA_USER_ID    + " INTEGER NOT NULL, " +
                    REAL_DATA_HEADER     + " TEXT NOT NULL, " +
                    REAL_DATA_HEADER_ID  + " INTEGER NOT NULL, " +
                    REAL_DATA_FIELD      + " TEXT NOT NULL, " +
                    REAL_DATA_FIELD_ID   + " INTEGER NOT NULL, " +
                    REAL_DATA_VALUE      + " REAL NOT NULL);";

    private static final String DROP_REAL_DATA_TABLE =
            "DROP TABLE IF EXISTS " + REAL_DATA_TABLE + ";";

    // Real data table constants
    protected static final String INTEGER_DATA_TABLE = "integerData";

    private static final String INTEGER_DATA_ID = "id";
    private static final int    INTEGER_DATA_ID_COL = 0;

    protected static final String INTEGER_DATA_USER_ID = "user_id";
    private   static final int    INTEGER_DATA_USER_ID_COL = 1;

    private static final String INTEGER_DATA_HEADER = "header";
    private static final int    INTEGER_DATA_HEADER_COL = 2;

    protected static final String INTEGER_DATA_HEADER_ID = "header_id";
    private   static final int    INTEGER_DATA_HEADER_ID_COL = 3;

    private static final String INTEGER_DATA_FIELD = "field";
    private static final int    INTEGER_DATA_FIELD_COL = 4;

    protected static final String INTEGER_DATA_FIELD_ID = "field_id";
    private   static final int    INTEGER_DATA_FIELD_ID_COL = 5;

    protected static final String INTEGER_DATA_VALUE = "value";
    private static final int    INTEGER_DATA_VALUE_COL = 6;

    private static final String CREATE_INTEGER_DATA_TABLE =
            "CREATE TABLE " + INTEGER_DATA_TABLE + " (" +
                    INTEGER_DATA_ID         + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    INTEGER_DATA_USER_ID    + " INTEGER NOT NULL, " +
                    INTEGER_DATA_HEADER     + " TEXT NOT NULL, " +
                    INTEGER_DATA_HEADER_ID  + " INTEGER NOT NULL, " +
                    INTEGER_DATA_FIELD      + " TEXT NOT NULL, " +
                    INTEGER_DATA_FIELD_ID   + " INTEGER NOT NULL, " +
                    INTEGER_DATA_VALUE      + " INTEGER NOT NULL);";

    private static final String DROP_INTEGER_DATA_TABLE =
            "DROP TABLE IF EXISTS " + INTEGER_DATA_TABLE + ";";

    protected static SQLiteDatabase dbRead;
    protected static SQLiteDatabase dbWrite;

    private static DBHelper dbHelper;

    private Context context;

    public CashFlowDb(Context context) {
        User user;

        this.context = context;

        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);

        setDatabasePointers();

user = getUser("2017_doug");
if (user == null) {
    user = new User();
    user.setEmail("2017_doug");
    user.setPassword("2017_doug");
    insertUser(dbHelper.getWritableDatabase(), user);
    user = getUser("2017_doug"); // get the id
    fillInDataBase(user.getId());
//    setDatabasePointers();
}
    }

    private static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context, String name, CursorFactory factory, int version){
            super (context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            Log.d(TAG, "Entering onCreate()");

            db.execSQL(CREATE_USERS_TABLE);
            db.execSQL(CREATE_HEADERS_TABLE);
            db.execSQL(CREATE_FIELDS_TABLE);
            db.execSQL(CREATE_DATES_TABLE);
            db.execSQL(CREATE_REAL_DATA_TABLE);
            db.execSQL(CREATE_INTEGER_DATA_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_USER_TABLE);
            db.execSQL(DROP_HEADERS_TABLE);
            db.execSQL(DROP_FIELDS_TABLE);
            db.execSQL(DROP_DATES_TABLE);
            db.execSQL(DROP_REAL_DATA_TABLE);
            db.execSQL(DROP_INTEGER_DATA_TABLE);

            onCreate(db);
        }
    }

//    public void openReadableDB() {
//       dbRead = dbHelper.getReadableDatabase();
//    }

//    public void openWritableDB() {
//        dbWrite = dbHelper.getWritableDatabase();
//    }

//    public void closeDB() {
//    }

    public void setDatabasePointers() {
        dbRead = dbHelper.getReadableDatabase();
        dbWrite = dbHelper.getWritableDatabase();
    }

    public void closeDatabasePointers() {
        if (dbRead != null) {
            dbRead.close();
            dbRead = null;
        }
        if (dbWrite != null) {
            dbWrite.close();
            dbWrite = null;
        }
    }

    public void fillInDataBase(int user_id) {
        if (getNumberOfRecordsInHeadersTable() == 0) {
            populateHeadersTable();
        }

        if (getNumberOfRecordsInFieldsTable() == 0) {
            populateFieldsTable();
        }

        if (getNumberOfRecordsInRealDataTable(user_id) == 0 &&
            getNumberOfRecordsInIntegerDataTable(user_id) == 0) {
            populateDataTable(user_id);
        }
    }

    public User getUser(String email) {
        String where = USER_EMAIL + " = ?";
        String[] whereArgs = {email};

        Cursor cursor = dbRead.query(USER_TABLE, null, where, whereArgs, null, null, null);
        cursor.moveToFirst();
        User user = getUserFromCursor(cursor);
        if (cursor != null)
            cursor.close();

        return user;
    }

    private static User getUserFromCursor(Cursor cursor) {

        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        else {
            try {
                User user = new User(
                        cursor.getInt(USER_ID_COL),
                        cursor.getString(USER_EMAIL_COL),
                        cursor.getString(USER_PASSWORD_COL)
                );
                return user;
            }
            catch (Exception e) {
                return null;
            }
        }
    }

    protected static Header getHeaderFromCursor(Cursor cursor) {
        Header header;

        if (cursor == null || cursor.getCount() == 0) {
            header = new Header();
        }
        else {
            try {
                header = new Header(
                        cursor.getInt(HEADERS_ID_COL),
                        cursor.getString(HEADERS_HEADER_COL)
                );
            }
            catch (Exception e) {
                header = new Header();
            }
        }

        return header;
    }

    protected static Integer getHeadersIdFromCursor(Cursor cursor) {
        Integer id;
        Header header;

        header = getHeaderFromCursor(cursor);
        if (header == null) {
            id = -1;
        }
        else {
            id=header.getId();
        }

        return id;
    }

    public static Field getFieldFromCursor(Cursor cursor) {
        Field field;

        if (cursor == null || cursor.getCount() == 0) {
            field = new Field();
        }
        else {
            try {
                field = new Field(
                        cursor.getInt(FIELDS_ID_COL),
                        cursor.getString(FIELDS_HEADER_COL)
                );
                return field;
            }
            catch (Exception e) {
                field = new Field();
            }
        }

        return field;
    }

    private static Integer getFieldsIdFromCursor(Cursor cursor) {
        Integer id;
        Field field;

        field = getFieldFromCursor(cursor);
        id=field.getId();

        return id;
    }

    private static MyDateString getDateFromCursor(Cursor cursor) {
        MyDateString date;

        if (cursor == null || cursor.getCount() == 0) {
            date = new MyDateString();
        }
        else {
            date = new MyDateString(
                    cursor.getInt(DATES_ID_COL),
                    cursor.getInt(DATES_USER_ID_COL),
                    cursor.getString(DATES_FIELD_COL),
                    cursor.getString(DATES_DATE_COL)
            );
        }

        return date;
    }

    private static String getDateStringFromCursor(Cursor cursor) {
        MyDateString date;
        String result;

        date = getDateFromCursor(cursor);
        result=date.getDate();

        return result;
    }

    private static IntegerData getIntegerDataFromCursor(Cursor cursor) {
        IntegerData integerData;

//        Log.d(TAG, "getIntegerDataFromCursor()");
//        Log.d(TAG, "    cursor.getCount(): " + cursor.getCount());

        if (cursor == null || cursor.getCount() == 0) {
            integerData = new IntegerData();
        }
        else {
            integerData = new IntegerData(
                    cursor.getInt(INTEGER_DATA_ID_COL),
                    cursor.getInt(INTEGER_DATA_USER_ID_COL),
                    cursor.getString(INTEGER_DATA_HEADER_COL),
                    cursor.getInt(INTEGER_DATA_HEADER_ID_COL),
                    cursor.getString(INTEGER_DATA_FIELD_COL),
                    cursor.getInt(INTEGER_DATA_FIELD_ID_COL),
                    cursor.getInt(INTEGER_DATA_VALUE_COL)
            );
        }

        return integerData;
    }

    private static Integer getIntegerValueFromCursor(Cursor cursor) {
        IntegerData integerData;
        Integer      result;

        integerData = getIntegerDataFromCursor(cursor);
        result=integerData.getValue();

        return result;
    }

    private static RealData getRealDataFromCursor(Cursor cursor) {
        RealData realData;

        if (cursor == null || cursor.getCount() == 0) {
            realData = new RealData();
        }
        else {
            realData = new RealData(
                    cursor.getInt(REAL_DATA_ID_COL),
                    cursor.getInt(REAL_DATA_USER_ID_COL),
                    cursor.getString(REAL_DATA_HEADER_COL),
                    cursor.getInt(REAL_DATA_HEADER_ID_COL),
                    cursor.getString(REAL_DATA_FIELD_COL),
                    cursor.getInt(REAL_DATA_FIELD_ID_COL),
                    cursor.getDouble(REAL_DATA_VALUE_COL)
            );
        }

        return realData;
    }

    private static Double getRealValueFromCursor(Cursor cursor) {
        RealData realData;
        Double   result;

        realData = getRealDataFromCursor(cursor);
        result=realData.getValue();

        return result;
    }

    public long insertUser(User user) {
        long rowID = 0;
        String sql = "INSERT INTO " + USER_TABLE + "(" + USER_EMAIL +", " + USER_PASSWORD + ") VALUES (?, ?)";

        SQLiteStatement stmt = dbWrite.compileStatement(sql);

        stmt.bindString(USER_EMAIL_COL, user.getEmail());
        stmt.bindString(USER_PASSWORD_COL, user.getPassword());

        rowID = stmt.executeInsert();

        return rowID;
    }

    public static void insertUser(SQLiteDatabase db, User user) {
        long rowID = 0;
        String sql = "INSERT INTO " + USER_TABLE + "(" + USER_EMAIL +", " + USER_PASSWORD + ") VALUES (?, ?)";

        SQLiteStatement stmt = db.compileStatement(sql);

        stmt.bindString(USER_EMAIL_COL, user.getEmail());
        stmt.bindString(USER_PASSWORD_COL, user.getPassword());

        rowID = stmt.executeInsert();

        return;
    }

    public long updateUser(User user) {
        ContentValues cv = new ContentValues();

        cv.put(USER_EMAIL, user.getEmail());
        cv.put(USER_PASSWORD, user.getPassword());

        String where = USER_ID + " = ? ";
        String whereArgs[] = { String.valueOf(user.getId()) };

        int rowCount = dbWrite.update(USER_TABLE, cv, where, whereArgs);

        return rowCount;
    }

    public long deleteUser(Integer userId) {
        int rowCount = 0;
        String where = USER_ID + " = ? ";
        String whereArgs[] = { String.valueOf(userId) };

//        rowCount = dbWrite.delete(USER_TABLE, where, whereArgs);
        rowCount = dbWrite.delete(USER_TABLE, where, whereArgs);

        return rowCount;
    }

    public long deleteUser(User user) {
        int rowCount = 0;
        String where = USER_EMAIL + " = ? AND " + USER_PASSWORD + "= ?";
        String whereArgs[] = { String.valueOf(user.getEmail()), String.valueOf(user.getPassword()) };

        rowCount = dbWrite.delete(USER_TABLE, where, whereArgs);

        return rowCount;
    }

    public long deleteDates(Integer userId) {
        int rowCount = 0;
        String where = DATES_USER_ID + " = ?";
        String whereArgs[] = { String.valueOf(userId) };

        rowCount = dbWrite.delete(DATES_TABLE, where, whereArgs);

        return rowCount;
    }

    public long deleteRealData(Integer userId) {
        int rowCount = 0;
        String where = REAL_DATA_USER_ID + " = ?";
        String whereArgs[] = { String.valueOf(userId) };

        rowCount = dbWrite.delete(REAL_DATA_TABLE, where, whereArgs);

        return rowCount;
    }

    public long deleteIntegerData(Integer userId) {
        int rowCount = 0;
        String where = INTEGER_DATA_USER_ID + " = ?";
        String whereArgs[] = { String.valueOf(userId) };

        rowCount = dbWrite.delete(INTEGER_DATA_TABLE, where, whereArgs);

        return rowCount;
    }

    public Integer getNumberOfRecordsInHeadersTable() {
        Integer result;

        Cursor cursor = dbRead.query(HEADERS_TABLE, null, null, null, null, null, null);
        result = cursor.getCount();
        if (cursor != null)
            cursor.close();

        return result;
    }

    public Integer getNumberOfRecordsInFieldsTable() {
        Integer result;

        Cursor cursor = dbRead.query(FIELDS_TABLE, null, null, null, null, null, null);
        result = cursor.getCount();
        if (cursor != null)
            cursor.close();

        return result;
    }

    public Integer getNumberOfRecordsInDatesTable() {
        Integer result;

        Cursor cursor = dbRead.query(DATES_TABLE, null, null, null, null, null, null);
        result = cursor.getCount();
        if (cursor != null)
            cursor.close();

        return result;
    }

    public Integer getNumberOfRecordsInUserTable() {
        Integer result;

        Cursor cursor = dbRead.query(USER_TABLE, null, null, null, null, null, null);
        result = cursor.getCount();
        if (cursor != null)
            cursor.close();

        return result;
    }

    public Integer getNumberOfRecordsInDatesTable(Integer userId) {
        Integer result;
        String where = DATES_USER_ID + " = ?";
        String[] whereArgs = {String.valueOf(userId)};

        Cursor cursor = dbRead.query(DATES_TABLE, null, where, whereArgs, null, null, null);

        result = cursor.getCount();
        if (cursor != null)
            cursor.close();

        return result;
    }

    public Integer getNumberOfRecordsInIntegerDataTable(Integer userId) {
        Integer result;
        String where = INTEGER_DATA_USER_ID + " = ?";
        String[] whereArgs = {String.valueOf(userId)};

        Cursor cursor = dbRead.query(INTEGER_DATA_TABLE, null, where, whereArgs, null, null, null);

        result = cursor.getCount();
        if (cursor != null)
            cursor.close();

        return result;
    }

    public Integer getNumberOfRecordsInIntegerDataTable() {
        Integer result;

        Cursor cursor = dbRead.query(INTEGER_DATA_TABLE, null, null, null, null, null, null);

        result = cursor.getCount();
        if (cursor != null)
            cursor.close();

        return result;
    }

    public Integer getNumberOfRecordsInRealDataTable(Integer userId) {
        Integer result;
        String where = REAL_DATA_USER_ID + " = ?";
        String[] whereArgs = {String.valueOf(userId)};

        Cursor cursor = dbRead.query(REAL_DATA_TABLE, null, where, whereArgs, null, null, null);
        result = cursor.getCount();
        if (cursor != null)
            cursor.close();

        return result;
    }

    public Integer getNumberOfRecordsInRealDataTable() {
        Integer result;

        Cursor cursor = dbRead.query(REAL_DATA_TABLE, null, null, null, null, null, null);

        result = cursor.getCount();

        if (cursor != null)
            cursor.close();

        return result;
    }

    private void putHeader(String header) {
        ContentValues cv = new ContentValues();

        cv.put(HEADERS_HEADER, header);

        long rowID = dbWrite.insert(HEADERS_TABLE, null, cv);

        return;
    }

    public void populateHeadersTable() {
        putHeader(context.getString(R.string.input_header_401k));
        putHeader(context.getString(R.string.input_header_403b));
        putHeader(context.getString(R.string.input_header_brokerage));
        putHeader(context.getString(R.string.input_header_cash_balance));
        putHeader(context.getString(R.string.input_header_deductions));
        putHeader(context.getString(R.string.input_header_expenses));
        putHeader(context.getString(R.string.input_header_ira_roth));
        putHeader(context.getString(R.string.input_header_ira_traditional));
        putHeader(context.getString(R.string.input_header_pension));
        putHeader(context.getString(R.string.input_header_personal));
        putHeader(context.getString(R.string.input_header_salary));
        putHeader(context.getString(R.string.input_header_savings));
        putHeader(context.getString(R.string.input_header_social_security));
        putHeader(context.getString(R.string.input_header_taxes));
    }

    private void putField(String field) {
        ContentValues cv = new ContentValues();

        cv.put(FIELDS_FIELD, field);

        long rowID = dbWrite.insert(FIELDS_TABLE, null, cv);

        return;
    }

    public void populateFieldsTable() {
        putField(context.getString(R.string.field_balance));
        putField(context.getString(R.string.field_contributions));
        putField(context.getString(R.string.field_growth_rate));
        putField(context.getString(R.string.field_number_of_withdrawals));
        putField(context.getString(R.string.field_start_withdrawals_age));
        putField(context.getString(R.string.field_yearly_deductions));
        putField(context.getString(R.string.field_annual_expenses));
        putField(context.getString(R.string.field_salary));
        putField(context.getString(R.string.field_merit_increase));
        putField(context.getString(R.string.field_federal_tax_rate));
        putField(context.getString(R.string.field_state_tax_rate));
        putField(context.getString(R.string.field_inflation_adjusted));
        putField(context.getString(R.string.field_starting_age));
        putField(context.getString(R.string.field_monthly_amount));
        putField(context.getString(R.string.field_retirement_age));
        putField(context.getString(R.string.field_life_expectancy_age));
        putField(context.getString(R.string.field_inflation));
    }


    private Date getDateFromString(String dateString)  {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

//        Log.d(TAG, "getDateFromString(String dateString))");
//        Log.d(TAG, "    dateString: " + dateString);

        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    private String getStringFromDate(Date date) {
        Calendar calendar;
        int month;
        int day;
        int year;
        String dateString;

        // create string of the date
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        String dayString = String.format("%02d", day);
        String monthString = String.format("%02d", month + 1);
        String yearString = String.format("%04d", year);
        dateString = new String(monthString + "/" + dayString + "/" + yearString);

        return dateString;
    }

    public void putDate(Integer userId, Integer field, Date date) {
//        String fieldString = LoginLab.getCurrentActivity().getResources().getString(field);
        String fieldString = context.getResources().getString(field);
        ContentValues cv = new ContentValues();
        String dateString;

//        Log.d(TAG, "<" + CREATE_DATES_TABLE + ">");

        dateString = getStringFromDate(date);

        cv.put(DATES_USER_ID, userId);
        cv.put(DATES_FIELD, fieldString);
        cv.put(DATES_DATE, dateString);

        long rowID = dbWrite.insert(DATES_TABLE, null, cv);

        return;
    }

    public Date getDate(Integer userId, Integer field) {
//        String fieldString = LoginLab.getCurrentActivity().getResources().getString(field);
        String fieldString = context.getResources().getString(field);
        String where = DATES_USER_ID + " = ? AND " + DATES_FIELD + " = ?";
        String[] whereArgs = {String.valueOf(userId), fieldString};
        Date date;

        Cursor cursor = dbRead.query(DATES_TABLE, null, where, whereArgs, null, null, null);
        cursor.moveToFirst();
        String dateString = getDateStringFromCursor(cursor);

        if (cursor != null)
            cursor.close();

//        Log.d(TAG, "CashFlowDb.java:getDate()");
//        Log.d(TAG, "      userId: " + userId);
//        Log.d(TAG, "      fieldString: <" + fieldString + ">");
//        Log.d(TAG, "      cursor.getCount(): " + cursor.getCount());
//se        Log.d(TAG, "      dateString: <" + dateString + ">");

        date = getDateFromString(dateString);

        return date;
    }

    public long updateDate(Integer userId, Integer field, Date date) {
//        Log.d(TAG, "updateDate(Integer user_id, Integer field, Date data)");

        ContentValues cv = new ContentValues();
        String dateString;
        String fieldString = LoginLab.getCurrentActivity().getResources().getString(field);

//        Log.d(TAG, "    fieldString: " + fieldString);

        String where = DATES_USER_ID + " = ? AND " + DATES_FIELD + " = ?";
        String[] whereArgs = {String.valueOf(userId), fieldString};

        dateString = getStringFromDate(date);

//        Log.d(TAG, "    dateString: " + dateString);

        cv.put(DATES_DATE, dateString);

        int rowCount = dbWrite.update(DATES_TABLE, cv, where, whereArgs);

        return rowCount;
    }

    protected Integer getHeaderId(String header) {
        String where = HEADERS_HEADER + " = ?";
        String[] whereArgs = {header};

        Cursor cursor = dbRead.query(HEADERS_TABLE, null, where, whereArgs, null, null, null);

        cursor.moveToFirst();
        Integer id = getHeadersIdFromCursor(cursor);

        if (cursor != null)
            cursor.close();

//        Log.d(TAG, "CashFlowDb.java:getHeaderId()");
//        Log.d(TAG, "      headerString: <" + header + ">");
//        Log.d(TAG, "      cursor.getCount(): " + cursor.getCount());
//        Log.d(TAG, "      id: " + id);

        return id;
    }

    protected Integer getFieldId(String field) {
        String where = FIELDS_FIELD + " = ?";
        String[] whereArgs = {field};


        Cursor cursor = dbRead.query(FIELDS_TABLE, null, where, whereArgs, null, null, null);

        cursor.moveToFirst();
        Integer id = getFieldsIdFromCursor(cursor);

        if (cursor != null)
            cursor.close();

//        Log.d(TAG, "CashFlowDb.java:getFieldId()");
//        Log.d(TAG, "      field: <" + field + ">");
//        Log.d(TAG, "      cursor.getCount(): " + cursor.getCount());
//        Log.d(TAG, "      id: " + id);

        return id;
    }

    public void insertRealDataTable(Integer userId, String header, Integer headerId, String field, Integer fieldId, double value) {
        ContentValues cv = new ContentValues();

        cv.put(REAL_DATA_USER_ID, userId);
        cv.put(REAL_DATA_HEADER, header);
        cv.put(REAL_DATA_HEADER_ID, headerId);
        cv.put(REAL_DATA_FIELD, field);
        cv.put(REAL_DATA_FIELD_ID, fieldId);
        cv.put(REAL_DATA_VALUE, value);

        long rowID = dbWrite.insert(REAL_DATA_TABLE, null, cv);

        return;
    }

    public void insertIntegerDataTable(Integer userId, String header, Integer headerId, String field, Integer fieldId, Integer value) {
        ContentValues cv = new ContentValues();

        cv.put(INTEGER_DATA_USER_ID, userId);
        cv.put(INTEGER_DATA_HEADER, header);
        cv.put(INTEGER_DATA_HEADER_ID, headerId);
        cv.put(INTEGER_DATA_FIELD, field);
        cv.put(INTEGER_DATA_FIELD_ID, fieldId);
        cv.put(INTEGER_DATA_VALUE, value);

        long rowID = dbWrite.insert(INTEGER_DATA_TABLE, null, cv);

        return;
    }

    private Date getBirthDate() {
        Date date;
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        try {
            date = format.parse("09/06/1958");
        }
        catch (Exception e) {
            date = new Date();
        }

        return date;

    }

    private void populateData(Integer userId) {
        String  header;
        Integer header_id;
        String  field;
        Integer field_id;

//        Log.d(TAG, "populateData(Integer userId) - ~line 813");

        // 401K
//        header = LoginLab.getCurrentActivity().getResources().getString(R.string.input_header_401k);
        header = context.getResources().getString(R.string.input_header_401k);
        header_id = getHeaderId(header);

//        field = LoginLab.getCurrentActivity().getResources().getString(R.string.field_balance);
        field = context.getResources().getString(R.string.field_balance);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 370000.0);

//        field = LoginLab.getCurrentActivity().getResources().getString(R.string.field_growth_rate);
        field = context.getResources().getString(R.string.field_growth_rate);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 4.0);

        field = context.getResources().getString(R.string.field_contributions);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 0.0);

        field = context.getResources().getString(R.string.field_start_withdrawals_age);
        field_id = getFieldId(field);
        insertIntegerDataTable(userId, header, header_id, field, field_id, 59);

        field = context.getResources().getString(R.string.field_number_of_withdrawals);
        field_id = getFieldId(field);
        insertIntegerDataTable(userId, header, header_id, field, field_id, 12);

        header = context.getResources().getString(R.string.input_header_403b);
        header_id = getHeaderId(header);

        field = context.getResources().getString(R.string.field_balance);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 0.0);

        field = context.getResources().getString(R.string.field_growth_rate);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 0.0);

        field = context.getResources().getString(R.string.field_contributions);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 0.0);

        field = context.getResources().getString(R.string.field_start_withdrawals_age);
        field_id = getFieldId(field);
        insertIntegerDataTable(userId, header, header_id, field, field_id, 0);

        field = context.getResources().getString(R.string.field_number_of_withdrawals);
        field_id = getFieldId(field);
        insertIntegerDataTable(userId, header, header_id, field, field_id, 0);

        header = context.getResources().getString(R.string.input_header_brokerage);
        header_id = getHeaderId(header);

        field = context.getResources().getString(R.string.field_balance);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 0.0);

        field = context.getResources().getString(R.string.field_growth_rate);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 0.0);

        header = context.getResources().getString(R.string.input_header_cash_balance);
        header_id = getHeaderId(header);

        field = context.getResources().getString(R.string.field_balance);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 0.0);

        field = context.getResources().getString(R.string.field_growth_rate);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 0.0);

        field = context.getResources().getString(R.string.field_contributions);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 0.0);

        field = context.getResources().getString(R.string.field_start_withdrawals_age);
        field_id = getFieldId(field);
        insertIntegerDataTable(userId, header, header_id, field, field_id, 0);

        field = context.getResources().getString(R.string.field_number_of_withdrawals);
        field_id = getFieldId(field);
        insertIntegerDataTable(userId, header, header_id, field, field_id, 0);

        header = context.getResources().getString(R.string.input_header_deductions);
        header_id = getHeaderId(header);

        field = context.getResources().getString(R.string.field_yearly_deductions);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 12000.0);

        header = context.getResources().getString(R.string.input_header_expenses);
        header_id = getHeaderId(header);

        field = context.getResources().getString(R.string.field_annual_expenses);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 50000.0);

        header = context.getResources().getString(R.string.input_header_ira_roth);
        header_id = getHeaderId(header);

        field = context.getResources().getString(R.string.field_balance);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 200000.0);

        field = context.getResources().getString(R.string.field_growth_rate);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 6.0);

        field = context.getResources().getString(R.string.field_contributions);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 0.0);

        field = context.getResources().getString(R.string.field_start_withdrawals_age);
        field_id = getFieldId(field);
        insertIntegerDataTable(userId, header, header_id, field, field_id, 0);

        field = context.getResources().getString(R.string.field_number_of_withdrawals);
        field_id = getFieldId(field);
        insertIntegerDataTable(userId, header, header_id, field, field_id, 0);

        header = context.getResources().getString(R.string.input_header_ira_traditional);
        header_id = getHeaderId(header);

        field = context.getResources().getString(R.string.field_balance);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 0.0);

        field = context.getResources().getString(R.string.field_growth_rate);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 0.0);

        field = context.getResources().getString(R.string.field_contributions);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 0.0);

        field = context.getResources().getString(R.string.field_start_withdrawals_age);
        field_id = getFieldId(field);
        insertIntegerDataTable(userId, header, header_id, field, field_id, 0);

        field = context.getResources().getString(R.string.field_number_of_withdrawals);
        field_id = getFieldId(field);
        insertIntegerDataTable(userId, header, header_id, field, field_id, 0);

        // Pension
        header = context.getResources().getString(R.string.input_header_pension);
        header_id = getHeaderId(header);

        field = context.getResources().getString(R.string.field_starting_age);
        field_id = getFieldId(field);
        insertIntegerDataTable(userId, header, header_id, field, field_id, 55);

        field = context.getResources().getString(R.string.field_monthly_amount);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 1700.0);

        field = context.getResources().getString(R.string.field_inflation_adjusted);
        field_id = getFieldId(field);
        insertIntegerDataTable(userId, header, header_id, field, field_id, 0); // false

        // Personal
        header = context.getResources().getString(R.string.input_header_personal);
        header_id = getHeaderId(header);

        Date simulationDate = new Date();
        putDate(userId, R.string.field_simulation_date, simulationDate);
        Date birthDate = getBirthDate();
        putDate(userId, R.string.field_birth_date, birthDate);

        field = context.getResources().getString(R.string.field_retirement_age);
        field_id = getFieldId(field);
        insertIntegerDataTable(userId, header, header_id, field, field_id, 53);

        field = context.getResources().getString(R.string.field_life_expectancy_age);
        field_id = getFieldId(field);
        insertIntegerDataTable(userId, header, header_id, field, field_id, 95);

        field = context.getResources().getString(R.string.field_inflation);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 2.8);

        // Salary
        header = context.getResources().getString(R.string.input_header_salary);
        header_id = getHeaderId(header);

        field = context.getResources().getString(R.string.field_salary);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 0.0);

        field = context.getResources().getString(R.string.field_merit_increase);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 0.0);

        // Savings
        header = context.getResources().getString(R.string.input_header_savings);
        header_id = getHeaderId(header);

        field = context.getResources().getString(R.string.field_balance);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 8500.0);

        field = context.getResources().getString(R.string.field_growth_rate);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 0.0);

        // Social Security
        header = context.getResources().getString(R.string.input_header_social_security);
        header_id = getHeaderId(header);

        field = context.getResources().getString(R.string.field_starting_age);
        field_id = getFieldId(field);
        insertIntegerDataTable(userId, header, header_id, field, field_id, 69);

        field = context.getResources().getString(R.string.field_monthly_amount);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 2300.00);

        // Taxes
        header = context.getResources().getString(R.string.input_header_taxes);
        header_id = getHeaderId(header);

        field = context.getResources().getString(R.string.field_federal_tax_rate);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 28.0);

        field = context.getResources().getString(R.string.field_state_tax_rate);
        field_id = getFieldId(field);
        insertRealDataTable(userId, header, header_id, field, field_id, 6.0);
    }

    public void populateDataTable(Integer userId) {

        populateData(userId);
    }

    public String getIntegerData(Integer user_id, String header, String field) {
        Integer result;
        Integer header_id = getHeaderId(header);
        Integer field_id = getFieldId(field);
        String where = INTEGER_DATA_USER_ID + " = ? AND " + INTEGER_DATA_HEADER_ID + " = ? AND " + INTEGER_DATA_FIELD_ID + " = ?";
        String[] whereArgs = {String.valueOf(user_id), String.valueOf(header_id), String.valueOf(field_id)};

        Cursor cursor = dbRead.query(INTEGER_DATA_TABLE, null, where, whereArgs, null, null, null);

        cursor.moveToFirst();
        result = getIntegerValueFromCursor(cursor);

//        Log.d(TAG, "getIntegerData():");
//        Log.d(TAG, "    user_id: " + user_id);
//        Log.d(TAG, "    header_id: " + header_id);
//        Log.d(TAG, "    field_id: " + field_id);
//        Log.d(TAG, "    cursor.getCount(): " + cursor.getCount());
//        Log.d(TAG, "    result: " + result.toString());

        cursor.close();

        return result.toString();
    }

    public String getRealData(Integer user_id, String header, String field) {
        Double result;
        Util util = new Util();
        Integer header_id = getHeaderId(header);
        Integer field_id = getFieldId(field);
        String where = REAL_DATA_USER_ID + " = ? AND " + REAL_DATA_HEADER_ID + " = ? AND " + REAL_DATA_FIELD_ID + " = ?";
        String[] whereArgs = {String.valueOf(user_id), String.valueOf(header_id), String.valueOf(field_id)};

        Cursor cursor = dbRead.query(REAL_DATA_TABLE, null, where, whereArgs, null, null, null);

        cursor.moveToFirst();
        result = getRealValueFromCursor(cursor);

        cursor.close();

        if (
            field.equals(context.getString(R.string.field_balance)) ||
            field.equals(context.getString(R.string.field_contributions)) ||
            field.equals(context.getString(R.string.field_yearly_deductions)) ||
            field.equals(context.getString(R.string.field_annual_expenses)) ||
            field.equals(context.getString(R.string.field_monthly_amount)) ||
            field.equals(context.getString(R.string.field_salary))
           )
            return util.getDollarFormat(result);
        else
        if (
                field.equals(context.getString(R.string.field_growth_rate)) ||
                field.equals(context.getString(R.string.field_inflation)) ||
                field.equals(context.getString(R.string.field_merit_increase)) ||
                field.equals(context.getString(R.string.field_federal_tax_rate)) ||
                field.equals(context.getString(R.string.field_state_tax_rate))
           )
            return util.getPercentFormat(result);

        return result.toString();
    }
}
