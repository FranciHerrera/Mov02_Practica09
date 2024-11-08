package com.example.mov2_practica09

import android.content.ContentValues
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.sql.SQLException

class RangerActivity : AppCompatActivity() {

    private lateinit var edtNombre: EditText
    private lateinit var edtApellido: EditText
    private lateinit var edtEdad: EditText
    private lateinit var edtSalario: EditText
    private lateinit var edtId: EditText

    private lateinit var admin: RangerBD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranger)

        // Inicialización de vistas
        edtNombre = findViewById(R.id.edtNombre_ranger)
        edtApellido = findViewById(R.id.edtApellido_ranger)
        edtEdad = findViewById(R.id.edtEdad_ranger)
        edtSalario = findViewById(R.id.edtSalario_ranger)
        edtId = findViewById(R.id.edtId_ranger)

        val btnAgregar: ImageButton = findViewById(R.id.btnAgregar_ranger)
        val btnBuscar: ImageButton = findViewById(R.id.btnBuscar_ranger)
        val btnEditar: ImageButton = findViewById(R.id.btnEditar_ranger)
        val btnEliminar: ImageButton = findViewById(R.id.btnEliminar_ranger)

        admin = RangerBD(this, "GestorRanger.db", null, 1)

        // Asignación de listeners
        btnAgregar.setOnClickListener { agregarRanger() }
        btnBuscar.setOnClickListener { buscarRanger() }
        btnEditar.setOnClickListener { editarRanger() }
        btnEliminar.setOnClickListener { eliminarRanger() }
    }

    private fun agregarRanger() {
        val db = admin.writableDatabase
        val id = edtId.text.toString()
        val nombre = edtNombre.text.toString()
        val apellido = edtApellido.text.toString()
        val edad = edtEdad.text.toString()
        val salario = edtSalario.text.toString()

        if (id.isNotEmpty() && nombre.isNotEmpty() && apellido.isNotEmpty() && edad.isNotEmpty() && salario.isNotEmpty()) {
            val registro = ContentValues().apply {
                put("idranger", id.toIntOrNull() ?: 0)
                put("nombre", nombre)
                put("apellido", apellido)
                put("edad", edad.toIntOrNull() ?: 0)
                put("salario", salario.toIntOrNull() ?: 0)
            }
            try {
                db.insert("ranger", null, registro)
                Toast.makeText(this, "Guardabosques registrado", Toast.LENGTH_SHORT).show()
                limpiarCampos()
            } catch (e: SQLException) {
                Toast.makeText(this, "Error al registrar al guardabosques", Toast.LENGTH_SHORT).show()
            } finally {
                db.close()
            }
        } else {
            Toast.makeText(this, "Debes ingresar todos los datos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun buscarRanger() {
        val id = edtId.text.toString()
        if (id.isNotEmpty()) {
            val db = admin.readableDatabase
            val cursor = db.rawQuery("SELECT nombre, apellido, edad, salario FROM ranger WHERE idranger=?", arrayOf(id))

            if (cursor.moveToFirst()) {
                edtNombre.setText(cursor.getString(0))
                edtApellido.setText(cursor.getString(1))
                edtEdad.setText(cursor.getString(2))
                edtSalario.setText(cursor.getString(3))
                Toast.makeText(this, "Guardabosques encontrado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Guardabosques no encontrado", Toast.LENGTH_SHORT).show()
                limpiarCampos()
            }
            cursor.close()
            db.close()
        } else {
            Toast.makeText(this, "Ingrese un ID válido", Toast.LENGTH_SHORT).show()
        }
    }

    private fun editarRanger() {
        val db = admin.writableDatabase
        val id = edtId.text.toString()
        val nombre = edtNombre.text.toString()
        val apellido = edtApellido.text.toString()
        val edad = edtEdad.text.toString()
        val salario = edtSalario.text.toString()

        if (id.isNotEmpty() && nombre.isNotEmpty() && apellido.isNotEmpty() && edad.isNotEmpty() && salario.isNotEmpty()) {
            val registro = ContentValues().apply {
                put("nombre", nombre)
                put("apellido", apellido)
                put("edad", edad.toIntOrNull() ?: 0)
                put("salario", salario.toIntOrNull() ?: 0)
            }
            val rowsUpdated = db.update("ranger", registro, "idranger=?", arrayOf(id))
            db.close()
            if (rowsUpdated > 0) {
                Toast.makeText(this, "Guardabosques actualizado", Toast.LENGTH_SHORT).show()
                limpiarCampos()
            } else {
                Toast.makeText(this, "Guardabosques no encontrado", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun eliminarRanger() {
        val db = admin.writableDatabase
        val id = edtId.text.toString()

        if (id.isNotEmpty()) {
            val rowsDeleted = db.delete("ranger", "idranger=?", arrayOf(id))
            db.close()
            if (rowsDeleted > 0) {
                Toast.makeText(this, "Guardabosques eliminado", Toast.LENGTH_SHORT).show()
                limpiarCampos()
            } else {
                Toast.makeText(this, "Guardabosques no encontrado", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Ingrese un ID válido", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiarCampos() {
        edtNombre.setText("")
        edtApellido.setText("")
        edtEdad.setText("")
        edtSalario.setText("")
        edtId.setText("")
        edtId.requestFocus()
    }
}
