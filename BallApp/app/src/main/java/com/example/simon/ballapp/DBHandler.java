package com.example.simon.ballapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandler extends SQLiteOpenHelper
{

    private static final String DATABASE_NAME = "users";
    private static final String TABLE_USER = "user";
    private static final String COLOUMN_ID = "_id";
    private static final String COLOUMN_NAME = "username";
    private static final String COLOUMN_SCORE = "score";

    private static final int DATABASE_VERSION = 1;

    public DBHandler(Context context, SQLiteDatabase.CursorFactory factory)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    // Use for creating the database table
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query = "CREATE TABLE " + TABLE_USER + "(" + COLOUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLOUMN_NAME + " TEXT," + COLOUMN_SCORE + " INTEGER)";
        db.execSQL(query);
    }

    // Called when the database is upgraded (the DATABASE_VERSION is changed)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop the table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER + ";");
        // Create a new table
        onCreate(db);
    }

    // Used to add new user to the table
    public void adduser(User user)
    {
        // // put the variables into ContentValue
        ContentValues values = new ContentValues();
        values.put(COLOUMN_NAME, user.getUsername());
        values.put(COLOUMN_SCORE, user.getScore());

        // Establish writable connection to the db
        SQLiteDatabase db = getWritableDatabase();
        // Insert the values into the TABLE_USER
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    // Used to delete a user from the tabel with a given name
    public void deleteuser(String name)
    {
        // Establish writable connection to the db
        SQLiteDatabase db = getWritableDatabase();
        // Delete from TABLE
        db.execSQL("DELETE FROM " + TABLE_USER + " WHERE " + COLOUMN_NAME + "=\"" + name + "\";");
    }

    // Used to clear the table (for testing purposes)
    public void clearTable()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_USER);
        Log.i("LOG", "Log: Table cleared");
        Log.i("LOG", "Log: \n" + this.ToString(false));
    }

    // Used to set score on an already inserted user (no longer in use)
    public void setScore(String name, int score)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_USER + " SET " + COLOUMN_SCORE + " = " + (score + getScore(name)) + " WHERE " + COLOUMN_NAME + "=\"" + name + "\";");
        db.close();
        ToString(false);
    }

    // Used to get the score of a user in the database
    public int getScore(String name)
    {
        // Establish writable connection to the db
        SQLiteDatabase db = getWritableDatabase();
        // Set score to 0
        int score = 0;
        // Select from the table where name = given name
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLOUMN_NAME + "=\"" + name + "\";";
        // Create a cursor
        Cursor c = db.rawQuery(query, null);
        // Move the cursor to first in table
        c.moveToFirst();

        // if the current column is not empty
        if (c.getString(c.getColumnIndex(COLOUMN_NAME)) != null)
        {
            // Set score to column value
            score = c.getInt(c.getColumnIndex(COLOUMN_SCORE));
        }

        // return the found score
        return score;
    }

    // Used to print content of the table
    public String ToString(boolean formatTheString)
    {
        Log.i("LOG", "Log - Content of database: " + TABLE_USER + "\n");
        // Establish writable connection to the db
        SQLiteDatabase db = getWritableDatabase();
        // Setup result
        String result = "";
        // Select from table ordered by SCORE and limit to 10 (ie. get top 10 scores)
        String query = "SELECT * FROM " + TABLE_USER + " WHERE 1 ORDER BY " + COLOUMN_SCORE + " DESC LIMIT 10;";
        // Setup cursor
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();


        // While the cursor has not reached the last element returned by the query
        while (!c.isAfterLast())
        {
            if (c.getString(c.getColumnIndex(COLOUMN_NAME)) != null)
            {
                // If the string is not supposed to be formatted for displaying to the user
                if (!formatTheString)
                {
                    result += "Username: " + c.getString(c.getColumnIndex(COLOUMN_NAME)) + "\t\tScore: " + c.getString(c.getColumnIndex(COLOUMN_SCORE)) + "\n";
                }
                else
                {
                    if (c.getInt(c.getColumnIndex(COLOUMN_SCORE)) > 0)
                    {
                        result += c.getInt(c.getColumnIndex(COLOUMN_ID)) + " " + c.getString(c.getColumnIndex(COLOUMN_NAME)) + ": " + c.getString(c.getColumnIndex(COLOUMN_SCORE)) + "\n";
                    }
                }
            }

            // Move to the next element
            c.moveToNext();
        }

        db.close();

        return result;
    }
}
