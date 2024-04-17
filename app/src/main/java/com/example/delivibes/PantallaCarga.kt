package com.example.delivibes

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView

class PantallaCarga : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_carga)
        val botonLogin = findViewById<Button>(R.id.botonLogin)
        val botonRegistro = findViewById<Button>(R.id.botonRegistro)


        // Cambia el color de fondo de los botones a naranja
        botonLogin.setBackgroundColor(Color.parseColor("#FFA500"))
        botonRegistro.setBackgroundColor(Color.parseColor("#FFA500"))
        window.statusBarColor = Color.parseColor("#162534")//Se cambia el color de la barra de estado (donde aparece el wifi, la batería...)



        // Agrega una animación de salto a los botones
        animarBoton(botonLogin)
        animarBoton(botonRegistro)

    }
    private fun animarBoton(boton: Button) {
        val animator = ObjectAnimator.ofFloat(boton, "translationY", 0f, -50f, 0f)
        animator.duration = 1000 // Duración de la animación en milisegundos
        animator.repeatCount = ObjectAnimator.INFINITE // Repetir la animación infinitamente
        animator.start()
    }

    private fun caerCubiertos(cubiertos: ImageView, cubiertos2: ImageView, cubiertos3: ImageView, cubiertos4: ImageView){
        //Hace la imagen visible.
        cubiertos.visibility = View.VISIBLE
        cubiertos2.visibility = View.VISIBLE
        cubiertos3.visibility = View.VISIBLE
        cubiertos4.visibility = View.VISIBLE

    }

    fun pulsarBotonRegistro(view: View) {
        val intent = Intent(this, Registrarse::class.java)
        startActivity(intent)
    }

    fun pulsarbotonInicio(view: View) {
        val intent = Intent(this, InicioSesion::class.java)
        startActivity(intent)
    }
}