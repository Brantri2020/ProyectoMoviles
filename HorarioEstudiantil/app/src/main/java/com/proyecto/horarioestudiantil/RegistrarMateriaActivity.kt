package com.proyecto.horarioestudiantil

import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegistrarMateriaActivity : AppCompatActivity() {


    var selectedStudentPosition = 0

    var estudiantesIdDocumentos = ArrayList<String>()

    lateinit var editTextTextNombreMateri: EditText
    lateinit var btnRegistrarMateri: Button
    lateinit var listViewSubjects: ListView

    lateinit var btnActualizarMateria: Button
    lateinit var btnDeleteMateria: Button

    val materias = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_materia)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)


        //Variable pasada
        var correoEstudiante: String
        correoEstudiante = intent.getStringExtra(CORREO_ESTUDIANTE).toString()


        //Inicializar variables
        editTextTextNombreMateri = findViewById(R.id.editTextTextNombreMateri)
        btnRegistrarMateri = findViewById(R.id.btnRegistrarMateri)
        listViewSubjects = findViewById(R.id.listViewReminder)
        btnActualizarMateria = findViewById(R.id.btnActualizarRec)
        btnDeleteMateria = findViewById(R.id.btnEliminarRec)



        consultarMaterias(correoEstudiante)


        listViewSubjects.setOnItemClickListener { parent, view, position, id ->
            selectedStudentPosition = position
            editTextTextNombreMateri.setText(materias[selectedStudentPosition].toString())
        }




        btnRegistrarMateri.setOnClickListener {

            crearMaterias(correoEstudiante, editTextTextNombreMateri.text.toString())

        }



        btnActualizarMateria.setOnClickListener {

            val nombreMateria = editTextTextNombreMateri.text.toString()
            actualizarMaterias(
                nombreMateria,
                materias[selectedStudentPosition].toString(),
                correoEstudiante
            )

        }

        btnDeleteMateria.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle(R.string.horarioEliminado)
            dialogBuilder.setMessage("¿Esta seguro que desea eliminar la materia ${materias[selectedStudentPosition]}?")
            dialogBuilder.setPositiveButton(
                R.string.borrar,
                DialogInterface.OnClickListener { _, _ ->

                    eliminarMaterias(materias[selectedStudentPosition].toString(), correoEstudiante)
                    consultarMaterias(correoEstudiante)

                })
            dialogBuilder.setNegativeButton(
                R.string.cancelar,
                DialogInterface.OnClickListener { dialog, which ->
                    //pass
                })
            dialogBuilder.create().show()
        }


    }


    fun consultarMaterias(correo: String) {
        val db = Firebase.firestore
        val docRef = db.collection(COLECCION).document(correo).collection(COLECCION_MATERIAS)
            .get()
            .addOnSuccessListener { document ->
                materias.clear()
                estudiantesIdDocumentos.clear()

                for (result in document) {
                    materias.add(result.id)
                }
                //Poblar en ListView información usando mi adaptador
                val contactoAdaptador = MateriaAdapter(this, materias as ArrayList<String>)
                listViewSubjects.adapter = contactoAdaptador


            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Error al obtener datos de materias:-> {$e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        limpiarCamposEditables()
    }

    fun crearMaterias(correo: String, nombreMateria: String) {


        val db = Firebase.firestore
        db.collection(COLECCION).document(correo).collection(COLECCION_MATERIAS).document(
            nombreMateria
        ).set(MateriaModelClass(0))

        consultarMaterias(correo)

    }

    fun actualizarMaterias(materia: String, idDocumentoSeleccionado: String, correo: String) {

        val db = Firebase.firestore

        db.collection(COLECCION).document(correo).collection(COLECCION_MATERIAS)
            .document(idDocumentoSeleccionado)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    var data = document.data
                    if (data != null) {
                        db.collection(COLECCION).document(correo).collection(COLECCION_MATERIAS)
                            .document(materia).set(data)
                        db.collection(COLECCION).document(correo).collection(COLECCION_MATERIAS)
                            .document(
                                idDocumentoSeleccionado
                            ).delete()
                    }
                } else {

                }
            }

        consultarMaterias(correo)


    }


    fun eliminarMaterias(idDocumentoSeleccionado: String, correo: String) {
        val db = Firebase.firestore
        db.collection(COLECCION).document(correo).collection(COLECCION_MATERIAS).document(
            idDocumentoSeleccionado
        ).delete()
            .addOnSuccessListener {
                Toast.makeText(this, R.string.materiaEliminada, Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Error al eliminar LA MATERIA:-> {$e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }


    private fun limpiarCamposEditables() {
        editTextTextNombreMateri.setText("")
    }

}