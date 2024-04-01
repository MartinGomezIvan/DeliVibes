package com.example.delivibes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class Registrarse : AppCompatActivity() {
    private lateinit var meterNombre: EditText
    private lateinit var meterApellidos: EditText
    private lateinit var meterEmail: EditText
    private lateinit var meterPassword: EditText
    private lateinit var meterTelefono: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)

        meterNombre = findViewById(R.id.meterNombre)
        meterApellidos = findViewById(R.id.meterApellidos)
        meterPassword = findViewById(R.id.meterPassword)
        meterEmail = findViewById(R.id.meterEmail)
        meterTelefono = findViewById(R.id.meterTelefono)
    }

    fun PulsarBotonHecho(view: View) {
        var nombre=meterNombre.text.toString()
        var apellidos=meterApellidos.text.toString()
        var password=meterPassword.text.toString()
        var email=meterEmail.text.toString()
        var telefono=meterTelefono.text.toString()
        if (nombre.isEmpty() || apellidos.isEmpty() || password.isEmpty() || email.isEmpty() || telefono.isEmpty() ){
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
        }else if(password.length < 8 || password.none { it.isUpperCase() }){//Que la contraseña no tenga minimo 8 o ninguna mayúscula
            Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres y contener al menos una mayúscula", Toast.LENGTH_SHORT).show()
        }else{
            //Que me lleve a la pestaña de inicio
        }
    }
}