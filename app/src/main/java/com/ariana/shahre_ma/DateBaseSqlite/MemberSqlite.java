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
public class MemberSqlite extends SQLiteOpenHelper{


    //Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "DBshahrma";

    // Books table name
    private static final String TABLE_MEMBER = "member";
    //Books Table Columns names
    private static final String ID = "id";
    private static final String NAME = "Name";
    private static final String EMAIL = "Email";
    private static final String MOBILE = "Mobile";
    private static final String AGE = "Age";
    private static final String SEX = "Sex";
    private static final String USERNAME = "UserName";
    private static final String PASSWORD = "Password";
    private static final String CITYID = "CityId";

    // SQL statement to create Member table
    private static final String CREATE_MEMBER_TABLE  = "CREATE TABLE  IF  NOT EXISTS " + TABLE_MEMBER + " (" +
            " Id INTEGER PRIMARY KEY," +
            " Name TEXT ," +
            " Email TEXT ," +
            " Mobile TEXT ," +
            " Age INTEGER ," +
            " Sex Boolean ," +
            " UserName TEXT ," +
            " Password TEXT ," +
            " CityId INTEGER " +
            ");";

    public  MemberSqlite(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public  void onCreate(SQLiteDatabase db)
    {
        // create books table
        db.execSQL(CREATE_MEMBER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS books");

        // create fresh books table
        this.onCreate(db);
    }

    public void Add(Integer id,String name,String email,String mobile,Integer age,Boolean sex,String username,String password,Integer cityid)
    {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(ID,id);
        values.put(NAME,name);
        values.put(EMAIL,email);
        values.put(MOBILE, mobile);
        values.put(AGE,age);
        values.put(SEX, sex);
        values.put(USERNAME,username);
        values.put(PASSWORD,password);
        values.put(CITYID,cityid);


        // 3. insert
        db.insert(TABLE_MEMBER, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }

    // Get All Books
    public List<String> getAllBooks() {
        List<String> books = new LinkedList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_MEMBER;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        //Book book = null;
        if (cursor.moveToFirst()) {
            do {
             /*  book = new Book();
                book.setId(Integer.parseInt(cursor.getString(0)));
                book.setTitle(cursor.getString(1));
                book.setAuthor(cursor.getString(2));*/

                // Add book to books
              //  books.add(book);
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
        int i=0 ;
     /* = db.update(TABLE_BOOKS_MEMBER, //table
                values, // column/value
                ID+" = ?", // selections
                new String[] { String.valueOf(book.getId()) }); //selection args*/

        // 4. close
        db.close();

        return i;

    }
}
