package com.example.mov2_practica09

import android.content.ContentValues
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.sql.SQLException

class ParquesActivity : AppCompatActivity() {

    private lateinit var nombre_park: EditText
    private lateinit var metros_park: EditText
    private lateinit var cantidad_park: EditText
    private lateinit var numId: EditText

    private lateinit var tipoBosque_park: RadioButton
    private lateinit var tipoProtegido_park: RadioButton
    private lateinit var tipoNacional_park: RadioButton

    private lateinit var agregar_park: ImageButton
    private lateinit var buscar_park: ImageButton
    private lateinit var editar_park: ImageButton
    private lateinit var eliminar_park: ImageButton

    private lateinit var admin: ParquesBD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parques)

        // Inicialización de vistas
        nombre_park = findViewById(R.id.edtNombre_park)
        metros_park = findViewById(R.id.edtMetros_park)
        cantidad_park = findViewById(R.id.edtCantidad_park)
        numId = findViewById(R.id.edtNumero_park)

        tipoBosque_park = findViewById(R.id.rbtnBosque)
        tipoProtegido_park = findViewById(R.id.rbtnProtegido)
        tipoNacional_park = findViewById(R.id.rbtnNacional)

        agregar_park = findViewById(R.id.btnAgregar_park)
        buscar_park = findViewById(R.id.btnBuscar_park)
        editar_park = findViewById(R.id.btnEditar_park)
        eliminar_park = findViewById(R.id.btnEliminar_park)

        admin = ParquesBD(this, "GestorParques.db", null, 1)

        // Asignación de listeners
        agregar_park.setOnClickListener { registrarParque() }
        buscar_park.setOnClickListener { buscarParque() }
        editar_park.setOnClickListener { actualizarParque() }
        eliminar_park.setOnClickListener { eliminarParque() }
    }

    private fun obtenerTipoParque(): String {
        return when {
            tipoBosque_park.isChecked -> "Bosque"
            tipoProtegido_park.isChecked -> "Protegido"
            tipoNacional_park.isChecked -> "Nacional"
            else -> ""
        }
    }

    private fun registrarParque() {
        val bd = admin.writableDatabase
        val id = numId.text.toString()
        val nombre = nombre_park.text.toString()
        val tipo = obtenerTipoParque()
        val metros = metros_park.text.toString()
        val cantidad = cantidad_park.text.toString()

        if (id.isNotEmpty() && nombre.isNotEmpty() && tipo.isNotEmpty() && metros.isNotEmpty() && cantidad.isNotEmpty()) {
            val registro = ContentValues().apply {
                put("idpark", id.toIntOrNull() ?: 0)
                put("nombre", nombre)
                put("tipo", tipo)
                put("metros", metros.toFloatOrNull() ?: 0f)
                put("arboles", cantidad.toIntOrNull() ?: 0)
            }
            try {
                bd.insert("parque", null, registro)
                Toast.makeText(this, "Parque registrado", Toast.LENGTH_SHORT).show()
            } catch (e: SQLException) {
                Toast.makeText(this, "Error al registrar parque", Toast.LENGTH_SHORT).show()
            } finally {
                bd.close()
            }
            limpiarCampos()
        } else {
            Toast.makeText(this, "Debes ingresar todos los datos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun buscarParque() {
        val bd = admin.readableDatabase
        val id = numId.text.toString()

        if (id.isNotEmpty()) {
            val fila = bd.rawQuery("SELECT nombre, tipo, metros, arboles FROM parque WHERE idpark=?", arrayOf(id))
            if (fila.moveToFirst()) {
                nombre_park.setText(fila.getString(0))
                when (fila.getString(1)) {
                    "Bosque" -> tipoBosque_park.isChecked = true
                    "Protegido" -> tipoProtegido_park.isChecked = true
                    "Nacional" -> tipoNacional_park.isChecked = true
                }
                metros_park.setText(fila.getString(2))
                cantidad_park.setText(fila.getString(3))
            } else {
                Toast.makeText(this, "Parque no encontrado", Toast.LENGTH_SHORT).show()
                limpiarCampos()
            }
            fila.close()
        } else {
            Toast.makeText(this, "Ingresa el ID del parque", Toast.LENGTH_SHORT).show()
        }
        bd.close()
    }

    private fun actualizarParque() {
        val bd = admin.writableDatabase
        val id = numId.text.toString()
        val nombre = nombre_park.text.toString()
        val tipo = obtenerTipoParque()
        val metros = metros_park.text.toString()
        val cantidad = cantidad_park.text.toString()

        if (id.isNotEmpty() && nombre.isNotEmpty() && tipo.isNotEmpty() && metros.isNotEmpty() && cantidad.isNotEmpty()) {
            val registro = ContentValues().apply {
                put("nombre", nombre)
                put("tipo", tipo)
                put("metros", metros.toFloatOrNull() ?: 0f)
                put("arboles", cantidad.toIntOrNull() ?: 0)
            }
            val rowsUpdated = bd.update("parque", registro, "idpark=?", arrayOf(id))
            bd.close()
            if (rowsUpdated > 0) {
                Toast.makeText(this, "Parque actualizado", Toast.LENGTH_SHORT).show()
                limpiarCampos()
            } else {
                Toast.makeText(this, "El parque no existe", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Debes ingresar todos los datos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun eliminarParque() {
        val bd = admin.writableDatabase
        val id = numId.text.toString()

        if (id.isNotEmpty()) {
            val rowsDeleted = bd.delete("parque", "idpark=?", arrayOf(id))
            bd.close()
            if (rowsDeleted > 0) {
                Toast.makeText(this, "Parque eliminado", Toast.LENGTH_SHORT).show()
                limpiarCampos()
            } else {
                Toast.makeText(this, "El parque no existe", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Ingresa el ID del parque", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiarCampos() {
        nombre_park.setText("")
        tipoBosque_park.isChecked = false
        tipoProtegido_park.isChecked = false
        tipoNacional_park.isChecked = false
        metros_park.setText("")
        cantidad_park.setText("")
        numId.setText("")
        numId.requestFocus()
    }
}