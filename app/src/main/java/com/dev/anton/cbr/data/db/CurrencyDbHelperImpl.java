package com.dev.anton.cbr.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.dev.anton.cbr.data.exception.CursNotFoundException;
import com.dev.anton.cbr.data.model.CurrencyInfoEntity;
import com.dev.anton.cbr.data.model.CurrencyEntity;
import com.dev.anton.cbr.data.model.core.BaseError;
import com.dev.anton.cbr.data.model.core.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public class CurrencyDbHelperImpl extends SQLiteOpenHelper implements CurrencyDbHelper {

    //DATABASE INFO
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Curs.db";
    //BASE COMMAND
    private static final String CREATE_TABLE = "CREATE TABLE";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS";

    //TABLE CURRENCY INFO
    private static class CurrencyInfoEntry implements BaseColumns {
        static final String TABLE_NAME = "CurrencyInfo";
        static final String COLUMN_NAME_DATE = "Date";
        static final String COLUMN_NAME = "Name";
    }
    //COMMAND
    private static final String SQL_CREATE_CURRENCY_INFO =
            CREATE_TABLE + " " + CurrencyInfoEntry.TABLE_NAME + " (" +
                    CurrencyInfoEntry._ID + " INTEGER PRIMARY KEY," +
                    CurrencyInfoEntry.COLUMN_NAME_DATE + " TEXT," +
                    CurrencyInfoEntry.COLUMN_NAME + " TEXT)";
    private static final String SQL_DELETE_CURRENCY_INFO =
            DROP_TABLE + " " + CurrencyInfoEntry.TABLE_NAME;

    //TABLE CURRENCY
    private static class CurrencyEntry implements BaseColumns {
        static final String TABLE_NAME = "Currency";
        static final String COLUMN_NAME_ID_CURRENCY_INFO = "IdCurrencyInfo";
        static final String COLUMN_NAME_NUM_CODE = "NumCode";
        static final String COLUMN_NAME_CHAR_CODE = "CharCode";
        static final String COLUMN_NAME_NOMINAL = "Nominal";
        static final String COLUMN_NAME = "Name";
        static final String COLUMN_NAME_VALUE = "Value";
    }
    //COMMAND
    private static final String SQL_CREATE_CURRENCY =
            CREATE_TABLE + " " + CurrencyEntry.TABLE_NAME + " (" +
                    CurrencyEntry._ID + " TEXT PRIMARY KEY," +
                    CurrencyEntry.COLUMN_NAME_ID_CURRENCY_INFO + " INTEGER," +
                    CurrencyEntry.COLUMN_NAME_NUM_CODE + " INTEGER," +
                    CurrencyEntry.COLUMN_NAME_CHAR_CODE + " TEXT," +
                    CurrencyEntry.COLUMN_NAME_NOMINAL + " INTEGER," +
                    CurrencyEntry.COLUMN_NAME + " TEXT," +
                    CurrencyEntry.COLUMN_NAME_VALUE + " TEXT)";
    private static final String SQL_DELETE_CURRENCY =
            DROP_TABLE + " " + CurrencyEntry.TABLE_NAME;

    public CurrencyDbHelperImpl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CURRENCY_INFO);
        db.execSQL(SQL_CREATE_CURRENCY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_CURRENCY_INFO);
        db.execSQL(SQL_DELETE_CURRENCY);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private void insertValCurs(CurrencyInfoEntity curs) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CurrencyInfoEntry._ID, 1);
        values.put(CurrencyInfoEntry.COLUMN_NAME_DATE, curs.getDate());
        values.put(CurrencyInfoEntry.COLUMN_NAME, curs.getName());

        long valCursId = db.insert(CurrencyInfoEntry.TABLE_NAME, null, values);
        insertValuteList(curs.getCurrencyEntities(), valCursId);
    }

    private void insertValuteList(List<CurrencyEntity> valuteList, long valCursId) {
        for (CurrencyEntity valute : valuteList) {
            insertValute(valute, valCursId);
        }
    }

    private void insertValute(CurrencyEntity currencyEntity, long valCursId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CurrencyEntry._ID, currencyEntity.getId());
        values.put(CurrencyEntry.COLUMN_NAME_ID_CURRENCY_INFO, valCursId);
        values.put(CurrencyEntry.COLUMN_NAME_NUM_CODE, currencyEntity.getNumCode());
        values.put(CurrencyEntry.COLUMN_NAME_CHAR_CODE, currencyEntity.getCharCode());
        values.put(CurrencyEntry.COLUMN_NAME_NOMINAL, currencyEntity.getNominal());
        values.put(CurrencyEntry.COLUMN_NAME, currencyEntity.getName());
        values.put(CurrencyEntry.COLUMN_NAME_VALUE, currencyEntity.getValue());

        db.insert(CurrencyEntry.TABLE_NAME, null, values);
    }

    private CurrencyInfoEntity getValCurs() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + CurrencyInfoEntry.TABLE_NAME + " LIMIT 1";
        Cursor c = db.rawQuery(selectQuery, null);

        CurrencyInfoEntity curs = null;
        if (c != null && c.moveToFirst()) {
            curs = new CurrencyInfoEntity();
            curs.setName(c.getString(c.getColumnIndex(CurrencyInfoEntry.COLUMN_NAME)));
            curs.setDate(c.getString(c.getColumnIndex(CurrencyInfoEntry.COLUMN_NAME_DATE)));
            curs.setCurrencyEntities(getListValute(c.getInt(c.getColumnIndex(CurrencyEntry._ID))));
        }
        return curs;

    }

    private List<CurrencyEntity> getListValute(long valCursId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + CurrencyEntry.TABLE_NAME + " WHERE "
                + CurrencyEntry.COLUMN_NAME_ID_CURRENCY_INFO + " = " + valCursId;
        Cursor c = db.rawQuery(selectQuery, null);

        List<CurrencyEntity> valuteList = null;
        if (c.moveToFirst()) {
            valuteList = new ArrayList<>(25);
            do {
                CurrencyEntity valute = new CurrencyEntity();
                valute.setId(c.getString((c.getColumnIndex(CurrencyEntry._ID))));
                valute.setNumCode(c.getInt((c.getColumnIndex(CurrencyEntry.COLUMN_NAME_NUM_CODE))));
                valute.setCharCode(c.getString((c.getColumnIndex(CurrencyEntry.COLUMN_NAME_CHAR_CODE))));
                valute.setNominal(c.getInt((c.getColumnIndex(CurrencyEntry.COLUMN_NAME_NOMINAL))));
                valute.setName(c.getString((c.getColumnIndex(CurrencyEntry.COLUMN_NAME))));
                valute.setValue(c.getString((c.getColumnIndex(CurrencyEntry.COLUMN_NAME_VALUE))));
                valuteList.add(valute);
            } while (c.moveToNext());
        }
        return valuteList;
    }

    @Override
    public void clearCache() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + CurrencyInfoEntry.TABLE_NAME);
        db.execSQL("DELETE FROM " + CurrencyEntry.TABLE_NAME);
    }

    @Override
    public BaseResponse<CurrencyInfoEntity> getCurrencyInfo() { //mb replace to simple if
        try {
            return BaseResponse.success(getValCurs());
        } catch (Exception ignored) {
            return BaseResponse.error(new BaseError(new CursNotFoundException()));
        }
    }

    @Override
    public void saveCurrencyInfo(CurrencyInfoEntity curs) {
        if (isCached()) {
            clearCache();
        }
        insertValCurs(curs);
    }

    @Override
    public boolean isCached() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  count(*) FROM " + CurrencyInfoEntry.TABLE_NAME;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null && c.moveToFirst()) {
            return c.getInt(0) > 0;
        }
        return false;
    }

}
