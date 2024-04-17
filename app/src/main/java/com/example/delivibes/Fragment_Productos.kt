package com.example.delivibes

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
        boton3.setOnClickListener{obtenerInformacionNutricional("8412422003019")}
        boton4.setOnClickListener{obtenerInformacionNutricional("8480013028413")}
        boton5.setOnClickListener{obtenerInformacionNutricional("20879204")}
        boton6.setOnClickListener{obtenerInformacionNutricional("8420197005558")}
        boton7.setOnClickListener{obtenerInformacionNutricional("8480000860330")}
        boton8.setOnClickListener{obtenerInformacionNutricional("8480017245878")}
        boton9.setOnClickListener{obtenerInformacionNutricional("2302714002683")}
        boton10.setOnClickListener{obtenerInformacionNutricional("40889290")}
        boton11.setOnClickListener{obtenerInformacionNutricional("2302815002513")}
        boton13.setOnClickListener{obtenerInformacionNutricional("3560071094935")}
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

    private fun mostrarInformacionNutricional(response: String) {//Nos muestra los datos recogidos en la conexión a l api
        try {
            val producto = Gson().fromJson(response, Producto::class.java)
            val informacionNutricional = producto.product?.nutriments?.toString()

            // Construir y mostrar el cuadro de diálogo de alerta con la información nutricional
            activity?.runOnUiThread {
                AlertDialog.Builder(requireContext())
                    .setTitle("Información Nutricional")
                    .setMessage(informacionNutricional)
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
