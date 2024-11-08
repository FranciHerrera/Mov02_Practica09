package com.example.mov2_practica09

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class RangerListActivity : AppCompatActivity() {

    private lateinit var listaRanger: ListView
    private lateinit var admin: RangerBD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranger_list)

        listaRanger = findViewById(R.id.listRanger)
        admin = RangerBD(this, "GestorRanger.db", null, 1)

        // Obtener una instancia de la base de datos en modo lectura
        val bd = admin.readableDatabase
        val registro = bd.rawQuery("SELECT * FROM ranger ORDER BY idranger", null)
        val rangerList = ArrayList<String>()

        // Revisar si hay registros y añadir cada parque al ArrayList
        if (registro.moveToFirst()) {
            do {
                val parque = "ID: ${registro.getString(0)}\n" +
                        "Nombre: ${registro.getString(1)}\n" +
                        "Apellido: ${registro.getString(2)}\n" +
                        "Edad: ${registro.getString(3)} años\n" +
                        "Sueldo: ${registro.getString(4)} pesos"
                rangerList.add(parque)
            } while (registro.moveToNext())
        } else {
            // Mostrar un mensaje si no hay registros
            Toast.makeText(this, "Sin registro de guardabosques", Toast.LENGTH_SHORT).show()
        }

        // Cerrar el cursor y la base de datos después de usarlos
        registro.close()
        bd.close()

        // Configurar el adaptador para el ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, rangerList)
        listaRanger.adapter = adapter
    }
}