package com.dev.anton.cbr.data.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.dev.anton.cbr.data.exception.CurrencyInfoNotFoundException;
import com.dev.anton.cbr.data.model.CurrencyEntity;
import com.dev.anton.cbr.data.model.CurrencyInfoEntity;
import com.dev.anton.cbr.data.model.core.BaseError;
import com.dev.anton.cbr.data.model.core.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public class CurrencyDbHelperImpl extends SQLiteOpenHelper implements CurrencyDbHelper {

    //DATABASE INFO
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Currency.db";
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

    private void insertCurrencyInfo(CurrencyInfoEntity currencyInfoEntity) {
        SQLiteDatabase db = this.getWritableDatabase();
        long currencyInfoId = -1;

        db.beginTransaction();
        try {
            currencyInfoId = db.insert(CurrencyInfoEntry.TABLE_NAME, null,
                    prepareCurrencyInfoCV(currencyInfoEntity));
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }

        if (currencyInfoId != -1 && currencyInfoEntity.getCurrencyEntities() != null &&
                !currencyInfoEntity.getCurrencyEntities().isEmpty()) {
            insertCurrencyList(currencyInfoEntity.getCurrencyEntities(), currencyInfoId);
        }
    }


    private ContentValues prepareCurrencyInfoCV(CurrencyInfoEntity curs) {
        ContentValues values = new ContentValues();
        values.put(CurrencyInfoEntry._ID, 1);
        values.put(CurrencyInfoEntry.COLUMN_NAME_DATE, curs.getDate());
        values.put(CurrencyInfoEntry.COLUMN_NAME, curs.getName());
        return values;
    }

    private void insertCurrencyList(final List<CurrencyEntity> currencyEntityList, long currencyInfoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (CurrencyEntity currencyEntity : currencyEntityList) {
                db.insert(CurrencyEntry.TABLE_NAME, null,
                        prepareCurrencyCV(currencyEntity, currencyInfoId));
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    private void insertCurrency(final CurrencyEntity currencyEntity, long currencyInfoId) {
        insertCurrencyList(new ArrayList<CurrencyEntity>(1) {{
            add(currencyEntity);
        }}, currencyInfoId);
    }

    private ContentValues prepareCurrencyCV(CurrencyEntity currencyEntity, long currencyInfoId) {
        ContentValues values = new ContentValues();
        values.put(CurrencyEntry._ID, currencyEntity.getId());
        values.put(CurrencyEntry.COLUMN_NAME_ID_CURRENCY_INFO, currencyInfoId);
        values.put(CurrencyEntry.COLUMN_NAME_NUM_CODE, currencyEntity.getNumCode());
        values.put(CurrencyEntry.COLUMN_NAME_CHAR_CODE, currencyEntity.getCharCode());
        values.put(CurrencyEntry.COLUMN_NAME_NOMINAL, currencyEntity.getNominal());
        values.put(CurrencyEntry.COLUMN_NAME, currencyEntity.getName());
        values.put(CurrencyEntry.COLUMN_NAME_VALUE, currencyEntity.getValue());
        return values;
    }

    private CurrencyInfoEntity readCurrencyInfo() {
        String selectQuery = "SELECT  * FROM " + CurrencyInfoEntry.TABLE_NAME + " LIMIT 1";
        CurrencyInfoEntity currencyInfo = null;

        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor c = db.rawQuery(selectQuery, null)) {
            if (c != null && c.moveToFirst()) {
                currencyInfo = new CurrencyInfoEntity();
                currencyInfo.setName(c.getString(c.getColumnIndex(CurrencyInfoEntry.COLUMN_NAME)));
                currencyInfo.setDate(c.getString(c.getColumnIndex(CurrencyInfoEntry.COLUMN_NAME_DATE)));
                currencyInfo.setCurrencyEntities(readCurrencyList(c.getInt(c.getColumnIndex(CurrencyEntry._ID))));
            }
        }
        return currencyInfo;
    }

    private List<CurrencyEntity> readCurrencyList(long currencyInfoId) {
        String selectQuery = "SELECT  * FROM " + CurrencyEntry.TABLE_NAME + " WHERE "
                + CurrencyEntry.COLUMN_NAME_ID_CURRENCY_INFO + " = " + currencyInfoId;
        List<CurrencyEntity> currencyEntityList = null;

        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor c = db.rawQuery(selectQuery, null)) {
            if (c != null && c.moveToFirst()) {
                currencyEntityList = new ArrayList<>(25);
                do {
                    CurrencyEntity currencyEntity = new CurrencyEntity();
                    currencyEntity.setId(c.getString((c.getColumnIndex(CurrencyEntry._ID))));
                    currencyEntity.setNumCode(c.getInt((c.getColumnIndex(CurrencyEntry.COLUMN_NAME_NUM_CODE))));
                    currencyEntity.setCharCode(c.getString((c.getColumnIndex(CurrencyEntry.COLUMN_NAME_CHAR_CODE))));
                    currencyEntity.setNominal(c.getInt((c.getColumnIndex(CurrencyEntry.COLUMN_NAME_NOMINAL))));
                    currencyEntity.setName(c.getString((c.getColumnIndex(CurrencyEntry.COLUMN_NAME))));
                    currencyEntity.setValue(c.getString((c.getColumnIndex(CurrencyEntry.COLUMN_NAME_VALUE))));
                    currencyEntityList.add(currencyEntity);
                } while (c.moveToNext());
            }
        }
        return currencyEntityList;
    }

    @Override
    public void clearCache() {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.execSQL("DELETE FROM " + CurrencyInfoEntry.TABLE_NAME);
            db.execSQL("DELETE FROM " + CurrencyEntry.TABLE_NAME);
        }
    }

    @Override
    public BaseResponse<CurrencyInfoEntity> getCurrencyInfo() {
        CurrencyInfoEntity currencyInfoEntity = null;
        try {
            currencyInfoEntity = readCurrencyInfo();
            if (currencyInfoEntity == null) {
                throw new NullPointerException();
            }
        } catch (NullPointerException npe) {
            return BaseResponse.error(new BaseError(new CurrencyInfoNotFoundException(npe)));
        } catch (Exception e) {
            return BaseResponse.error(new BaseError(new CurrencyInfoNotFoundException(e)));
        }
        return BaseResponse.success(currencyInfoEntity);
    }

    @Override
    public void saveCurrencyInfo(CurrencyInfoEntity currencyInfoEntity) {
        if (currencyInfoEntity == null) {
            return;
        }
        if (isCached()) {
            clearCache();
        }
        insertCurrencyInfo(currencyInfoEntity);
    }

    @Override
    public boolean isCached() {
        String selectQuery = "SELECT  count(*) FROM " + CurrencyInfoEntry.TABLE_NAME;
        boolean isCached;
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor c = db.rawQuery(selectQuery, null)) {
            isCached = c.moveToFirst() && c.getInt(0) > 0;
        }
        return isCached;
    }

}
