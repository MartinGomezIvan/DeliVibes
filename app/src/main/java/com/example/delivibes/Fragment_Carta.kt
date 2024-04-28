package com.example.delivibes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
class Fragment_Carta : Fragment() {
    private lateinit var spinner: Spinner
    private lateinit var cardView1: CardView
    private lateinit var cardView2: CardView
    private lateinit var cardView3: CardView
    lateinit var imagen1: ImageView
    private lateinit var imagen2: ImageView
    private lateinit var imagen3: ImageView
    private lateinit var titulo1: TextView
    private lateinit var titulo2: TextView
    private lateinit var titulo3: TextView
    private lateinit var texto1: TextView
    private lateinit var texto2: TextView
    private lateinit var texto3: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment__carta, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spinner = view.findViewById(R.id.spinner)
        cardView1 = view.findViewById(R.id.cardView1)
        cardView2 = view.findViewById(R.id.cardView2)
        cardView3 = view.findViewById(R.id.cardView3)
        imagen1 = view.findViewById(R.id.imagen1)
        imagen2 = view.findViewById(R.id.imagen2)
        imagen3 = view.findViewById(R.id.imagen3)
        titulo1 = view.findViewById(R.id.Titulo1)
        titulo2 = view.findViewById(R.id.Titulo2)
        titulo3 = view.findViewById(R.id.Titulo3)
        texto1 = view.findViewById(R.id.Texto1)
        texto2 = view.findViewById(R.id.Texto2)
        texto3 = view.findViewById(R.id.Texto3)

// Configurar el Spinner con las opciones
        val opciones = arrayOf("Primer plato", "Segundo plato", "Postre")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opciones)
        spinner.adapter = adapter
        val selec = spinner.selectedItem.toString()
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val selec: String = opciones[position]
                mostrarPrimeraTarjeta(selec)
                mostrarSegundaTarjeta(selec)
                mostrarTerceraTarjeta(selec)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se hace nada si no hay nada seleccionado
            }
        }

        // Mostrar contenido inicial
        val selecInicial: String = opciones[0]
        mostrarPrimeraTarjeta(selecInicial)
        mostrarSegundaTarjeta(selecInicial)
        mostrarTerceraTarjeta(selecInicial)
    }

    private fun mostrarPrimeraTarjeta(selec: String) {//Configuramos el contenido de la priemra tarjeta
        when (selec) {
            "Primer plato" -> {
                imagen1.setImageResource(R.drawable.primerplato1)
                titulo1.text = "Revuelto de setas con gambas."
                texto1.text = "Contiene: Setas, huevos, cebolla, gambas."
            }
            "Segundo plato" -> {
                imagen1.setImageResource(R.drawable.segundoplato1)
                titulo1.text = "Chuletón con guarnición."
                texto1.text = "Contiene: Chuletón, pasas, zanahoria."
            }
            "Postre" -> {
                imagen1.setImageResource(R.drawable.postre1)
                titulo1.text = "Tarta de queso."
                texto1.text = "Contiene: Queso, galleta, mermelada de frambuesa."
            }
        }
    }

    private fun mostrarSegundaTarjeta(selec: String) {//Configuramos el contenido de la segunda tarjeta
        when (selec) {
            "Primer plato" -> {
                imagen2.setImageResource(R.drawable.primerplato2)
                titulo2.text = "Espaguetis a la carbonara."
                texto2.text = "Contiene: Huevo, queso, bacon, espaguetis."
            }
            "Segundo plato" -> {
                imagen2.setImageResource(R.drawable.segundoplato2)
                titulo2.text = "Lubina al horno."
                texto2.text = "Contiene: Lubina, patatas, tomate, limón."
            }
            "Postre" -> {
                imagen2.setImageResource(R.drawable.postre2)
                titulo2.text = "Natillas de la abuela."
                texto2.text = "Contiene: Vainilla, canela."
            }
        }
    }

    private fun mostrarTerceraTarjeta(selec: String) {//Configuramos el contenido de la tercera tarjeta
        when (selec) {
            "Primer plato" -> {
                imagen3.setImageResource(R.drawable.primerplato3)
                titulo3.text = "Fabada asturiana."
                texto3.text = "Contiene: Alubiones, chorizo criollo, morcilla, tocino."
            }
            "Segundo plato" -> {
                imagen3.setImageResource(R.drawable.segundoplato3)
                titulo3.text = "Solomillo de cerdo al Pedro Ximenez."
                texto3.text = "Contiene: Solomillo, patatas fritas."
            }
            "Postre" -> {
                imagen3.setImageResource(R.drawable.postre3)
                titulo3.text = "Arroz con leche."
                texto3.text = "Contiene: Arroz, leche, canela."
            }
        }
    }



}