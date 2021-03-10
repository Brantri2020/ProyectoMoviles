package com.proyecto.horarioestudiantil

import android.content.DialogInterface
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.proyecto.horarioestudiantil.database.HorarioDbHelper

class RegistrarMateriaActivity : AppCompatActivity() {

    var materias = arrayListOf<MateriaModelClass>()
    var selectedSubjectPosition = 0
    lateinit var editTextTextNombreMateri: EditText
    lateinit var btnRegistrarMateri: Button
    lateinit var listViewSubjects: ListView

    lateinit var btnActualizarMateria: Button
    lateinit var btnDeleteMateria: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_materia)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)



        //Variable pasada
        var idEstudiante: String
        idEstudiante = intent.getStringExtra(ID_ESTUDIANTE).toString()
        var idEstudiante_int:Int=idEstudiante.toInt()

        //Inicializar variables
        editTextTextNombreMateri = findViewById(R.id.editTextTextNombreMateri)
        btnRegistrarMateri = findViewById(R.id.btnRegistrarMateri)
        listViewSubjects = findViewById(R.id.listViewSchedule)
        btnActualizarMateria= findViewById(R.id.btnActualizarHorarioApp)
        btnDeleteMateria= findViewById(R.id.btnEliminarHorarioApp)



        consultarMaterias(idEstudiante)

        listViewSubjects.setOnItemClickListener { parent, view, position, id ->
            selectedSubjectPosition = position
            editTextTextNombreMateri.setText(materias[selectedSubjectPosition].NameSubject.toString())
        }




        btnRegistrarMateri.setOnClickListener {


            val nombreMateria = editTextTextNombreMateri.text.toString()

            val existe:Boolean=HorarioDbHelper(this).consultarMateria(nombreMateria)

            if (nombreMateria != ""&&existe==false) {

                //contactos.add(ContactoModelClass(id,nombre,apellido,telefono, email))
                val respuesta = HorarioDbHelper(this).createSubject(
                    MateriaModelClass(1,
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
                    consultarMaterias(idEstudiante)


                }

            } else {

                if(existe){
                    Toast.makeText(this, "El nombre de la materia ya existe", Toast.LENGTH_LONG)
                        .show()
                }else {
                    Toast.makeText(this, "Debe poner el nombre de la materia", Toast.LENGTH_LONG)
                        .show()
                }
            }


        }

        btnActualizarMateria.setOnClickListener {
            /* contactos[selectedContactPosition].userId = editTextUserId.text.toString().toInt()
             contactos[selectedContactPosition].firstName = editTextFirstName.text.toString()
             contactos[selectedContactPosition].lastName = editTextLastName.text.toString()
             contactos[selectedContactPosition].phoneNumber = editTextPhoneNumber.text.toString()
             contactos[selectedContactPosition].emailAddress = editTextEmailAddress.text.toString()
             */


            val nombreMateria = editTextTextNombreMateri.text.toString()

            val respuesta = HorarioDbHelper(this).actualizarMateria(MateriaModelClass(1,nombreMateria,idEstudiante_int),materias[selectedSubjectPosition].NameSubject.toString())

            if (respuesta == -1) {
                Toast.makeText(this, "Error al actualizar la nueva materia", Toast.LENGTH_LONG).show()
            } else {

                Toast.makeText(this, "Materia actualizada exitosamente", Toast.LENGTH_LONG).show()
            }


            consultarMaterias(idEstudiante)
        }

        btnDeleteMateria.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Confirmación de Eliminación")
            dialogBuilder.setMessage("¿Esta seguro que desea eliminar la materia ${materias[selectedSubjectPosition].NameSubject}?")
            dialogBuilder.setPositiveButton("Eliminar", DialogInterface.OnClickListener { _, _ ->
                //contactos.removeAt(selectedContactPosition)

                val filasBorradas = HorarioDbHelper(this).eliminarMateria(materias[selectedSubjectPosition].NameSubject.toString())

                if (filasBorradas < 1) {
                    Toast.makeText(this, "Error al eliminar materia", Toast.LENGTH_LONG).show()
                } else {

                    Toast.makeText(this, "Materia eliminada exitosamente", Toast.LENGTH_LONG).show()
                }

                consultarMaterias(idEstudiante)
            })
            dialogBuilder.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
                //pass
            })
            dialogBuilder.create().show()
        }


    }

    fun consultarMaterias(idEstudiante: String?) {
        materias = HorarioDbHelper(this).readAllSubjects(idEstudiante)


        //Poblar en listview informacion usando el adaptador
        val materiaAdapter = MateriaAdapter(this, materias)
        listViewSubjects.adapter = materiaAdapter
        limpiarCamposEditables()
    }



    private fun limpiarCamposEditables() {
        editTextTextNombreMateri.setText("")
    }

}