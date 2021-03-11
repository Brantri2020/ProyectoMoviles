package com.proyecto.horarioestudiantil

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.proyecto.horarioestudiantil.database.HorarioDbHelper

class RegisterActivity : AppCompatActivity() {

    lateinit var editTextNombre: EditText
    lateinit var editTextApellido: EditText
    lateinit var editTextCorreoRegistro: EditText
    lateinit var editTextContraseñaRegistro: EditText
    lateinit var editTextContraseñaRegistro2: EditText
    lateinit var btnRegistrar: Button

    //API GOOGle
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        ///Cambiar a modo vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        auth = FirebaseAuth.getInstance();
        auth = Firebase.auth


        //Inicializar variables
        editTextNombre = findViewById(R.id.editTextNombre)
        editTextApellido = findViewById(R.id.editTextApellido)
        editTextCorreoRegistro = findViewById(R.id.editTextCorreoRegistro)
        editTextContraseñaRegistro = findViewById(R.id.editTextContraseñaRegistro)
        editTextContraseñaRegistro2 = findViewById(R.id.editTextContraseñaRegistro2)
        btnRegistrar = findViewById(R.id.btnRegistrar)


        btnRegistrar.setOnClickListener {

            if (ValidarDatos()) {
                //return@setOnClickListener
                SignUpNewUser(editTextCorreoRegistro.text.toString(),editTextContraseñaRegistro.text.toString(),editTextNombre.text.toString(),editTextApellido.text.toString())
                //Toast.makeText(this, getString(R.string.Registrado), Toast.LENGTH_SHORT).show()
                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }




        }


    }






    fun SignUpNewUser(email:String, password:String, nombre:String,apellido:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //val user = auth.currentUser
              Toast.makeText(baseContext, getString(R.string.usuarioCreado),
                        Toast.LENGTH_SHORT).show()
                    crearEstudiante(EstudianteModelClass(nombre, apellido, email))


                } else {
                    Toast.makeText(baseContext, task.exception!!.message,
                        Toast.LENGTH_SHORT).show()
                }


            }

    }



    fun ValidarDatos(): Boolean {

        fun CharSequence?.isValidEmail() =
            !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()


        if (editTextNombre.text.isNullOrEmpty()) {
            editTextNombre.setError(getString(R.string.nombreVacio))
            editTextNombre.requestFocus()
            return false
        }

        if (editTextApellido.text.isNullOrEmpty()) {
            editTextApellido.setError(getString(R.string.apellidoVacio))
            editTextApellido.requestFocus()
            return false
        }


        if (editTextCorreoRegistro.text.isNullOrEmpty()) {
            editTextCorreoRegistro.setError(getString(R.string.editTextTextEmailAddress_hint))
            editTextCorreoRegistro.requestFocus()
            return false
        }
        if (!editTextCorreoRegistro.text.isValidEmail()) {
            editTextCorreoRegistro.setError(getString(R.string.email_NoValido))
            editTextCorreoRegistro.requestFocus()
            return false
        }
        if (editTextContraseñaRegistro.text.isNullOrEmpty()) {
            editTextContraseñaRegistro.setError(getString(R.string.editTextPassword_hint))
            editTextContraseñaRegistro.requestFocus()
            return false
        }

        if (editTextContraseñaRegistro2.text.isNullOrEmpty()) {
            editTextContraseñaRegistro2.setError(getString(R.string.editTextPassword_hint))
            editTextContraseñaRegistro2.requestFocus()
            return false
        }

        if (editTextContraseñaRegistro.text.length < MIN_PASSWORD_LENGTH) {
            editTextContraseñaRegistro.setError(getString(R.string.passwordLongitud))
            editTextContraseñaRegistro.requestFocus()
            editTextContraseñaRegistro.setText("")
            editTextContraseñaRegistro2.setText("")
            return false
        }

        if(!editTextContraseñaRegistro.text.toString().equals(editTextContraseñaRegistro2.text.toString())){
            editTextContraseñaRegistro.setError(getString(R.string.passwordNoCoinciden))
            editTextContraseñaRegistro2.setError(getString(R.string.passwordNoCoinciden))
            editTextContraseñaRegistro.setText("")
            editTextContraseñaRegistro2.setText("")
            editTextContraseñaRegistro.requestFocus()
            return false
        }




        return true
    }



    fun crearEstudiante(estudiante: EstudianteModelClass) {
        /*val contactoHashMap = hashMapOf(
                "userId" to contactoNuevo.userId,
                "firstName" to contactoNuevo.firstName,
                "lastName" to contactoNuevo.lastName,
                "phoneNumber" to contactoNuevo.phoneNumber,
                "emailAddress" to contactoNuevo.emailAddress
        )*/



        val db = Firebase.firestore

        db.collection(COLECCION).document(estudiante.Correo)
            .set(estudiante)
            .addOnSuccessListener {  }
            .addOnFailureListener {  }

        /*
        db.collection(COLECCION).doc()
            .add(estudiante) //.add(contactoHashMap)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this,"Contacto creadoo exitosamente", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Error al crear el estudiante:-> {$e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }

         */
    }


}