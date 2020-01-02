package com.with.app.manage

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.with.app.manage.RecentSearchesHelper.Companion.DATABASE_NAME
import com.with.app.manage.RecentSearchesHelper.Companion.DATABASE_VERSION

class RecentViewsHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val TABLE_NAME = "views"
    private val COLUMN_ID = "_id"
    private val COLUMN_IDX = "idx"

    private val SQL_CREATE_ENTRIES =
        "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_IDX TEXT)"

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

    @SuppressLint("Recycle")
    @Throws(SQLiteConstraintException::class)
    fun insertView(idx: Int){
        val db = writableDatabase
        val values = ContentValues()
        values.put("idx", idx)
        db.insert(TABLE_NAME, null, values)
    }

    fun readView(): String {
        var result = ""
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT * FROM $TABLE_NAME GROUP BY $COLUMN_IDX ORDER BY $COLUMN_ID DESC", null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return result
        }

        var idx: String
        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                idx = cursor.getString(cursor.getColumnIndex(COLUMN_IDX))
                if (cursor.position != 5 && !cursor.isLast) result += "$idx+"
                else if (cursor.isLast || cursor.position == 5) {
                    result += idx
                    break
                }
                cursor.moveToNext()
            }
        }
        return result
    }
}