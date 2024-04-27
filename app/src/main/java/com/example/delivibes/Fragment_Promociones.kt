package com.example.delivibes

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout

class Fragment_Promociones : Fragment() {
    private lateinit var bocadillo: LinearLayout
    private lateinit var btnOcultar: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__promociones, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bocadillo = view.findViewById(R.id.bocadillo)
        btnOcultar = view.findViewById(R.id.btnOcultar)
        btnOcultar.setBackgroundColor(Color.parseColor("#FFA500"))//Color del botón en naranja


        // Mostrar el bocadillo al entrar en la pantalla
        mostrarBocadillo();

        // Configurar el botón "ocultar"
        btnOcultar.setOnClickListener {
            ocultarBocadillo()
        }
}
    private fun mostrarBocadillo() {
        bocadillo.visibility = View.VISIBLE
    }

    private fun ocultarBocadillo() {
        bocadillo.visibility = View.GONE
    }
}