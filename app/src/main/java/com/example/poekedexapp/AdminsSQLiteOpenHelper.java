package com.example.poekedexapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * The type Admins sq lite open helper.
 */
public class AdminsSQLiteOpenHelper extends SQLiteOpenHelper {
    /**
     * Instantiates a new Admins sq lite open helper.
     *
     * @param context the context
     * @param name    the name
     * @param factory the factory
     * @param version the version
     */
    public AdminsSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE pokemons(id int primary key, name text, url text, sprite BLOB, color text, user text)");
        db.execSQL("CREATE TABLE pokemons_favs(id int primary key, name text, url text, sprite BLOB, color text, user text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
