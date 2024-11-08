package com.example.mov2_practica09

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.sql.SQLException

class RangerBD (context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(dataBase: SQLiteDatabase?) {
        val sql = "create table ranger (idranger int primary key, nombre text, apellido text, edad real, salario real)"
        try {
            dataBase?.execSQL(sql)
        } catch (e: SQLException) {
            Toast.makeText(null, "Error al crear la base de datos ranger", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}
}