package com.example.delivibes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast

class InicioSesion : AppCompatActivity() {
    private lateinit var meterEmail: EditText
    private lateinit var meterPassword: EditText
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_sesion)
         meterEmail = findViewById(R.id.email)
         meterPassword = findViewById(R.id.password)
        val botonAcceder = findViewById<Button>(R.id.button)
        progressBar = findViewById(R.id.progressBar)


        // Agrego un TextWatcher para los EditText, para después controlar el progressbar
        meterEmail.addTextChangedListener(textWatcher)
        meterPassword.addTextChangedListener(textWatcher)
    }
    // Defino un TextWatcher para controlar los cambios en los EditText
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            actualizarProgreso()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    // Actualiza el progreso del ProgressBar
    private fun actualizarProgreso() {
        val email = meterEmail.text.toString().trim()
        val password = meterPassword.text.toString().trim()
        val totalCampos = 2
        var camposCompletados = 0

        // Verifica si el campo de correo electrónico no está vacío
        if (email.isNotEmpty()) {
            camposCompletados++
        }

        // Verifica si el campo de contraseña no está vacío
        if (password.isNotEmpty()) {
            camposCompletados++
        }

        // Calcula el progreso en porcentaje
        val progreso = (camposCompletados * 100) / totalCampos

        // Actualiza el progreso del ProgressBar
        progressBar.progress = progreso
    }



    fun pulsarBotonAcceder(view: View) {
        val email= meterEmail.text.toString()
        val password= meterPassword.text.toString()
        if (email.isEmpty()|| password.isEmpty()){
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
        }else{//Luego cambiar esto para compararlo con la base de datos
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }
    }
}