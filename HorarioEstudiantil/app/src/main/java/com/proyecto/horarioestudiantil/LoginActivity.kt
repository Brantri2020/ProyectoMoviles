package com.proyecto.horarioestudiantil

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.proyecto.horarioestudiantil.database.HorarioDbHelper

class LoginActivity : AppCompatActivity() {

    var estudiante = arrayListOf<EstudianteModelClass>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ///Cambiar a modo vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        //Contraseña Quemada


        val editTexCorreo = findViewById<EditText>(R.id.editTextCorreo)
        val editTexPassword = findViewById<EditText>(R.id.editTextTextPassword)


        val buttonLogin = findViewById(R.id.btnIngresar) as Button
        buttonLogin.setOnClickListener {

            if (editTexCorreo.text.toString().length != 0 && editTexPassword.text.toString().length != 0) {

                var arrayLogin = arrayOf("","","")

                arrayLogin = consultarEstudiante(
                    editTexCorreo.text.toString(),
                    editTexPassword.text.toString()
                )


                var mensaje: String? = arrayLogin[0]
                var idEstudiante: String? = arrayLogin[1]
                var nombreEstudiante: String?= arrayLogin[2]

                if (mensaje == "Contraseña incorrecta" || mensaje == "El usuario no existe") {
                    Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()

                    if(mensaje=="Contraseña incorrecta"){
                        editTexPassword.setText("")
                    }else{
                        editTexCorreo.setText("")
                        editTexPassword.setText("")
                    }


                } else {
                    Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
                    var intent = Intent(this, InicioActivity::class.java)
                    intent.putExtra(ID_ESTUDIANTE,idEstudiante.toString())
                    intent.putExtra(NOMBRE_ESTUDIANTE,nombreEstudiante.toString())
                    startActivity(intent)
                    finish()
                }


            } else {
                Toast.makeText(this, getString(R.string.camposVacios), Toast.LENGTH_LONG).show()
            }


        }


        val buttonLinkRegister = findViewById(R.id.btnLinkRegistrar) as Button
        buttonLinkRegister.setOnClickListener{
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)

        }
    }

    fun consultarEstudiante(email: String?, pass: String?): Array<String> {
        var arrayLogin = arrayOf("","")
        var mensaje: String? = ""
        var idEstudiante: String?=""
        arrayLogin = HorarioDbHelper(this).readAllStudent(email, pass)

        return arrayLogin

    }

}