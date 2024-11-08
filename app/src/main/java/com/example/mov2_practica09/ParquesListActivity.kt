package com.example.mov2_practica09

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class ParquesListActivity : AppCompatActivity() {

    private lateinit var listaParques: ListView
    private lateinit var admin: ParquesBD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parques_list)

        listaParques = findViewById(R.id.listParques)
        admin = ParquesBD(this, "GestorParques.db", null, 1)

        // Obtener una instancia de la base de datos en modo lectura
        val bd = admin.readableDatabase
        val registro = bd.rawQuery("SELECT * FROM parque ORDER BY idpark", null)
        val parqueList = ArrayList<String>()

        // Revisar si hay registros y añadir cada parque al ArrayList
        if (registro.moveToFirst()) {
            do {
                val parque = "ID: ${registro.getString(0)}\n" +
                        "Nombre: ${registro.getString(1)}\n" +
                        "Tipo: ${registro.getString(2)}\n" +
                        "Tamaño: ${registro.getString(3)} m²\n" +
                        "Cantidad de árboles: ${registro.getString(4)} árboles"
                parqueList.add(parque)
            } while (registro.moveToNext())
        } else {
            // Mostrar un mensaje si no hay registros
            Toast.makeText(this, "Sin registro de parques", Toast.LENGTH_SHORT).show()
        }

        // Cerrar el cursor y la base de datos después de usarlos
        registro.close()
        bd.close()

        // Configurar el adaptador para el ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, parqueList)
        listaParques.adapter = adapter
    }
}
