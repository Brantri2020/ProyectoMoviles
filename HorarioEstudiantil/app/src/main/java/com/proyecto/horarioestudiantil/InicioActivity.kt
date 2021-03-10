package com.proyecto.horarioestudiantil

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class InicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
        ///Cambiar a modo vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        //Id del estudiante logueado
        var idEstudiante:String
        var nombreEstudiante:String
        idEstudiante = intent.getStringExtra(ID_ESTUDIANTE).toString()
        nombreEstudiante = intent.getStringExtra(NOMBRE_ESTUDIANTE).toString()

        val actionBar = supportActionBar
        // Set the action bar title, subtitle and elevation
        actionBar!!.title = supportActionBar?.title.toString()
        actionBar.subtitle = nombreEstudiante
        actionBar.elevation = 4.0F



        val buttonRegistrarMateria = findViewById(R.id.btnRegistrarMateria) as Button
        buttonRegistrarMateria.setOnClickListener {
            val n = Intent(this, RegistrarMateriaActivity::class.java)
            n.putExtra(ID_ESTUDIANTE,idEstudiante.toString())
            startActivity(n)

        }



        val buttonRegistrarHorario = findViewById(R.id.btnRegistrarHorario) as Button
        buttonRegistrarHorario.setOnClickListener {
            val i = Intent(this, RegistrarHorarioActivity::class.java)
            i.putExtra(ID_ESTUDIANTE,idEstudiante.toString())
            startActivity(i)

        }

        val buttonVerHorario = findViewById(R.id.btnVerHorario) as Button
        buttonVerHorario.setOnClickListener {
            val j = Intent(this, VerHorarioActivity::class.java)
            startActivity(j)

        }

        val buttonRegistrarRecordatorio = findViewById(R.id.btnRegistrarRecordatorio) as Button
        buttonRegistrarRecordatorio.setOnClickListener {
            val j = Intent(this, RegistrarRecordatorioActivity::class.java)
            startActivity(j)
        }

        val buttonVerRecordatorio = findViewById(R.id.btnVerRecordatorio) as Button
        buttonVerRecordatorio.setOnClickListener {
            val k = Intent(this, VerRecordatoriosActivity::class.java)
            startActivity(k)
        }

        val buttonSalirInicio = findViewById(R.id.btnSalir) as Button
        buttonSalirInicio.setOnClickListener {
            val l = Intent(this, LoginActivity::class.java)
            startActivity(l)
        }

    }
}