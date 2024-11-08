package com.example.mov2_practica09

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    private lateinit var usuario: EditText
    private lateinit var contrasena: EditText


    private lateinit var btnIngresar: Button
    private lateinit var btnGuardar: Button
    private lateinit var btnSalir: Button

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usuario = findViewById(R.id.edtUsuario)
        contrasena = findViewById(R.id.edtContrasena)

        btnIngresar = findViewById(R.id.btnIngresar)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnSalir = findViewById(R.id.btnSalir)

        sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)

        // Cargar datos guardados si el usuario eligió guardar
        cargarDatosGuardados()



        btnIngresar.setOnClickListener {
            ingresar()
        }

        btnGuardar.setOnClickListener {
            guardar()
        }

        btnSalir.setOnClickListener {
            finish()
        }
    }

    private fun cargarDatosGuardados() {
        val savedUser = sharedPreferences.getString("usuario", "")
        val savedPassword = sharedPreferences.getString("contrasena", "")
        if (!savedUser.isNullOrEmpty() && !savedPassword.isNullOrEmpty()) {
            usuario.setText(savedUser)
            contrasena.setText(savedPassword)
        }
    }

    private fun ingresar() {
        val user = usuario.text.toString()
        val password = contrasena.text.toString()

        if (user.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese usuario y contraseña", Toast.LENGTH_SHORT).show()
        } else {
            val savedUser = sharedPreferences.getString("usuario", null)
            val savedPassword = sharedPreferences.getString("contrasena", null)

            if (user == savedUser && password == savedPassword) {
                // Usuario y contraseña coinciden, acceso permitido
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Bienvenido de nuevo", Toast.LENGTH_SHORT).show()
            } else {
                // Usuario o contraseña incorrectos
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun guardar() {
        val user = usuario.text.toString()
        val password = contrasena.text.toString()

        if (user.isNotEmpty() && password.isNotEmpty()) {
            val editor = sharedPreferences.edit()
            editor.putString("usuario", user)
            editor.putString("contrasena", password)
            editor.apply()
            Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Usuario o contraseña vacíos", Toast.LENGTH_SHORT).show()
        }
    }
}
