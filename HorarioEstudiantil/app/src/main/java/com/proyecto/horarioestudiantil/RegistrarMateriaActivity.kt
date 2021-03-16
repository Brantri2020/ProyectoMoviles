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
    var idDocumentoMateriaSeleccionado: String = ""
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
            /*

            val nombreMateria = editTextTextNombreMateri.text.toString()

            val existe: Boolean = HorarioDbHelper(this).consultarMateria(nombreMateria)

            if (nombreMateria != "" && existe == false) {

                //contactos.add(ContactoModelClass(id,nombre,apellido,telefono, email))
                val respuesta = HorarioDbHelper(this).createSubject(
                    MateriaModelClass(
                        1,
                        nombreMateria,
                        idEstudiante_int
                    )
                )

                if (respuesta == -1) {
                    Toast.makeText(this, "Error al añadir el nuevo registro", Toast.LENGTH_LONG)
                        .show()
                } else {
                    editTextTextNombreMateri.setText("")

                    Toast.makeText(this, "Registrado con éxito", Toast.LENGTH_LONG).show()
                    //consultarMaterias(idEstudiante)


                }

            } else {

                if (existe) {
                    Toast.makeText(this, "El nombre de la materia ya existe", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(this, "Debe poner el nombre de la materia", Toast.LENGTH_LONG)
                        .show()
                }
            }
*/

        }



        btnActualizarMateria.setOnClickListener {
            /* contactos[selectedContactPosition].userId = editTextUserId.text.toString().toInt()
             contactos[selectedContactPosition].firstName = editTextFirstName.text.toString()
             contactos[selectedContactPosition].lastName = editTextLastName.text.toString()
             contactos[selectedContactPosition].phoneNumber = editTextPhoneNumber.text.toString()
             contactos[selectedContactPosition].emailAddress = editTextEmailAddress.text.toString()
             */


            val nombreMateria = editTextTextNombreMateri.text.toString()
            actualizarMaterias(
                nombreMateria,
                materias[selectedStudentPosition].toString(),
                correoEstudiante
            )
/*
            val respuesta = HorarioDbHelper(this).actualizarMateria(
                MateriaModelClass(
                    1,
                    nombreMateria,
                    idEstudiante_int
                ), materias[selectedSubjectPosition].NameSubject.toString()
            )

            if (respuesta == -1) {
                Toast.makeText(this, "Error al actualizar la nueva materia", Toast.LENGTH_LONG)
                    .show()
            } else {

                Toast.makeText(this, "Materia actualizada exitosamente", Toast.LENGTH_LONG).show()
            }

*/
            //consultarMaterias(idEstudiante)
        }

        btnDeleteMateria.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Confirmación de Eliminación")
            dialogBuilder.setMessage("¿Esta seguro que desea eliminar la materia ${materias[selectedStudentPosition]}?")
            dialogBuilder.setPositiveButton("Eliminar", DialogInterface.OnClickListener { _, _ ->
            //contactos.removeAt(selectedContactPosition)
            eliminarMaterias(materias[selectedStudentPosition].toString(),correoEstudiante)
                consultarMaterias(correoEstudiante)

            })
            dialogBuilder.setNegativeButton(
                "Cancelar",
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
        /*val contactoHashMap = mapOf(
                "userId" to contacto.userId,
                "firstName" to contacto.firstName,
                "lastName" to contacto.lastName,
                "phoneNumber" to contacto.phoneNumber,
                "emailAddress" to contacto.emailAddress
        )*/
        val db = Firebase.firestore

        db.collection(COLECCION).document(correo).collection(COLECCION_MATERIAS).document(idDocumentoSeleccionado)
        .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    var data = document.data
                    if (data != null) {
                        db.collection(COLECCION).document(correo).collection(COLECCION_MATERIAS).document(materia).set(data)
                        db.collection(COLECCION).document(correo).collection(COLECCION_MATERIAS).document(
                            idDocumentoSeleccionado
                        ).delete()
                    }
                } else {
                    //Log.d(TAG, "No such document")
                }
            }

        consultarMaterias(correo)


        /*
        .document(idDocumentoSeleccionado)
        //.update(contactoHashMap)
        .set(contacto) //ootra forma de actualizar
        .addOnSuccessListener {
            Toast.makeText(this, "Contacto actualizado exitosamente", Toast.LENGTH_LONG).show()
        }
        .addOnFailureListener { e ->
            Toast.makeText(
                this,
                "Error al actualizar el contacto:-> {$e.message}",
                Toast.LENGTH_LONG
            ).show()
        }*/
    }


    fun eliminarMaterias(idDocumentoSeleccionado: String,correo: String) {
        val db = Firebase.firestore
        db.collection(COLECCION).document(correo).collection(COLECCION_MATERIAS).document(
            idDocumentoSeleccionado
        ).delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Materia eliminada exitosamente", Toast.LENGTH_LONG).show()
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
