package com.catviet.android.translation.data.resource.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.catviet.android.translation.data.model.Translate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ducvietho on 27/04/2018.
 */

public class TranslateDataHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "translate.db";
    public static final String TABLE_NAME = "translate";
    public static final String NAME_COLUMN = "name";
    public static final String TEXT_COLUMN = "text";
    public static final String CODE_COLUMN = "code";
    public static final String IMAGE_COLUMN = "image";
    public static final String TYPE_COLUMN = "type";
    public static final String TYPE_TRANSLATE_COLUMN = "type_translate";
    private static final  String SQL_CREATE_TRANSLATE = " CREATE TABLE "
            +TABLE_NAME
            +"("
            + BaseColumns._ID
            +" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +NAME_COLUMN
            +" TEXT,"
            +TEXT_COLUMN
            +" TEXT,"
            +IMAGE_COLUMN
            +" INTEGER,"
            +TYPE_COLUMN
            +" INTEGER,"
            +TYPE_TRANSLATE_COLUMN
            +" INTEGER,"
            + CODE_COLUMN
            +" TEXT"
            +")";
    private static final String SQL_DROP_LANGUAGES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
    public TranslateDataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TRANSLATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DROP_LANGUAGES);
        sqLiteDatabase.execSQL(SQL_CREATE_TRANSLATE);
    }
    public void insert(Translate translate,int type){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_COLUMN,translate.getLanguage());
        contentValues.put(CODE_COLUMN,translate.getCode());
        contentValues.put(TEXT_COLUMN,translate.getText());
        contentValues.put(IMAGE_COLUMN,translate.getImage());
        contentValues.put(TYPE_COLUMN,translate.getType());
        contentValues.put(TYPE_TRANSLATE_COLUMN,type);
        database.insert(TABLE_NAME,null,contentValues);
        database.close();
    }
    public List<Translate> getDataText(){
        SQLiteDatabase database = getReadableDatabase();
        String query = "Select * from "+TABLE_NAME+" where "+TYPE_TRANSLATE_COLUMN+"=0";
        Cursor cursor = database.rawQuery(query,null);
        List<Translate> list = new ArrayList<>();
        while (cursor.moveToNext()){
            Translate translate = cursorTranslate(cursor);
            list.add(translate);
        }
        return list;
    }
    public List<Translate> getDataCamera(){
        SQLiteDatabase database = getReadableDatabase();
        String query = "Select * from "+TABLE_NAME+" where "+TYPE_TRANSLATE_COLUMN+"=1";
        Cursor cursor = database.rawQuery(query,null);
        List<Translate> list = new ArrayList<>();
        while (cursor.moveToNext()){
            Translate translate = cursorTranslate(cursor);
            list.add(translate);
        }
        return list;
    }
    public List<Translate> getDataVoice(){
        SQLiteDatabase database = getReadableDatabase();
        String query = "Select * from "+TABLE_NAME+" where "+TYPE_TRANSLATE_COLUMN+"=2";
        Cursor cursor = database.rawQuery(query,null);
        List<Translate> list = new ArrayList<>();
        while (cursor.moveToNext()){
            Translate translate = cursorTranslate(cursor);
            list.add(translate);
        }
        return list;
    }
    public Translate cursorTranslate(Cursor cursor){
        Translate translate = new Translate();
        translate.setCode(cursor.getString(cursor.getColumnIndex(CODE_COLUMN)));
        translate.setImage(cursor.getInt(cursor.getColumnIndex(IMAGE_COLUMN)));
        translate.setLanguage(cursor.getString(cursor.getColumnIndex(NAME_COLUMN)));
        translate.setText(cursor.getString(cursor.getColumnIndex(TEXT_COLUMN)));
        translate.setType(cursor.getInt(cursor.getColumnIndex(TYPE_COLUMN)));
       return translate;
    }
}
