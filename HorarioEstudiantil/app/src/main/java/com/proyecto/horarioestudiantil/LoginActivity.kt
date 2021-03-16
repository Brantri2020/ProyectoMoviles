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


}
