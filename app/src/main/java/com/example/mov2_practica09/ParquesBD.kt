package com.example.mov2_practica09

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class ParquesBD(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(dataBase: SQLiteDatabase?) {
        val sql = "create table parque (idpark int primary key, nombre text, tipo text, metros real, arboles integer )"
        try {
            dataBase?.execSQL(sql)
        } catch (e: SQLException) {
            Toast.makeText(null, "Error al crear la base de datos parque", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}
}