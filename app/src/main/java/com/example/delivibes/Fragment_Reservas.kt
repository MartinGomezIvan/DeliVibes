package com.example.delivibes

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
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


class Fragment_Reservas : Fragment() {
    private lateinit var nombreEditText: EditText
    private lateinit var telefonoEditText: EditText
    private lateinit var numeroPersonas: EditText
    private lateinit var calendarView: CalendarView
    private lateinit var timePicker: TimePicker
    private lateinit var pagarButton: Button

    private val db= FirebaseFirestore.getInstance()//Hacemos referencia a nuestra base de datos

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
        numeroPersonas = view.findViewById(R.id.numeroPersonas)
        pagarButton.setBackgroundColor(Color.parseColor("#FFA500"))//Color del botón a naranja



        pagarButton.setOnClickListener {
            pulsarBotonPagar()
        }
    }

    fun pulsarBotonPagar() {
        val nombre = nombreEditText.text.toString()
        val telefono = telefonoEditText.text.toString()
        val numeroPersonas = numeroPersonas.text.toString()
        val fechaSeleccionada = calendarView.date
        val horaSeleccionada = "${timePicker.hour}:${timePicker.minute}"//Comprobamos que todo esté seleccionado
        if (nombre.equals("") || telefono.equals("") || fechaSeleccionada.equals("") || horaSeleccionada.equals("")
            || numeroPersonas.equals("")){

            val builder = AlertDialog.Builder(requireContext())//Creamos el alert
            builder.setMessage("Seleccione y rellene todos los campos para reservar")
            builder.setPositiveButton("Aceptar"){
                    dialog, _ ->dialog.dismiss()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()

            //Si lo que se escribe en el número de personas no es ninguna de las siguientes opciones
        }else if (!numeroPersonas.contains("1 persona") && !numeroPersonas.contains("2 personas") && !numeroPersonas.contains("4 personas")){
            val builder = AlertDialog.Builder(requireContext())//Muestra el alert
            builder.setMessage("El número de personas a reservar sólo puede ser de 1, 2 o 4")
            builder.setPositiveButton("Aceptar"){
                    dialog, _ ->dialog.dismiss()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()

        }else{//Si todo está correcto, nos hace la comprobación de la reserva
            val reservasRef = db.collection("tablaReservas")
            val query = reservasRef
                .whereEqualTo("fecha", fechaSeleccionada)//Comprobamos que no hay reservas con la misma fecha ni hora
                .whereEqualTo("hora", horaSeleccionada)

            query.get()
                .addOnSuccessListener { documents ->
                    if (documents.isEmpty) {
                        // No hay registros con la misma fecha y hora, insertamos nueva reserva
                        reservasRef.add(mapOf(
                            "nombre" to nombre,
                            "telefono" to telefono,
                            "fecha" to fechaSeleccionada,
                            "hora" to horaSeleccionada,
                            "numeroPersonas" to numeroPersonas
                        ))
                        val builder = AlertDialog.Builder(requireContext())//Muestra el alert de reserva realizada
                        builder.setMessage("Reserva realizada")
                        builder.setPositiveButton(android.R.string.ok, ){ _, _ ->
                            // Crear un Intent para navegar a la otra actividad
                            val intent = Intent(requireContext(), Menu::class.java)
                            startActivity(intent)
                        }
                        val alertDialog: AlertDialog = builder.create()
                        alertDialog.show()
                    } else {
                        val builder = AlertDialog.Builder(requireContext())//Muestra el alert de reserva no realizada
                        builder.setMessage("Ya hay una reserva con la misma fecha y hora seleccionadas")
                        builder.setPositiveButton("Aceptar"){
                                dialog, _ ->dialog.dismiss()
                        }
                        val alertDialog: AlertDialog = builder.create()
                        alertDialog.show()
                    }
                }
        }
    }


}