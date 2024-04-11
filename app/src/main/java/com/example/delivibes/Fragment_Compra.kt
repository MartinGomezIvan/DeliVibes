package com.example.delivibes

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController


class Fragment_Compra : Fragment() {
    //Controlar que en el apartado de cantidad, solo se puedan poner kilos o docenas
    var precio=0.0
    lateinit var producto: EditText
    lateinit var cantidad: EditText
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
        cantidad = view.findViewById<EditText>(R.id.cantidad)
        producto = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val info = view.findViewById<EditText>(R.id.info)
        val direccion = view.findViewById<EditText>(R.id.direccion)


        // Manejar el botón "Pagar"
        btnPagar.setOnClickListener {
            val producto1=producto.text.toString()
            val cantidad1=cantidad.text.toString()
            val info1=info.text.toString()
            val direccion1=direccion.text.toString()
            if (validateInputs(producto1, cantidad1, info1, direccion1)) {
                showPaymentDialog(producto1, cantidad1, info1, direccion1)
            } else {
                // Mostrar un mensaje de error si alguna de las casillas no está completada
                AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage("Por favor, complete todos los campos.")
                    .setPositiveButton(android.R.string.ok, null)
                    .show()
            }
        }
        }

//         Función para validar que todos los campos estén completados
    private fun validateInputs(producto: String, cantidad: String, info: String, direccion: String): Boolean {
        return !(TextUtils.isEmpty(producto) || TextUtils.isEmpty(cantidad) || TextUtils.isEmpty(info) || TextUtils.isEmpty(direccion))
    }

    // Función para mostrar el diálogo de pago exitoso
    private fun showPaymentDialog(producto1: String, cantidad1: String, info1: String, direccion1: String) {
        // Calcula el precio
        calculatePrice(producto, cantidad)
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_pago, null)

        val numeroTarjetaEditText = dialogView.findViewById<EditText>(R.id.numeroTarjetaEditText)
        val codigoPostalEditText = dialogView.findViewById<EditText>(R.id.codigoPostalEditText)

        val builder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle("Resumen de pedido:")
            .setMessage("Producto: $producto1\nCantidad: $cantidad1\nInformación adicional: $info1\nDirección: $direccion1\nTotal a pagar: $precio")
            .setPositiveButton("Aceptar") { dialog, _ ->
                val numeroTarjeta = numeroTarjetaEditText.text.toString()
                val codigoPostal = codigoPostalEditText.text.toString()

                // Validar que ambos campos de la tarjeta estén completados
                if (validateTarjeta(numeroTarjeta, codigoPostal)) {
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
        val dialog = builder.create()
        dialog.show()
    }

    // Función para validar que ambos campos de la tarjeta estén completados
    private fun validateTarjeta(numeroTarjeta: String, codigoPostal: String): Boolean {
        return !(TextUtils.isEmpty(numeroTarjeta) || TextUtils.isEmpty(codigoPostal))
    }

    private fun calculatePrice(producto: EditText, cantidad: EditText){
        val producto1=producto.text.toString()
        val cantidadText = cantidad.text.toString()

        // Extraer el número inicial de la cantidad usando una expresión regular
        val regex = """^\d+""".toRegex()
        val matchResult = regex.find(cantidadText)
        val cantidad1 = matchResult?.value?.toDouble()

        when(producto1){
            "Chuletón de vaca madurada" -> {
                precio=30* cantidad1!! //Las exclamaciones indican que no puede ser nulo
            }
            "Lubina salvaje de temporada" -> {
                precio=19.90* cantidad1!! //Las exclamaciones indican que no puede ser nulo
            }
            "Trufa negra (Melanosporum)" -> {
                precio=1200* cantidad1!! //Las exclamaciones indican que no puede ser nulo
            }
            "Revuelto de setas variadas" -> {
                precio=15* cantidad1!! //Las exclamaciones indican que no puede ser nulo
            }
            "Huevo de gallina en natural" -> {
                precio=4* cantidad1!! //Las exclamaciones indican que no puede ser nulo
            }
            "Queso semicurado de oveja" -> {
                precio=2.20* cantidad1!! //Las exclamaciones indican que no puede ser nulo
            }
            "Mermelada de frambuesa" -> {
                precio=5.55* cantidad1!! //Las exclamaciones indican que no puede ser nulo
            }
            "Chorizo criollo de Galicia" -> {
                precio=9* cantidad1!! //Las exclamaciones indican que no puede ser nulo
            }
            "Solomillo de cerdo premium" -> {
                precio=8.90* cantidad1!! //Las exclamaciones indican que no puede ser nulo
            }
            "Arroz blanco de India" -> {
                precio=2* cantidad1!! //Las exclamaciones indican que no puede ser nulo
            }
            "Morcilla de Burgos" -> {
                precio=5.19* cantidad1!! //Las exclamaciones indican que no puede ser nulo
            }
            "Tocino de panceta" -> {
                precio=3.90* cantidad1!! //Las exclamaciones indican que no puede ser nulo
            }


        }
    }


}
