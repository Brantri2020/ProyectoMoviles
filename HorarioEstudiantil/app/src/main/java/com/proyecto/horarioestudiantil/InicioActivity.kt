package com.proyecto.horarioestudiantil

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class InicioActivity : AppCompatActivity() {

    var estudiantes = arrayListOf<EstudianteModelClass>()
    var estudiantesIdDocumentos = ArrayList<String>()
    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
        ///Cambiar a modo vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        //Correo del estudiante logueado
        var correoEstudiante: String
        correoEstudiante = intent.getStringExtra(CORREO_ESTUDIANTE).toString()

        nombresEstudiante(correoEstudiante)




        val buttonRegistrarMateria = findViewById(R.id.btnRegistrarMateria) as Button
        buttonRegistrarMateria.setOnClickListener {
            val n = Intent(this, RegistrarMateriaActivity::class.java)
            n.putExtra(CORREO_ESTUDIANTE, correoEstudiante)
            startActivity(n)

        }



        val buttonRegistrarHorario = findViewById(R.id.btnRegistrarHorario) as Button
        buttonRegistrarHorario.setOnClickListener {
            val i = Intent(this, RegistrarHorarioActivity::class.java)
           i.putExtra(CORREO_ESTUDIANTE,correoEstudiante)
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
            j.putExtra(CORREO_ESTUDIANTE,correoEstudiante)
            startActivity(j)
        }


        val buttonSalirInicio = findViewById(R.id.btnSalir) as Button
        buttonSalirInicio.setOnClickListener {
            val l = Intent(this, LoginActivity::class.java)
            FirebaseAuth.getInstance().signOut();
            startActivity(l)
        }

    }


fun nombresEstudiante(correo:String){

    var nombres:String=""
    var nombre:String=""
    var apellido:String=""
    val docRef = db.collection(COLECCION).document(correo)

    docRef.get()
        .addOnSuccessListener { document ->
            if (document != null) {

                nombre=document.get("nombre").toString()
                apellido=document.get("apellido").toString()

                nombres=nombre+" "+apellido

                val actionBar = supportActionBar
                // Set the action bar title, subtitle and elevation
                actionBar!!.title = supportActionBar?.title.toString()
                actionBar.subtitle = nombres
                actionBar.elevation = 4.0F


            } else {
                // Log.d(TAG, "No such document")
            }
        }
        .addOnFailureListener { exception ->
            //Log.d(TAG, "get failed with ", exception)
        }


}



}
