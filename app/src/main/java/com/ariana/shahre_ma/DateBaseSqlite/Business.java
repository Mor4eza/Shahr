package com.ariana.shahre_ma.DateBaseSqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ariana on 6/1/2015.
 */
public class Business extends SQLiteOpenHelper {
    //Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "DBshahrma.db";
    // business table name
    private static final String TABLE_NAME_BUSINESS = "business";
    //business Table Columns names
    private static final String ID = "Id";
    private static final String MARKET = "Market";
    private static final String CODE = "Code";
    private static final String PHONE = "Phone";
    private static final String MOBILE = "Mobile";
    private static final String FAX = "Fax";
    private static final String EMAIL = "Email";
    private static final String BUSINESSOWNER = "BusinessOwner";
    private static final String ADDRESS = "Address";
    private static final String DESCRIPTION = "Description";
    private static final String STARTDATE = "Startdate";
    private static final String EXPIRATIONDATE = "ExpirationDate";
    private static final String INACTIVE = "Inactive";
    private static final String SUBSET = "Subset";
    private static final String SUBSETID = "SubsetId";
    private static final String LONGITUDE = "Longitude";
    private static final String LATITUDE = "Latitude";
    private static final String AREAID = "AreaId";
    private static final String AREA = "Area";
    private static final String USER = "User";
    private static final String USERID = "UserId";

    private static final String CREATE_TABLE_Business = "CREATE TABLE  IF  NOT EXISTS " + TABLE_NAME_BUSINESS + " (" +
            " Id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " Market TEXT ," +
            " Code TEXT ," +
            " Phone TEXT ," +
            " Mobile TEXT ," +
            " Fax TEXT ," +
            " Email TEXT ," +
            " BusinessOwner TEXT ," +
            " Address TEXT ," +
            " Description TEXT ," +
            " Startdate TEXT ," +
            " ExpirationDate TEXT ," +
            " Inactive TEXT ," +
            " Subset TEXT ," +
            " SubsetId INTEGER ," +
            " Longitude TEXT," +
            " Latitude TEXT," +
            " AreaId INTEGER ," +
            " Area TEXT ," +
            " User TEXT ," +
            " UserId INTEGER " +
            ");";

    public Business(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_BOOK_TABLE = "";

        // create books table
        db.execSQL(CREATE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS books");

        // create fresh books table
        this.onCreate(db);
    }

    public void Add(String market, String code, String phone, String mobile, String fax, String email, String businessowner, String address, String description, String startdate, String expirationdate, String inactive, String subset, Integer subsetid, String longitude, String latitude, Integer areaid, String area, String user, Integer userid) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(MARKET, market);
        values.put(CODE, code);
        values.put(PHONE, phone);
        values.put(MOBILE, mobile);
        values.put(FAX, fax);
        values.put(EMAIL, email);
        values.put(BUSINESSOWNER, businessowner);
        values.put(ADDRESS, address);
        values.put(DESCRIPTION, description);
        values.put(STARTDATE, startdate);
        values.put(EXPIRATIONDATE, expirationdate);
        values.put(INACTIVE, inactive);
        values.put(SUBSET, subset);
        values.put(SUBSETID, subsetid);
        values.put(LONGITUDE, longitude);
        values.put(LATITUDE, latitude);
        values.put(AREAID, areaid);
        values.put(AREA, area);
        values.put(USER, user);
        values.put(USERID, userid);


        // 3. insert
        db.insert(TABLE_NAME_BUSINESS, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }

    // Get All Books
    public List<String> getAllBooks() {
        List<String> books = new LinkedList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_NAME_BUSINESS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
       // Book book = null;
        if (cursor.moveToFirst()) {
            do {
             /*  book = new Book();
                book.setId(Integer.parseInt(cursor.getString(0)));
                book.setTitle(cursor.getString(1));
                book.setAuthor(cursor.getString(2));*/

                // Add book to books
            //    books.add(book);
            } while (cursor.moveToNext());
        }

        Log.d("getAllBooks()", books.toString());

        // return books
        return books;
    }

    // Deleting single book
    public void delete(Integer id) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        // db.delete(TABLE_BOOKS_MEMBER,ID," =",id);

        // 3. close
        db.close();

        // Log.d("deleteBook", book.toString());

    }

    // Updating single book
    public int update(Integer id) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        //values.put("title", book.getTitle()); // get title
        //values.put("author", book.getAuthor()); // get author

        // 3. updating row
        int i = 0;
     /* = db.update(TABLE_BOOKS_MEMBER, //table
                values, // column/value
                ID+" = ?", // selections
                new String[] { String.valueOf(book.getId()) }); //selection args*/

        // 4. close
        db.close();

        return i;

    }
}
