package de.hawhamburg.sea2.echargeTanke.library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * Created by Simon on 04.03.14.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "echarge";
    // Login table name
    private static final String TABLE_LOGIN = "login";
    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_FIRSTNAME = "fname";
    private static final String KEY_LASTNAME = "lname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_USERNAME = "uname";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_BIRTHDAY = "birthday";
    private static final String KEY_STREET = "street";
    private static final String KEY_NUMBER = "number";
    private static final String KEY_PLZ = "plz";
    private static final String KEY_CITY = "city";
    private static final String KEY_COUNTRY = "country";
    private static final String KEY_MOBILE = "mobile";


    // Constructor
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_FIRSTNAME + " TEXT,"
                + KEY_LASTNAME + " TEXT,"
                + KEY_BIRTHDAY + " TEXT,"
                + KEY_STREET + " TEXT,"
                + KEY_NUMBER + " TEXT,"
                + KEY_PLZ + " TEXT,"
                + KEY_CITY + " TEXT,"
                + KEY_COUNTRY + " TEXT,"
                + KEY_MOBILE + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_USERNAME + " TEXT,"
                + KEY_UID + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        // Create tables again
        onCreate(db);
    }
    /**
     * Storing user details in database
     * */
    public void addUser(String fname, String lname, String email, String birthday, String street, String number, String plz,
                        String city, String country, String mobile, String uname, String uid, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, fname); // FirstName
        values.put(KEY_LASTNAME, lname); // LastName
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_BIRTHDAY, birthday); // Geburtsdatum
        values.put(KEY_STREET, street); // Straße
        values.put(KEY_NUMBER, number); // Hausnummer
        values.put(KEY_PLZ, plz); // Postleitzahl
        values.put(KEY_CITY, city); // Stadt
        values.put(KEY_COUNTRY, country); // Land
        values.put(KEY_MOBILE, mobile); // Handynummer
        values.put(KEY_USERNAME, uname); // UserName
        values.put(KEY_UID, uid); // Email
        values.put(KEY_CREATED_AT, created_at); // Created At
        // Inserting Row
        db.insert(TABLE_LOGIN, null, values);
        db.close(); // Closing database connection
    }
    /**
     * Getting user data from database
     * */
    public HashMap getUserDetails(){
        HashMap user = new HashMap();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.put("fname", cursor.getString(cursor.getColumnIndex(KEY_FIRSTNAME)));
            user.put("lname", cursor.getString(cursor.getColumnIndex(KEY_LASTNAME)));
            user.put("birthday", cursor.getString(cursor.getColumnIndex(KEY_BIRTHDAY)));
            user.put("street", cursor.getString(cursor.getColumnIndex(KEY_STREET)));
            user.put("number", cursor.getString(cursor.getColumnIndex(KEY_NUMBER)));
            user.put("plz", cursor.getString(cursor.getColumnIndex(KEY_PLZ)));
            user.put("city", cursor.getString(cursor.getColumnIndex(KEY_CITY)));
            user.put("country", cursor.getString(cursor.getColumnIndex(KEY_COUNTRY)));
            user.put("mobile", cursor.getString(cursor.getColumnIndex(KEY_MOBILE)));
            user.put("email", cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
            user.put("uname", cursor.getString(cursor.getColumnIndex(KEY_USERNAME)));
            user.put("uid", cursor.getString(cursor.getColumnIndex(KEY_UID)));
            user.put("created_at", cursor.getString(cursor.getColumnIndex(KEY_CREATED_AT)));

        }
        cursor.close();
        db.close();
        // return user
        return user;
    }
    /**
     * Getting user login status
     * return true if rows are there in table
     * */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        // return row count
        return rowCount;
    }
    /**
     * Re create database
     * Delete all tables and create them again
     * */
    public void resetTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LOGIN, null, null);
        db.close();
    }
}
