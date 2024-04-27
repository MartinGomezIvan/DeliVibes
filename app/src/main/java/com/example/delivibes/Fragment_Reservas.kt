package com.example.delivibes

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TimePicker
import android.widget.Toast
import android.widget.ToggleButton
import androidx.navigation.Navigation.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class Fragment_Reservas : Fragment() {
    private lateinit var nombreEditText: EditText
    private lateinit var telefonoEditText: EditText
    private lateinit var numeroPersonas: EditText
    private lateinit var calendarView: CalendarView
    private lateinit var timePicker: TimePicker
    private lateinit var pagarButton: Button
    private lateinit var verReservasButton: Button
    private lateinit var meterMes: EditText

    private val db = FirebaseFirestore.getInstance()//Hacemos referencia a nuestra base de datos

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__reservas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nombreEditText = view.findViewById(R.id.nombre)
        telefonoEditText = view.findViewById(R.id.telefono)
        calendarView = view.findViewById(R.id.calendarView)
        timePicker = view.findViewById(R.id.timePicker)
        pagarButton = view.findViewById(R.id.button3)
        verReservasButton = view.findViewById(R.id.button7)
        numeroPersonas = view.findViewById(R.id.numeroPersonas)
        meterMes = view.findViewById(R.id.mes)
        pagarButton.setBackgroundColor(Color.parseColor("#FFA500"))//Color del botón a naranja
        verReservasButton.setBackgroundColor(Color.parseColor("#FFA500"))//Color del botón a naranja


        pagarButton.setOnClickListener {
            pulsarBotonPagar()
        }
        verReservasButton.setOnClickListener {
            mostrarReservas()
        }
    }

    fun pulsarBotonPagar() {
        val nombre = nombreEditText.text.toString()
        val telefono = telefonoEditText.text.toString()
        val numeroPersonas = numeroPersonas.text.toString()
        val fechaSeleccionada = Calendar.getInstance()
        fechaSeleccionada.timeInMillis = calendarView.date//Cogemos el día
        fechaSeleccionada.set(Calendar.HOUR_OF_DAY, 0)//Ponemos la hora a 0, para que en la bd se inserte sólo el día de la reserva
        fechaSeleccionada.set(Calendar.MINUTE, 0)//Ponemos los minutos a 0, para que en la bd se inserte sólo el día de la reserva
        fechaSeleccionada.set(Calendar.SECOND, 0)//Ponemos los segundos a 0, para que en la bd se inserte sólo el día de la reserva
        fechaSeleccionada.set(Calendar.MILLISECOND, 0)//Ponemos los milisegundos a 0, para que en la bd se inserte sólo el día de la reserva
        val fechaTimestamp = com.google.firebase.Timestamp(fechaSeleccionada.time)
        val horaSeleccionada =
            "${timePicker.hour}:${timePicker.minute}"//Comprobamos que todo esté seleccionado
        if (nombre.equals("") || telefono.equals("") || fechaSeleccionada.equals("") || horaSeleccionada.equals(
                ""
            )
            || numeroPersonas.equals("")
        ) {

            val builder = AlertDialog.Builder(requireContext())//Creamos el alert
            builder.setMessage("Seleccione y rellene todos los campos para reservar")
            builder.setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()

            //Si lo que se escribe en el número de personas no es ninguna de las siguientes opciones
        } else if (!numeroPersonas.contains("1 persona") && !numeroPersonas.contains("2 personas") && !numeroPersonas.contains(
                "4 personas"
            )
        ) {
            val builder = AlertDialog.Builder(requireContext())//Muestra el alert
            builder.setMessage("El número de personas a reservar sólo puede ser de 1, 2 o 4")
            builder.setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()

        } else {//Si todo está correcto, nos hace la comprobación de la reserva
            val reservasRef = db.collection("tablaReservas")
            val query = reservasRef
                .whereEqualTo("fecha", fechaTimestamp )//Comprobamos que no hay reservas con la misma fecha ni hora
                .whereEqualTo("hora", horaSeleccionada)

            query.get()
                .addOnSuccessListener { documents ->
                    if (documents.isEmpty) {
                        // No hay registros con la misma fecha y hora, insertamos nueva reserva
                        reservasRef.add(
                            mapOf(
                                "nombre" to nombre,
                                "telefono" to telefono,
                                "fecha" to fechaTimestamp,
                                "hora" to horaSeleccionada,
                                "numeroPersonas" to numeroPersonas
                            )
                        )
                        val builder =
                            AlertDialog.Builder(requireContext())//Muestra el alert de reserva realizada
                        builder.setMessage("Reserva realizada")
                        builder.setPositiveButton(android.R.string.ok,) { _, _ ->
                            // Crear un Intent para navegar a la otra actividad
                            val intent = Intent(requireContext(), Menu::class.java)
                            startActivity(intent)
                        }
                        val alertDialog: AlertDialog = builder.create()
                        alertDialog.show()
                    } else {
                        val builder =
                            AlertDialog.Builder(requireContext())//Muestra el alert de reserva no realizada
                        builder.setMessage("Ya hay una reserva con la misma fecha y hora seleccionadas")
                        builder.setPositiveButton("Aceptar") { dialog, _ ->
                            dialog.dismiss()
                        }
                        val alertDialog: AlertDialog = builder.create()
                        alertDialog.show()
                    }
                }
        }
    }

    private fun mostrarReservas() {
        val mes = meterMes.text.toString().toIntOrNull()
        if (mes == null || mes !in 1..12) {
            val builder =
                AlertDialog.Builder(requireContext())//Muestra el alert de reserva no realizada
            builder.setMessage("Seleccione un número entre el 1-12")
            builder.setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }

        val reservasRef = db.collection("tablaReservas")
        if (mes != null) {
            reservasRef
                .get()
                .addOnSuccessListener { documents ->
                    val reservas = mutableListOf<String>()
                    for (doc in documents) {
                        val reserva = doc.data
                        val fechaTimestamp = reserva["fecha"] as com.google.firebase.Timestamp
                        val fecha = fechaTimestamp.toDate()
                        val calendar = Calendar.getInstance()
                        calendar.time = fecha
                        val reservaMes = calendar.get(Calendar.MONTH) + 1 // Sumamos 1 porque los meses van de 0 a 11
                        if (reservaMes == mes) {
                            val hora = reserva["hora"].toString()
                            val numeroPersonas = reserva["numeroPersonas"].toString()
                            val formato = SimpleDateFormat("dd") // Solo mostramos el día
                            val dia = formato.format(fecha)
                            val mensaje =
                                "Día: $dia\nHora: $hora\nNúmero de personas: $numeroPersonas\n"
                            reservas.add(mensaje)
                        }
                    }

                    if (reservas.isNotEmpty()) {
                        val nombreMes = obtenerNombreMes(mes) // Obtenemos el nombre del mes
                        val mensajeCompleto =
                            reservas.joinToString(separator = "\n\n")
                        val builder =
                            AlertDialog.Builder(requireContext())// Muestra el alert de reservas
                        builder.setMessage("Reservas del mes de $nombreMes: \n" + mensajeCompleto)
                        builder.setPositiveButton("Aceptar") { dialog, _ ->
                            dialog.dismiss()
                        }
                        val alertDialog: AlertDialog = builder.create()
                        alertDialog.show()
                    } else {
                        val nombreMes = obtenerNombreMes(mes)
                        val builder =
                            AlertDialog.Builder(requireContext())// Muestra el alert de reserva no realizada
                        builder.setMessage("No hay reservas para el mes de $nombreMes")
                        builder.setPositiveButton("Aceptar") { dialog, _ ->
                            dialog.dismiss()
                        }
                        val alertDialog: AlertDialog = builder.create()
                        alertDialog.show()
                    }
                }
        }
    }

    private fun obtenerNombreMes(mes: Int): String{
        return when (mes) {
                1 -> "Enero"
                2 -> "Febrero"
                3 -> "Marzo"
                4 -> "Abril"
                5 -> "Mayo"
                6 -> "Junio"
                7 -> "Julio"
                8 -> "Agosto"
                9 -> "Septiembre"
                10 -> "Octubre"
                11 -> "Noviembre"
                12 -> "Diciembre"
                else -> "Mes inválido"
            }
    }
}
