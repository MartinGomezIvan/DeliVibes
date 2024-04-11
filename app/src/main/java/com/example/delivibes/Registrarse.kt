package com.example.delivibes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Registrarse : AppCompatActivity() {
    private lateinit var meterNombre: EditText
    private lateinit var meterApellidos: EditText
//    private lateinit var meterEmail: EditText
//    private lateinit var meterPassword: EditText
    private lateinit var meterTelefono: EditText
    companion object {
        lateinit var meterEmail: EditText
        lateinit var meterPassword: EditText
        var controllPage=0 //Variable para controlar si se ha accedido mediantre registro o inicio de sesion
    }
    private val db= FirebaseFirestore.getInstance()//Hacemos referencia a nuestra base de datos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)

        meterNombre = findViewById(R.id.meterNombre)
        meterApellidos = findViewById(R.id.meterApellidos)
        meterPassword = findViewById(R.id.meterPassword)
        meterEmail = findViewById(R.id.meterEmail)
        meterTelefono = findViewById(R.id.meterTelefono)
        controllPage =1//La ponemos a 2, mientras que en inicio de sesión, está en 1

    }

    fun PulsarBotonHecho(view: View) {
        var nombre=meterNombre.text.toString()
        var apellidos=meterApellidos.text.toString()
        var password=meterPassword.text.toString()
        var email=meterEmail.text.toString()
        var telefono=meterTelefono.text.toString()
        if (nombre.isEmpty() || apellidos.isEmpty() || password.isEmpty() || email.isEmpty() || telefono.isEmpty() ){
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
        }else if(password.length < 8 || password.none { it.isUpperCase() }){//Que la contraseña no tenga minimo 8 o ninguna mayúscula
            Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres y contener al menos una mayúscula", Toast.LENGTH_SHORT).show()
        }else{
//            Que nos cree un nuevo registro en el authentication, además, que nos notifique cuando eso ocurra
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                if(it.isSuccessful){//Si se ha creado de forma satisfactoria, o ha pasado algo inesperado
                    showNewContent()
                }else{
                    val builder =AlertDialog.Builder(this@Registrarse)
                    builder.setTitle("Error")
                    builder.setMessage("Se ha producido un error autenticando al usuario. Inténtelo más tarde")
                    builder.setPositiveButton("Aceptar", null)
                    val dialog: AlertDialog= builder.create()
                    dialog.show()
                }
            }
            //Guardamos en la base de datos los datos restantes, con el indicador del email guardado en el authenticator
            db.collection("tablaUsuarios").document(email).set(
                hashMapOf("nombre" to nombre,
                    "apellidos" to apellidos,
                    "telefono" to telefono)
            )
        }
    }
    private fun showNewContent(){//Nos lleva a la clase menú
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }
}