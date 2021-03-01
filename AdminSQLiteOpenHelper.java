package com.estecode.frutifru;                                  //para crar esta base de dato hay que crear en com.estecode.frutifru una nueva classe java y llamarla AdminSQLiteOpenHelper si o si

import android.content.Context;                                 //para importar la clase lo hace solo el ide. pero hay que crear los metodos oncreate
import android.database.sqlite.SQLiteDatabase;                  // y uponcreate despues sigue marcando error porque hay que crear el constructor
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BD) {
        BD.execSQL("create table puntaje (nombre text, score int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase BD, int oldVersion, int newVersion) {

    }
}
