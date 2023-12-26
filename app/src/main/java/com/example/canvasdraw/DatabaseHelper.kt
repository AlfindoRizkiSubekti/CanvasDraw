package com.example.canvasdraw

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "point_database"
        private const val TABLE_NAME = "points"
        private const val COLUMN_ID = "id"
        private const val COLUMN_X = "x"
        private const val COLUMN_Y = "y"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        createTable(db)
    }

    private fun createTable(db: SQLiteDatabase?) {
        val createTableQuery =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_X REAL, $COLUMN_Y REAL);"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        dropTable(db)
        createTable(db)
    }

    fun addPoint(x: Float, y: Float): Long {
        val db = this.writableDatabase
        
            val values = ContentValues().apply {
                put(COLUMN_X, x)
                put(COLUMN_Y, y)
            }
            val id = db.insert(TABLE_NAME, null, values)
            db.close()
            return id
        } 

    fun getAllPoints(): List<Point> {
        val points = mutableListOf<Point>()
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NAME, arrayOf(COLUMN_ID, COLUMN_X, COLUMN_Y), null, null, null, null, null)

        cursor.use {
            val idIndex = it.getColumnIndex(COLUMN_ID)
            val xIndex = it.getColumnIndex(COLUMN_X)
            val yIndex = it.getColumnIndex(COLUMN_Y)

            while (it.moveToNext()) {
                // Check if the column indices are valid
                if (idIndex != -1 && xIndex != -1 && yIndex != -1) {
                    val id = it.getLong(idIndex)
                    val x = it.getFloat(xIndex)
                    val y = it.getFloat(yIndex)
                    points.add(Point(id, x, y))
                } else {
                    // Handle the case where the columns are not found
                    // Log.e("DatabaseHelper", "Column not found in the result set")
                }
            }
        }

        db.close()
        return points
    }

    fun deleteTable() {
        val db = this.writableDatabase
        dropTable(db)
        createTable(db)
        db.close()
    }

    private fun dropTable(db: SQLiteDatabase?) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }
}