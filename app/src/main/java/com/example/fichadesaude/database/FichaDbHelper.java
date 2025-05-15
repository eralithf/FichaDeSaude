package com.example.fichadesaude.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fichadesaude.model.FichaSaude;

import java.util.ArrayList;
import java.util.List;

public class FichaDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fichadesaude.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "fichas";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOME = "nome";
    private static final String COLUMN_IDADE = "idade";
    private static final String COLUMN_PESO = "peso";
    private static final String COLUMN_ALTURA = "altura";
    private static final String COLUMN_PRESSAO = "pressao";

    public FichaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NOME + " TEXT," +
                COLUMN_IDADE + " INTEGER," +
                COLUMN_PESO + " REAL," +
                COLUMN_ALTURA + " REAL," +
                COLUMN_PRESSAO + " TEXT" +
                ")";
        db.execSQL(CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public long inserirFicha(FichaSaude ficha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put(COLUMN_NOME, ficha.getNome());
        valores.put(COLUMN_IDADE, ficha.getIdade());
        valores.put(COLUMN_PESO, ficha.getPeso());
        valores.put(COLUMN_ALTURA, ficha.getAltura());
        valores.put(COLUMN_PRESSAO, ficha.getPressao());

        long id = db.insert(TABLE_NAME, null, valores);
        db.close();
        return id;
    }


    public FichaSaude getFichaById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_NOME, COLUMN_IDADE, COLUMN_PESO, COLUMN_ALTURA, COLUMN_PRESSAO},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            FichaSaude ficha = new FichaSaude(
                    cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IDADE)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PESO)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_ALTURA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRESSAO))
            );
            cursor.close();
            db.close();
            return ficha;
        } else {
            db.close();
            return null;
        }
    }


    public List<FichaSaude> getTodasFichas() {
        List<FichaSaude> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " DESC";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                FichaSaude ficha = new FichaSaude(
                        cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IDADE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PESO)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_ALTURA)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRESSAO))
                );
                lista.add(ficha);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }
}
