package com.panacloud.arif.android.sqlite;

/**
 * Created by Arif on 10/14/2014.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class BMIDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_RESULT };

    public BMIDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public BMIResult createBMIResult(String comment) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_RESULT, comment);
        long insertId = database.insert(MySQLiteHelper.TABLE_BMI_RESULTS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_BMI_RESULTS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        BMIResult newBMIResult = cursorToBMIResult(cursor);
        cursor.close();
        return newBMIResult;
    }

    public void deleteBMIResult(BMIResult BMIResult) {
        long id = BMIResult.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_BMI_RESULTS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<BMIResult> getAllBMIResults() {
        List<BMIResult> BMIResults = new ArrayList<BMIResult>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_BMI_RESULTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            BMIResult BMIResult = cursorToBMIResult(cursor);
            BMIResults.add(BMIResult);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return BMIResults;
    }

    private BMIResult cursorToBMIResult(Cursor cursor) {
        BMIResult BMIResult = new BMIResult();
        BMIResult.setId(cursor.getLong(0));
        BMIResult.setComment(cursor.getString(1));
        return BMIResult;
    }
}
