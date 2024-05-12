package com.example.delivibes

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore


class Fragment_Compra : Fragment() {
    private val db = FirebaseFirestore.getInstance()//Obtener la instancia de la bd
    var precio=0.0
    lateinit var producto: EditText
    lateinit var cantidad: EditText
    lateinit var info: EditText
    lateinit var direccion: EditText
    var cantidadEncontrada = 0.0//Para almacenar la cantidad que encuentra de la base de datos

    // Variable para controlar si se ha pulsado el botón "Ver"
    private var verPulsado = false
    private lateinit var bocadillo: LinearLayout
    private lateinit var btnOcultar: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__compra, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val autoCompleteTextView =
            view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val productos = arrayOf(
            "Chuletón de vaca madurada",
            "Lubina salvaje de temporada",
            "Trufa negra (Melanosporum)",
            "Revuelto de setas variadas",
            "Huevo de gallina en natural",
            "Queso semicurado de oveja",
            "Mermelada de frambuesa",
            "Chorizo criollo de Galicia",
            "Solomillo de cerdo premium",
            "Arroz blanco de India",
            "Morcilla de Burgos",
            "Tocino de panceta"
        ) // Lista de productos disponibles
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, productos)
        autoCompleteTextView.setAdapter(adapter)//Metemos en el adapter la lista de productos

        val btnPagar = view.findViewById<Button>(R.id.btnPagar)
        cantidad = view.findViewById(R.id.cantidad)
        producto = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        info = view.findViewById(R.id.info)
        direccion = view.findViewById(R.id.direccion)
        val boton = view.findViewById<Button>(R.id.button6)
        bocadillo = view.findViewById(R.id.bocadillo)
        btnOcultar = view.findViewById(R.id.btnOcultar)

        btnOcultar.setBackgroundColor(Color.parseColor("#FFA500"))//Color del botón en naranja
        btnPagar.setBackgroundColor(Color.parseColor("#FFA500"))//Color del botón en naranja
        boton.setBackgroundColor(Color.parseColor("#FFA500"))//Color del botón en naranja

        //Que estén por defecto deshabilitadas
        cantidad.isEnabled = false
        info.isEnabled = false
        direccion.isEnabled = false

        // Mostrar el bocadillo al entrar en la pantalla
        mostrarBocadillo();

        // Configurar el botón "ocultar"
        btnOcultar.setOnClickListener {
            ocultarBocadillo()
        }

        boton.setOnClickListener {
            val producto1=producto.text.toString()
            pulsarBotonVer(producto1)
        }

        // Manejar el botón "Pagar"
        btnPagar.setOnClickListener {
            val producto1=producto.text.toString()
            val cantidad1=cantidad.text.toString()
            val info1=info.text.toString()
            val direccion1=direccion.text.toString()
            if (validateInputs(producto1, cantidad1, info1, direccion1)) {
                showPaymentDialog(producto1, cantidad1, info1, direccion1)
            } else{
                // Mostrar un mensaje de error si alguna de las casillas no está completada
                AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage("Por favor, complete correctamente todos los campos."+"\n"+"Recuerde que las unidades sólo pueden ser kg o docenas.")
                    .setPositiveButton(android.R.string.ok, null)
                    .show()
            }
        }
        // Manejar el botón de retroceso para ir a la actividad Menu
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(requireContext(), Menu::class.java)
                startActivity(intent)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun mostrarBocadillo() {
        bocadillo.visibility = View.VISIBLE
    }

    private fun ocultarBocadillo() {
        bocadillo.visibility = View.GONE
    }

    private fun pulsarBotonVer(productName: String) {
        // Realizar consulta a la base de datos para obtener la cantidad del producto
        db.collection("cantidadProductos")
            .whereEqualTo("nombre", productName)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    cantidadEncontrada = document.getDouble("cantidad")!!//Las !! indican que no puede  ser nulo
                    if (cantidadEncontrada != null) {
                        var palabra="";
                        if(productName.equals("Huevo de gallina en natural")){//Para saber si poner docenas o kg
                            palabra="docenas"
                            // Se encontró la cantidad del producto
                            AlertDialog.Builder(requireContext())
                                .setTitle("Cantidad")
                                .setMessage("Cantidad disponible: $cantidadEncontrada $palabra")
                                .setPositiveButton(android.R.string.ok, null)
                                .show()
                            verPulsado = true
                            habilitarOpcionesSiVerPulsado()
                            return@addOnSuccessListener // Salir del bucle cuando se encuentra la cantidad
                        }else{
                            palabra="kg"
                            AlertDialog.Builder(requireContext())
                                .setTitle("Cantidad")
                                .setMessage("Cantidad disponible: $cantidadEncontrada $palabra")
                                .setPositiveButton(android.R.string.ok, null)
                                .show()
                            verPulsado = true
                            habilitarOpcionesSiVerPulsado()
                            return@addOnSuccessListener // Salir del bucle cuando se encuentra la cantidad
                        }

                    }
                }
                // Si se llega aquí, significa que no se encontró la cantidad del producto en ningún documento
                AlertDialog.Builder(requireContext())
                    .setTitle("Cantidad")
                    .setMessage("Cantidad no disponible")
                    .setPositiveButton(android.R.string.ok, null)
                    .show()
            }
    }
    // Función para extraer el número de la cadena de cantidad
    private fun getCantidadNumerica(cantidad: String): Double? {
        // Expresión regular para extraer el número de la cadena de cantidad
        val regex = """(\d+(\.\d+)?)""".toRegex()
        val matchResult = regex.find(cantidad)

        // Si se encuentra una coincidencia, devuelve el número extraído como un Double
        return matchResult?.value?.toDoubleOrNull()
    }

    //         Función para validar que todos los campos estén completados
    private fun validateInputs(producto: String, cantidad: String, info: String, direccion: String): Boolean {
        // Verificar que el campo "cantidad" contenga un número seguido de "kg", "docena" o "docenas"
        val cantidadRegex = """^\d+(\.\d+)?\s*(kg|docena|docenas|kilogramos|kilogramo)$""".toRegex(RegexOption.IGNORE_CASE)

        // Extraer el número de la cantidad ingresada
        val cantidadNumerica = getCantidadNumerica(cantidad)

        // Verificar que ningún campo esté vacío
        if (TextUtils.isEmpty(producto) || TextUtils.isEmpty(cantidad) || TextUtils.isEmpty(info) || TextUtils.isEmpty(direccion)) {
            return false
        }
        else if (!cantidad.matches(cantidadRegex)) {
            return false
        }

        else if (cantidadNumerica != null) {
            if (cantidadNumerica > cantidadEncontrada) {
                // La cantidad ingresada es mayor que la cantidad disponible en la base de datos
                AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage("La cantidad ingresada es mayor que la cantidad disponible en la tienda.")
                    .setPositiveButton(android.R.string.ok, null)
                    .show()
                return false
            }
        } else if (cantidadEncontrada==0.0){
                // No se pudo extraer un número válido de la cantidad ingresada
                AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage("Producto agotado.")
                    .setPositiveButton(android.R.string.ok, null)
                    .show()
                return false
            }

        // Si pasa ambas validaciones, retornar true
        return true
    }

    // Función para mostrar el diálogo de pago exitoso
    private fun showPaymentDialog(producto1: String, cantidad1: String, info1: String, direccion1: String) {
        // Calcula el precio
        calculatePrice(producto, cantidad)
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_pago, null)

        val numeroTarjetaEditText = dialogView.findViewById<EditText>(R.id.numeroTarjetaEditText)
        val codigoPostalEditText = dialogView.findViewById<EditText>(R.id.codigoPostalEditText)

        val builder = AlertDialog.Builder(requireContext())//Mostrar el alert
            .setView(dialogView)
            .setTitle("Resumen de pedido:")
            .setMessage("Producto: $producto1\nCantidad: $cantidad1\nInformación adicional: $info1\nDirección: $direccion1\nTotal a pagar: $precio €")
            .setPositiveButton("Aceptar") { dialog, _ ->
                val numeroTarjeta = numeroTarjetaEditText.text.toString()
                val codigoPostal = codigoPostalEditText.text.toString()

                // Validar que ambos campos de la tarjeta estén completados
                if (validateTarjeta(numeroTarjeta, codigoPostal)) {
                    restarCantidad(producto, cantidad1) // Restar la cantidad comprada de la base de datos
                    AlertDialog.Builder(requireContext())
                        .setTitle("Pago")
                        .setMessage("¡Pago realizado con éxito!")
                        .setPositiveButton(android.R.string.ok, ){ _, _ ->
                            // Crear un Intent para navegar a la otra actividad
                            val intent = Intent(requireContext(), Menu::class.java)
                            startActivity(intent)
                        }
                        .show()
                    dialog.dismiss()
                } else {
                    // Mostrar un mensaje de error si falta algún campo de la tarjeta
                    AlertDialog.Builder(requireContext())
                        .setTitle("Error")
                        .setMessage("Por favor, complete ambos campos de la tarjeta.")
                        .setPositiveButton(android.R.string.ok, null)
                        .show()
                }
            }
        // Personalización del AlertDialog
        val colorText = Color.WHITE
        val colorButton = Color.parseColor("#FFA500")

        val alertDialog = builder.create()
        alertDialog.show()

        // Cambiar el color del texto de los botones (el de aceptar)
        val positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        positiveButton.setTextColor(colorText)
        positiveButton.setBackgroundColor(colorButton)
    }

    // Función para validar que ambos campos de la tarjeta estén completados
    private fun validateTarjeta(numeroTarjeta: String, codigoPostal: String): Boolean {
        return !(TextUtils.isEmpty(numeroTarjeta) || TextUtils.isEmpty(codigoPostal))
    }

    private fun calculatePrice(producto: EditText, cantidad: EditText){//Función en la que calculamos los precios
        val producto1=producto.text.toString()
        val cantidadText = cantidad.text.toString()

        // Extraer el número inicial de la cantidad usando una expresión regular
        //Ejemplo, 2 kg, extraemos el 2
        val regex = """^\d+(\.\d+)?""".toRegex()
        val matchResult = regex.find(cantidadText)
        val cantidad1 = matchResult?.value?.toDouble()

        // Redondear la cantidad para considerar valores decimales mayores o iguales a 0.5
        val cantidadRedondeada = cantidad1?.let {
            val cantidadDouble = it.toDouble()
            val cantidadEntera = cantidadDouble.toInt()
            val parteDecimal = cantidadDouble - cantidadEntera
            if (parteDecimal >= 0.5) {
                cantidadEntera + 1.0
            } else {
                cantidadEntera.toDouble()
            }
        } ?: return

        when(producto1){
            "Chuletón de vaca madurada" -> {
                precio=30* cantidadRedondeada!! //Las exclamaciones indican que no puede ser nulo
            }
            "Lubina salvaje de temporada" -> {
                precio=19.90* cantidadRedondeada!! //Las exclamaciones indican que no puede ser nulo
            }
            "Trufa negra (Melanosporum)" -> {
                precio=1200* cantidadRedondeada!! //Las exclamaciones indican que no puede ser nulo
            }
            "Revuelto de setas variadas" -> {
                precio=15* cantidadRedondeada!! //Las exclamaciones indican que no puede ser nulo
            }
            "Huevo de gallina en natural" -> {
                precio=4* cantidadRedondeada!! //Las exclamaciones indican que no puede ser nulo
            }
            "Queso semicurado de oveja" -> {
                precio=2.20* cantidadRedondeada!! //Las exclamaciones indican que no puede ser nulo
            }
            "Mermelada de frambuesa" -> {
                precio=5.55* cantidadRedondeada!! //Las exclamaciones indican que no puede ser nulo
            }
            "Chorizo criollo de Galicia" -> {
                precio=9* cantidadRedondeada!! //Las exclamaciones indican que no puede ser nulo
            }
            "Solomillo de cerdo premium" -> {
                precio=8.90* cantidadRedondeada!! //Las exclamaciones indican que no puede ser nulo
            }
            "Arroz blanco de India" -> {
                precio=2* cantidadRedondeada!! //Las exclamaciones indican que no puede ser nulo
            }
            "Morcilla de Burgos" -> {
                precio=5.19* cantidadRedondeada!! //Las exclamaciones indican que no puede ser nulo
            }
            "Tocino de panceta" -> {
                precio=3.90* cantidadRedondeada!! //Las exclamaciones indican que no puede ser nulo
            }


        }
    }
    private fun habilitarOpcionesSiVerPulsado() {//Para saber si se ha pulsado el botón Ver
        if (verPulsado) {
            // Habilitar las opciones si el botón "Ver" se ha pulsado
            cantidad.isEnabled = true
            info.isEnabled = true
            direccion.isEnabled = true
        }
    }
    // Función para restar la cantidad comprada de la base de datos
    private fun restarCantidad(producto: EditText, cantidadText: String) {
        val producto1 = producto.text.toString()

        // Extraer el número inicial de la cantidad usando una expresión regular
        // Ejemplo, 2 kg, extraemos el 2
        val regex = """^\d+(\.\d+)?""".toRegex()
        val matchResult = regex.find(cantidadText)
        val cantidad = matchResult?.value?.toDouble()

        // Realizar consulta a la base de datos para obtener la cantidad del producto
        db.collection("cantidadProductos")
            .whereEqualTo("nombre", producto1)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val idDocumento = document.id
                    var cantidadDisponible = document.getDouble("cantidad")!!

                    // Restar la cantidad comprada de la base de datos
                    cantidadDisponible -= cantidad ?: 0.0 // Si la cantidad es nula, usar 0.0

                    // Actualizar la cantidad disponible en la base de datos
                    val cantidadActualizada = hashMapOf(
                        "cantidad" to cantidadDisponible
                    )
                    db.collection("cantidadProductos").document(idDocumento)
                        .update(cantidadActualizada as Map<String, Any>)
                        .addOnSuccessListener {
                            Log.d(TAG, "Cantidad actualizada correctamente")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error al actualizar la cantidad", e)
                        }
                    return@addOnSuccessListener // Salir del bucle cuando se actualiza la cantidad
                }
            }
    }
}
