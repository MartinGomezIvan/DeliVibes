package com.example.delivibes

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import androidx.recyclerview.widget.RecyclerView
import java.net.HttpURLConnection
import java.net.URL
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlin.math.abs

class Fragment_Productos : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment__productos, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val boton1=view.findViewById<Button>(R.id.botonInformacionNutricional)
        val boton2=view.findViewById<Button>(R.id.botonInformacionNutricional2)
        val boton3=view.findViewById<Button>(R.id.botonInformacionNutricional3)
        val boton4=view.findViewById<Button>(R.id.botonInformacionNutricional4)
        val boton5=view.findViewById<Button>(R.id.botonInformacionNutricional5)
        val boton6=view.findViewById<Button>(R.id.botonInformacionNutricional6)
        val boton7=view.findViewById<Button>(R.id.botonInformacionNutricional7)
        val boton8=view.findViewById<Button>(R.id.botonInformacionNutricional8)
        val boton9=view.findViewById<Button>(R.id.botonInformacionNutricional9)
        val boton10=view.findViewById<Button>(R.id.botonInformacionNutricional10)
        val boton11=view.findViewById<Button>(R.id.botonInformacionNutricional11)
        val boton13=view.findViewById<Button>(R.id.botonInformacionNutricional13)

        //Color del botón a naranja
        boton1.setBackgroundColor(Color.parseColor("#FFA500"))
        boton2.setBackgroundColor(Color.parseColor("#FFA500"))
        boton3.setBackgroundColor(Color.parseColor("#FFA500"))
        boton4.setBackgroundColor(Color.parseColor("#FFA500"))
        boton5.setBackgroundColor(Color.parseColor("#FFA500"))
        boton6.setBackgroundColor(Color.parseColor("#FFA500"))
        boton7.setBackgroundColor(Color.parseColor("#FFA500"))
        boton8.setBackgroundColor(Color.parseColor("#FFA500"))
        boton9.setBackgroundColor(Color.parseColor("#FFA500"))
        boton10.setBackgroundColor(Color.parseColor("#FFA500"))
        boton11.setBackgroundColor(Color.parseColor("#FFA500"))
        boton13.setBackgroundColor(Color.parseColor("#FFA500"))


        //Pasamos a la función el código de barras de cada producto
        boton1.setOnClickListener{obtenerInformacionNutricional("2200880003964")}
        boton2.setOnClickListener{obtenerInformacionNutricional("2293680004460")}
        boton3.setOnClickListener{obtenerInformacionNutricional("8437001210855")}
        boton4.setOnClickListener{obtenerInformacionNutricional("8480013028413")}
        boton5.setOnClickListener{obtenerInformacionNutricional("8423462100163")}
        boton6.setOnClickListener{obtenerInformacionNutricional("8428580000111")}
        boton7.setOnClickListener{obtenerInformacionNutricional("8433329067348")}//
        boton8.setOnClickListener{obtenerInformacionNutricional("95305332")}
        boton9.setOnClickListener{obtenerInformacionNutricional("2302714002683")}
        boton10.setOnClickListener{obtenerInformacionNutricional("40889290")}
        boton11.setOnClickListener{obtenerInformacionNutricional("2302815002513")}
        boton13.setOnClickListener{obtenerInformacionNutricional("8436046503076")}

        // Manejar el botón de retroceso para ir a la actividad Menu
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(requireContext(), Menu::class.java)
                startActivity(intent)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
    private fun obtenerInformacionNutricional(barcode: String) {
        Thread {
            val apiUrl = "https://world.openfoodfacts.org/api/v0/product/$barcode.json"//Ruta de la api en formato json
            val url = URL(apiUrl)
            val connection = url.openConnection() as HttpURLConnection//Conexión

            try {
                connection.connect()//Nos conectamos
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {//Si la respuesta es correcta
                    val inputStream = connection.inputStream
                    val response = inputStream.bufferedReader().use { it.readText() }//Nos escribe el resultado
                    // Aquí procesa la respuesta JSON para obtener la información nutricional
                    mostrarInformacionNutricional(response)
                } else {//Error en la conexión a la api
                    activity?.runOnUiThread {
                        AlertDialog.Builder(requireContext())
                            .setTitle("Información Nutricional")
                            .setMessage("No se pudo mostrar la información nutricional")
                            .setPositiveButton("Aceptar", null)
                            .show()
                    }
                }
            } catch (e: Exception) {
                // Manejar errores de conexión, procesamiento de datos, etc.
                e.printStackTrace()
            } finally {
                connection.disconnect()
            }
        }.start()
    }

    private fun mostrarInformacionNutricional(response: String) {
        try {
            val producto = Gson().fromJson(response, Producto::class.java)
            val nutriments = producto.product?.nutriments

            // Inflar el diseño de la tabla desde XML
            val inflater = LayoutInflater.from(requireContext())
            val tablaView = inflater.inflate(R.layout.tabla_info_nutricional, null) as TableLayout

            // Agregar filas a la tabla para cada nutriente
            nutriments?.forEach { (nombre, cantidad) ->
                val row = TableRow(requireContext())
                val nombreView = TextView(requireContext())
                nombreView.text = nombre
                val cantidadView = TextView(requireContext())
                cantidadView.text = cantidad

                // Establecer el estilo de las vistas
                nombreView.setPadding(8, 8, 8, 8)
                cantidadView.setPadding(8, 8, 8, 8)

                // Agregar las vistas a la fila
                row.addView(nombreView)
                row.addView(cantidadView)

                // Agregar la fila a la tabla
                tablaView.addView(row)
            }

            // Construir y mostrar el cuadro de diálogo de alerta con la tabla de información nutricional
            activity?.runOnUiThread {
                AlertDialog.Builder(requireContext())
                    .setTitle("Información Nutricional")
                    .setView(tablaView)
                    .setPositiveButton("Aceptar", null)
                    .show()
            }
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            // Manejar el caso de respuesta JSON no válida
            activity?.runOnUiThread {
                AlertDialog.Builder(requireContext())
                    .setTitle("Información Nutricional")
                    .setMessage("No se pudo mostrar la información nutricional")
                    .setPositiveButton("Aceptar", null)
                    .show()
            }
        }
    }

    data class Producto(val product: Product?)//Clases para configurar el objeto en el que va la información nutricional
    data class Product(val nutriments: Map<String, String>?)

}
