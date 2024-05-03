package com.example.delivibes

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Fragment_Cuenta : Fragment() {
    private val db= FirebaseFirestore.getInstance()//Hacemos referencia a nuestra base de datos

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__cuenta, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val boton = view.findViewById<Button>(R.id.button4)
        val boton1 = view.findViewById<Button>(R.id.button5)
        var text1 = view.findViewById<TextView>(R.id.salidaEmail)
        var text2 = view.findViewById<TextView>(R.id.salidaPassword)
        var text3 = view.findViewById<EditText>(R.id.uno)
        var text4 = view.findViewById<EditText>(R.id.dos)
        var text5 = view.findViewById<EditText>(R.id.tres)

        boton.setBackgroundColor(Color.parseColor("#FFA500"))//Color del botón en naranja
        boton1.setBackgroundColor(Color.parseColor("#FFA500"))//Color del botón en naranja


        val knowStateVal = Registrarse.controllPage//Obtengo la variable para saber si es 1 o 2
        if (knowStateVal == 1) {
            // Si es 1, significa que se registró
            text1.text = Registrarse.meterEmail.text.toString()
            text2.text = Registrarse.meterPassword.text.toString()
            val email = Registrarse.meterEmail.text.toString()
//            getInfo(email)
            // Buscar el usuario por correo electrónico en Firestore
            db.collection("tablaUsuarios").whereEqualTo("email", email).get().addOnSuccessListener { documents ->
                for (document in documents) {
                    val userID = document.id // Obtener el ID de documento del usuario
                    // Realizar una operación get() con el ID de documento obtenido
                    db.collection("tablaUsuarios").document(userID).get()
                        .addOnSuccessListener { documentSnapshot ->
                            if (documentSnapshot.exists()) {
                                // El documento existe, puedes acceder a sus datos aquí
                                val nombre1 = documentSnapshot.getString("nombre")
                                val apellidos1 = documentSnapshot.getString("apellidos")
                                val telefono1 = documentSnapshot.getString("telefono")

                                text3.setText(nombre1)
                                text4.setText(apellidos1)
                                text5.setText(telefono1)

                            } else {
                                // El documento no existe
                            }
                        }
                }
            }


        } else {
            // Si no es 1, significa que inició sesión
            text1.text = InicioSesion.meterEmail.text.toString()
            text2.text = InicioSesion.meterPassword.text.toString()
            val email = InicioSesion.meterEmail.text.toString()
//            getInfo(email)
            // Buscar el usuario por correo electrónico en Firestore
            db.collection("tablaUsuarios").whereEqualTo("email", email).get().addOnSuccessListener { documents ->
                for (document in documents) {
                    val userID = document.id // Obtener el ID de documento del usuario
                    // Realizar una operación get() con el ID de documento obtenido
                    db.collection("tablaUsuarios").document(userID).get()
                        .addOnSuccessListener { documentSnapshot ->
                            if (documentSnapshot.exists()) {
                                // El documento existe, puedes acceder a sus datos aquí
                                val nombre1 = documentSnapshot.getString("nombre")
                                val apellidos1 = documentSnapshot.getString("apellidos")
                                val telefono1 = documentSnapshot.getString("telefono")

                                text3.setText(nombre1)
                                text4.setText(apellidos1)
                                text5.setText(telefono1)


                            } else {
                                // El documento no existe
                            }
                        }
                }
            }

        }
        boton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireContext(), PantallaCarga::class.java)
            startActivity(intent)
        }
        boton1.setOnClickListener {
            // Obtener el usuario actualmente autenticado
            val user = FirebaseAuth.getInstance().currentUser

            // Eliminar la cuenta del usuario actualmente autenticado
            user?.delete()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // La cuenta se eliminó exitosamente
                        Toast.makeText(requireContext(), "Cuenta eliminada correctamente", Toast.LENGTH_SHORT).show()

                        // Redirigir a la pantalla de carga u otra actividad
                        val intent = Intent(requireContext(), PantallaCarga::class.java)
                        startActivity(intent)
                    } else {
                        // La cuenta no se pudo eliminar
                        Toast.makeText(requireContext(), "Error al eliminar la cuenta", Toast.LENGTH_SHORT).show()
                    }
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
}