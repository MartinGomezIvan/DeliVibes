package com.example.delivibes

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Fragment_Cuenta : Fragment() {
    //private val db= FirebaseFirestore.getInstance()//Hacemos referencia a nuestra base de datos
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__cuenta, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val boton=view.findViewById<Button>(R.id.button4)
        var text1=view.findViewById<TextView>(R.id.salidaEmail)
        var text2=view.findViewById<TextView>(R.id.salidaPassword)
        var text3=view.findViewById<TextView>(R.id.salidaNombre)
        var text4=view.findViewById<TextView>(R.id.meterApellidos)
        var text5=view.findViewById<TextView>(R.id.salidaTelefono)

        val knowStateVal=Registrarse.controllPage//Obtengo la variable para saber si es 1 o 2
        if (knowStateVal==1){//Si es 1, quiere decir que he entrado registrándome
            text1.setText(Registrarse.meterEmail.text.toString())
            text2.setText(Registrarse.meterPassword.text.toString())
        }else {//Si no es 1, quiere decir que he entrado iniciando sesión
            text1.setText(InicioSesion.meterEmail.text.toString())
            text2.setText(InicioSesion.meterPassword.text.toString())
        }


        boton.setOnClickListener {
           FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireContext(), PantallaCarga::class.java)
            startActivity(intent)
        }
    }

}