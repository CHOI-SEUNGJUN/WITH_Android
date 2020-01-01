package com.with.app.manage

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class RecentSearchesHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    val TABLE_NAME = "search"
    val COLUMN_ID = "_id"
    val COLUMN_KEYWORD = "keyword"

    private val SQL_CREATE_ENTRIES =
        "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_KEYWORD TEXT)"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertKeyword(keyword: String): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put("keyword", keyword)
        db.insert(TABLE_NAME, null, values)
        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteKeyword(keyword: String) {
        val db = writableDatabase
        val selection = "$COLUMN_KEYWORD LIKE ?"
        val selectionArgs = arrayOf(keyword)
        db.delete(TABLE_NAME, selection, selectionArgs)
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteAllKeyword(){
        val db = writableDatabase
        val sql = "DELETE FROM search"
        db.execSQL(sql)
    }

    fun readKeyword(): ArrayList<String> {
        val searches = ArrayList<String>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var keyword: String
        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                keyword = cursor.getString(cursor.getColumnIndex(COLUMN_KEYWORD))

                searches.add(keyword)
                cursor.moveToNext()
            }
        }
        return searches
    }

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "RecentSearches.db"
    }

}