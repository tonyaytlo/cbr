package com.dev.anton.cbr.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.dev.anton.cbr.data.exception.CursNotFoundException;
import com.dev.anton.cbr.data.model.ValCursEntity;
import com.dev.anton.cbr.data.model.ValuteEntity;
import com.dev.anton.cbr.data.model.core.BaseError;
import com.dev.anton.cbr.data.model.core.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public class CursDbHelper extends SQLiteOpenHelper implements DbHelper {

    //DATABASE INFO
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Curs.db";
    //BASE COMMAND
    private static final String CREATE_TABLE = "CREATE TABLE";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS";

    //TABLE VAL CURS
    private static class ValCursEntry implements BaseColumns {
        static final String TABLE_NAME = "ValCurs";
        static final String COLUMN_NAME_DATE = "Date";
        static final String COLUMN_NAME = "Name";
    }

    private static final String SQL_CREATE_VAL_CURS =
            CREATE_TABLE + " " + ValCursEntry.TABLE_NAME + " (" +
                    ValCursEntry._ID + " INTEGER PRIMARY KEY," +
                    ValCursEntry.COLUMN_NAME_DATE + " TEXT," +
                    ValCursEntry.COLUMN_NAME + " TEXT)";

    private static final String SQL_DELETE_VAL_CURS =
            DROP_TABLE + " " + ValCursEntry.TABLE_NAME;

    //TABLE VALUTE
    private static class ValuteEntry implements BaseColumns {
        static final String TABLE_NAME = "Valute";
        static final String COLUMN_NAME_ID_VAL_CURS = "IdValCurs";
        static final String COLUMN_NAME_NUM_CODE = "NumCode";
        static final String COLUMN_NAME_CHAR_CODE = "CharCode";
        static final String COLUMN_NAME_NOMINAL = "Nominal";
        static final String COLUMN_NAME = "Name";
        static final String COLUMN_NAME_VALUE = "Value";
    }

    private static final String SQL_CREATE_VALUTE =
            CREATE_TABLE + " " + ValuteEntry.TABLE_NAME + " (" +
                    ValuteEntry._ID + " TEXT PRIMARY KEY," +
                    ValuteEntry.COLUMN_NAME_ID_VAL_CURS + " INTEGER," +
                    ValuteEntry.COLUMN_NAME_NUM_CODE + " INTEGER," +
                    ValuteEntry.COLUMN_NAME_CHAR_CODE + " TEXT," +
                    ValuteEntry.COLUMN_NAME_NOMINAL + " INTEGER," +
                    ValuteEntry.COLUMN_NAME + " TEXT," +
                    ValuteEntry.COLUMN_NAME_VALUE + " TEXT)";

    private static final String SQL_DELETE_VALUTE =
            DROP_TABLE + " " + ValuteEntry.TABLE_NAME;

    public CursDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_VAL_CURS);
        db.execSQL(SQL_CREATE_VALUTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_VAL_CURS);
        db.execSQL(SQL_DELETE_VALUTE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private void insertValCurs(ValCursEntity curs) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ValCursEntry._ID, 1);
        values.put(ValCursEntry.COLUMN_NAME_DATE, curs.getDate());
        values.put(ValCursEntry.COLUMN_NAME, curs.getName());

        long valCursId = db.insert(ValCursEntry.TABLE_NAME, null, values);
        insertValuteList(curs.getValuteEntities(), valCursId);
    }

    private void insertValuteList(List<ValuteEntity> valuteList, long valCursId) {
        for (ValuteEntity valute : valuteList) {
            insertValute(valute, valCursId);
        }
    }

    private void insertValute(ValuteEntity valuteEntity, long valCursId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ValuteEntry._ID, valuteEntity.getId());
        values.put(ValuteEntry.COLUMN_NAME_ID_VAL_CURS, valCursId);
        values.put(ValuteEntry.COLUMN_NAME_NUM_CODE, valuteEntity.getNumCode());
        values.put(ValuteEntry.COLUMN_NAME_CHAR_CODE, valuteEntity.getCharCode());
        values.put(ValuteEntry.COLUMN_NAME_NOMINAL, valuteEntity.getNominal());
        values.put(ValuteEntry.COLUMN_NAME, valuteEntity.getName());
        values.put(ValuteEntry.COLUMN_NAME_VALUE, valuteEntity.getValue());

        db.insert(ValuteEntry.TABLE_NAME, null, values);
    }

    private ValCursEntity getValCurs() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + ValCursEntry.TABLE_NAME + " LIMIT 1";
        Cursor c = db.rawQuery(selectQuery, null);

        ValCursEntity curs = null;
        if (c != null && c.moveToFirst()) {
            curs = new ValCursEntity();
            curs.setName(c.getString(c.getColumnIndex(ValCursEntry.COLUMN_NAME)));
            curs.setDate(c.getString(c.getColumnIndex(ValCursEntry.COLUMN_NAME_DATE)));
            curs.setValuteEntities(getListValute(c.getInt(c.getColumnIndex(ValuteEntry._ID))));
        }
        return curs;

    }

    private List<ValuteEntity> getListValute(long valCursId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + ValuteEntry.TABLE_NAME + " WHERE "
                + ValuteEntry.COLUMN_NAME_ID_VAL_CURS + " = " + valCursId;
        Cursor c = db.rawQuery(selectQuery, null);

        List<ValuteEntity> valuteList = null;
        if (c.moveToFirst()) {
            valuteList = new ArrayList<>(25);
            do {
                ValuteEntity valute = new ValuteEntity();
                valute.setId(c.getString((c.getColumnIndex(ValuteEntry._ID))));
                valute.setNumCode(c.getInt((c.getColumnIndex(ValuteEntry.COLUMN_NAME_NUM_CODE))));
                valute.setCharCode(c.getString((c.getColumnIndex(ValuteEntry.COLUMN_NAME_CHAR_CODE))));
                valute.setNominal(c.getInt((c.getColumnIndex(ValuteEntry.COLUMN_NAME_NOMINAL))));
                valute.setName(c.getString((c.getColumnIndex(ValuteEntry.COLUMN_NAME))));
                valute.setValue(c.getString((c.getColumnIndex(ValuteEntry.COLUMN_NAME_VALUE))));
                valuteList.add(valute);
            } while (c.moveToNext());
        }
        return valuteList;
    }

    @Override
    public void clearCache() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + ValCursEntry.TABLE_NAME);
        db.execSQL("DELETE FROM " + ValuteEntry.TABLE_NAME);
    }

    @Override
    public BaseResponse<ValCursEntity> getCurs() { //mb replace to simple if
        try {
            return BaseResponse.success(getValCurs());
        } catch (Exception ignored) {
            return BaseResponse.error(new BaseError(new CursNotFoundException()));
        }
    }

    @Override
    public void saveCurs(ValCursEntity curs) {
        if (isCached()) {
            clearCache();
        }
        insertValCurs(curs);
    }

    @Override
    public boolean isCached() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  count(*) FROM " + ValCursEntry.TABLE_NAME;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null && c.moveToFirst()) {
            return c.getInt(0) > 0;
        }
        return false;
    }

}
