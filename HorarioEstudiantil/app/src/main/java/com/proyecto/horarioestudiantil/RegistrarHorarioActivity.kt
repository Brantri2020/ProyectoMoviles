package com.proyecto.horarioestudiantil

import android.content.DialogInterface
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.proyecto.horarioestudiantil.database.HorarioDbHelper

class RegistrarHorarioActivity : AppCompatActivity() {


    var horarios = arrayListOf<HorarioModelClass>()
    var selectedSchedulePosition = 0
    lateinit var spinnerMateria: Spinner
    lateinit var spinnerDia: Spinner
    lateinit var spinnerHoraInicio: Spinner
    lateinit var spinnerHoraFin: Spinner
    lateinit var listViewSchedule: ListView

    lateinit var btnRegistrarHorarioApp: Button
    lateinit var btnActualizarHorarioApp: Button
    lateinit var btnEliminarHorarioApp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_horario)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        //Variable pasada
        var idEstudiante: String
        idEstudiante = intent.getStringExtra(ID_ESTUDIANTE).toString()
        var idEstudiante_int: Int = idEstudiante.toInt()

        //Inicializar variables
        spinnerMateria = findViewById(R.id.spinnerMateria)
        spinnerDia = findViewById(R.id.spinnerDia)
        spinnerHoraInicio = findViewById(R.id.spinnerHoraInicio)
        spinnerHoraFin = findViewById(R.id.spinnerHoraFIn)
        listViewSchedule = findViewById(R.id.listViewSchedule)

        btnRegistrarHorarioApp = findViewById(R.id.btnRegistrarHorarioApp)
        btnActualizarHorarioApp = findViewById(R.id.btnActualizarHorarioApp)
        btnEliminarHorarioApp = findViewById(R.id.btnEliminarHorarioApp)


        /*/////////////////////////////////ListView escuchador
        //consultarHorarios(idEstudiante)

        listViewSchedule.setOnItemClickListener { parent, view, position, id ->
            selectedSchedulePosition = position
            //editTextTextNombreMateri.setText(materias[selectedSubjectPosition].NameSubject.toString())
        }

*/
        ///Llenar spinner Materia

        val materia = HorarioDbHelper(this).llenarSpinnerMateria(idEstudiante)
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, materia)
        spinnerMateria?.adapter = arrayAdapter

        var materiaString: String = "Materias"
        spinnerMateria?.onItemSelectedListener = object :


            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                materiaString = materia[position].toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        ///Llenar spinner Dia

        val dia = arrayOf("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo")
        val arrayAdapterDia = ArrayAdapter(this, android.R.layout.simple_spinner_item, dia)
        spinnerDia?.adapter = arrayAdapterDia

        var diaString: String = "Dias"
        spinnerDia?.onItemSelectedListener = object :


            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                diaString = dia[position].toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        //Llenar Spiner hora inicio
        val horaInicio =
            arrayOf("7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20")
        val arrayAdapterHoraInicio =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, horaInicio)
        spinnerHoraInicio?.adapter = arrayAdapterHoraInicio

        var horaInicioString: String = "Hora inicio"
        spinnerHoraInicio?.onItemSelectedListener = object :


            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                horaInicioString = horaInicio[position].toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        //Llenar Spiner hora fin
        val horaFin = arrayOf(
            "8",
            "9",
            "10",
            "11",
            "12",
            "13",
            "14",
            "15",
            "16",
            "17",
            "18",
            "19",
            "20",
            "21"
        )
        val arrayAdapterHoraFin = ArrayAdapter(this, android.R.layout.simple_spinner_item, horaFin)
        spinnerHoraFin.adapter = arrayAdapterHoraFin

        var horaFinString: String = "Hora fin"
        spinnerHoraFin?.onItemSelectedListener = object :


            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                horaFinString = horaFin[position].toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }



        btnRegistrarHorarioApp.setOnClickListener {

            //Crear variables para enviar tabla
            val nombreMateria = spinnerMateria.selectedItem.toString()
            val dia = spinnerDia.selectedItem.toString()
            val horaInicio = spinnerHoraInicio.selectedItem.toString()
            val horaFin = spinnerHoraFin.selectedItem.toString()


            //Consultar id materia
            val idMateria: Int =
                HorarioDbHelper(this).consultarIdMateria(idEstudiante, nombreMateria)

            val existe: Boolean = HorarioDbHelper(this).consultarDiaHora(dia, horaInicio, horaFin)

            if (existe == false) {

                //contactos.add(ContactoModelClass(id,nombre,apellido,telefono, email))
                val respuesta = HorarioDbHelper(this).createSchedule(
                    HorarioModelClass(
                        0,
                        idMateria,
                        dia,
                        horaInicio,
                        horaFin
                    )
                )

                if (respuesta == -1) {
                    Toast.makeText(this, "Error al añadir el nuevo horario", Toast.LENGTH_LONG)
                        .show()
                } else {
                    spinnerMateria.setSelection(0)
                    spinnerDia.setSelection(0)
                    spinnerHoraInicio.setSelection(0)
                    spinnerHoraFin.setSelection(0)

                    Toast.makeText(this, "Registrado con éxito", Toast.LENGTH_LONG).show()
                    //consultarMaterias(idEstudiante)


                }

            } else {

                Toast.makeText(this, "El horario ya se encuentra ocupado", Toast.LENGTH_LONG)
                    .show()

            }


        }
/*
        btnActualizarMateria.setOnClickListener {
            /* contactos[selectedContactPosition].userId = editTextUserId.text.toString().toInt()
             contactos[selectedContactPosition].firstName = editTextFirstName.text.toString()
             contactos[selectedContactPosition].lastName = editTextLastName.text.toString()
             contactos[selectedContactPosition].phoneNumber = editTextPhoneNumber.text.toString()
             contactos[selectedContactPosition].emailAddress = editTextEmailAddress.text.toString()
             */


            val nombreMateria = editTextTextNombreMateri.text.toString()

            val respuesta = HorarioDbHelper(this).actualizarMateria(
                MateriaModelClass(
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


            consultarMaterias(idEstudiante)
        }

        btnDeleteMateria.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Confirmación de Eliminación")
            dialogBuilder.setMessage("¿Esta seguro que desea eliminar la materia ${materias[selectedSubjectPosition].NameSubject}?")
            dialogBuilder.setPositiveButton("Eliminar", DialogInterface.OnClickListener { _, _ ->
                //contactos.removeAt(selectedContactPosition)

                val filasBorradas =
                    HorarioDbHelper(this).eliminarMateria(materias[selectedSubjectPosition].NameSubject.toString())

                if (filasBorradas < 1) {
                    Toast.makeText(this, "Error al eliminar materia", Toast.LENGTH_LONG).show()
                } else {

                    Toast.makeText(this, "Materia eliminada exitosamente", Toast.LENGTH_LONG).show()
                }

                consultarMaterias(idEstudiante)
            })
            dialogBuilder.setNegativeButton(
                "Cancelar",
                DialogInterface.OnClickListener { dialog, which ->
                    //pass
                })
            dialogBuilder.create().show()
        }
*/

    }


    fun consultarHorarios(idEstudiante: String?) {
        horarios = HorarioDbHelper(this).readAllSchedule(idEstudiante)


        //Poblar en listview informacion usando el adaptador
        val horarioAdapter = HorarioAdapter(this, horarios)
        listViewSchedule.adapter = horarioAdapter
        limpiarCamposEditables()
    }


    private fun limpiarCamposEditables() {
        //TODO
    }


}