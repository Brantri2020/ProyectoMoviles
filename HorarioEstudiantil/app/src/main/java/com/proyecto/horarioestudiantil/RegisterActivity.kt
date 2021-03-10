package com.proyecto.horarioestudiantil

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.proyecto.horarioestudiantil.database.HorarioDbHelper

class RegisterActivity : AppCompatActivity() {

    lateinit var editTextNombre: EditText
    lateinit var editTextApellido: EditText
    lateinit var editTextCorreoRegistro: EditText
    lateinit var editTextContraseñaRegistro: EditText
    lateinit var editTextContraseñaRegistro2: EditText
    lateinit var btnRegistrar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        ///Cambiar a modo vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        //Inicializar variables
        editTextNombre = findViewById(R.id.editTextNombre)
        editTextApellido = findViewById(R.id.editTextApellido)
        editTextCorreoRegistro = findViewById(R.id.editTextCorreoRegistro)
        editTextContraseñaRegistro = findViewById(R.id.editTextContraseñaRegistro)
        editTextContraseñaRegistro2 = findViewById(R.id.editTextContraseñaRegistro2)
        btnRegistrar = findViewById(R.id.btnRegistrar)


        btnRegistrar.setOnClickListener {

            //val id = editTextUserId.text.toString().toInt()
            val nombre = editTextNombre.text.toString()
            val apellido = editTextApellido.text.toString()
            val correo = editTextCorreoRegistro.text.toString()
            val pass1 = editTextContraseñaRegistro.text.toString()
            val pass2 = editTextContraseñaRegistro2.text.toString()

            if (nombre != "" && apellido != "" && correo != "" && pass1 != "" && pass2 != "") {


                if (pass1 != pass2) {
                    editTextContraseñaRegistro.setText("")
                    editTextContraseñaRegistro2.setText("")
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
                } else {
                    //contactos.add(ContactoModelClass(id,nombre,apellido,telefono, email))
                    val respuesta = HorarioDbHelper(this).createStudent(
                        EstudianteModelClass(
                            nombre,
                            apellido,
                            correo,
                            pass1
                        )
                    )

                    if (respuesta == -1) {
                        Toast.makeText(this, "Error al añadir el nuevo registro", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        editTextNombre.setText("")
                        editTextApellido.setText("")
                        editTextCorreoRegistro.setText("")
                        editTextContraseñaRegistro.setText("")
                        editTextContraseñaRegistro2.setText("")

                        Toast.makeText(this, "Registrado con éxito", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Llene todos lo campos", Toast.LENGTH_LONG).show()
            }

        }


    }
}