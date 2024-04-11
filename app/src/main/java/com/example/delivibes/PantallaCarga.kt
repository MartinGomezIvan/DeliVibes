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
        val cubiertos = findViewById<ImageView>(R.id.comidaImageView)
        val cubiertos2 = findViewById<ImageView>(R.id.comidaImageView3)
        val cubiertos3 = findViewById<ImageView>(R.id.comidaImageView4)
        val cubiertos4 = findViewById<ImageView>(R.id.comidaImageView5)

        // Cambia el color de fondo de los botones a naranja
        botonLogin.setBackgroundColor(Color.parseColor("#FFA500"))
        botonRegistro.setBackgroundColor(Color.parseColor("#FFA500"))



        // Agrega una animación de salto a los botones
        animarBoton(botonLogin)
        animarBoton(botonRegistro)

        //Agrega la imagen a al función
        caerCubiertos(cubiertos, cubiertos2, cubiertos3, cubiertos4)
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
        val animatorCubierto = ObjectAnimator.ofFloat(cubiertos, "translationY", -200f, 2000f)
        val animatorCubierto2 = ObjectAnimator.ofFloat(cubiertos2, "translationY", -200f, 2000f)
        val animatorCubierto3 = ObjectAnimator.ofFloat(cubiertos3, "translationY", 2000f, -200f)
        val animatorCubierto4 = ObjectAnimator.ofFloat(cubiertos4, "translationY", 2000f, -200f)

        // Configurar duración y repetición de la animación
        val duration = 40000L
        val repeatCount = ObjectAnimator.INFINITE
        animatorCubierto.duration = duration
        animatorCubierto2.duration = duration
        animatorCubierto3.duration = duration
        animatorCubierto4.duration = duration
        animatorCubierto.repeatCount = repeatCount
        animatorCubierto2.repeatCount = repeatCount
        animatorCubierto3.repeatCount = repeatCount
        animatorCubierto4.repeatCount = repeatCount

        // Iniciar las animaciones
        animatorCubierto.start()
        animatorCubierto2.start()
        animatorCubierto3.start()
        animatorCubierto4.start()
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