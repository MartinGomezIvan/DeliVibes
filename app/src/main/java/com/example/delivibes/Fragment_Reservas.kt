package com.example.delivibes

import android.app.AlertDialog
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


class Fragment_Reservas : Fragment() {
    private lateinit var nombreEditText: EditText
    private lateinit var telefonoEditText: EditText
    private lateinit var tarjetaEditText: EditText
    private lateinit var codigoPostalEditText: EditText
    private lateinit var numeroPersonas: EditText
    private lateinit var calendarView: CalendarView
    private lateinit var timePicker: TimePicker
    private lateinit var pagarButton: Button

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
        tarjetaEditText = view.findViewById(R.id.tarjeta)
        codigoPostalEditText = view.findViewById(R.id.codigoPostal)
        calendarView = view.findViewById(R.id.calendarView)
        timePicker = view.findViewById(R.id.timePicker)
        pagarButton = view.findViewById(R.id.button3)
        numeroPersonas = view.findViewById(R.id.numeroPersonas)


        pagarButton.setOnClickListener {
            pulsarBotonPagar()
        }
    }

    fun pulsarBotonPagar() {
        val nombre = nombreEditText.text.toString()
        val telefono = telefonoEditText.text.toString()
        val tarjeta = tarjetaEditText.text.toString()
        val codigoPostal = codigoPostalEditText.text.toString()
        val numeroPersonas = numeroPersonas.text.toString()
        val fechaSeleccionada = calendarView.date
        val horaSeleccionada = "${timePicker.hour}:${timePicker.minute}"
        if (nombre.equals("") || telefono.equals("") || tarjeta.equals("") || codigoPostal.equals("") || fechaSeleccionada.equals("") || horaSeleccionada.equals("")
            || numeroPersonas.equals("")){

            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("Seleccione y rellene todos los campos para pagar")
            builder.setPositiveButton("Aceptar"){
                    dialog, _ ->dialog.dismiss()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()


        }else if (!numeroPersonas.contains("1 persona") && !numeroPersonas.contains("2 personas") && !numeroPersonas.contains("4 personas")){
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("El número de personas a reservar sólo puede ser de 1, 2 o 4")
            builder.setPositiveButton("Aceptar"){
                    dialog, _ ->dialog.dismiss()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()

        }else{
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("Pago realizado correctmente")
            builder.setPositiveButton("Aceptar"){
                    dialog, _ ->dialog.dismiss()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()

//            val action = fragment_c.actionFragmentReservasToOtroFragmento()
//            findNavController().navigate(action)
            //Que nos lleve a la página principal
            //Que lo inserte en la base de datos
        }
    }


}