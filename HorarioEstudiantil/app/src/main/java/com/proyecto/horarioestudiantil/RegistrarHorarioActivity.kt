package com.proyecto.horarioestudiantil

import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class RegistrarHorarioActivity : AppCompatActivity() {

    var horariosIdDocumentos = ArrayList<String>()
    var horarios = arrayListOf<HorarioModelClass>()
    var materias = arrayListOf<String>()
    var ids = arrayListOf<Int>()
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
        var correoEstudiante: String
        correoEstudiante = intent.getStringExtra(CORREO_ESTUDIANTE).toString()


        //Inicializar variables
        spinnerMateria = findViewById(R.id.spinnerMateria)
        spinnerDia = findViewById(R.id.spinnerDia)
        spinnerHoraInicio = findViewById(R.id.spinnerHoraInicio)
        spinnerHoraFin = findViewById(R.id.spinnerHoraFIn)
        listViewSchedule = findViewById(R.id.listViewReminder)

        btnRegistrarHorarioApp = findViewById(R.id.btnRegistrarRec)
        btnActualizarHorarioApp = findViewById(R.id.btnActualizarRec)
        btnEliminarHorarioApp = findViewById(R.id.btnEliminarRec)


        /*/////////////////////////////////ListView escuchador

        */
        consultarHorario(correoEstudiante)
/*
        listViewSchedule.setOnItemClickListener { parent, view, position, id ->
            selectedSchedulePosition = position
            //editTextTextNombreMateri.setText(materias[selectedSubjectPosition].NameSubject.toString())
        }
*/

        ///Llenar spinner Materia

        val subjects = ArrayList<String>()


        val db = Firebase.firestore
        val docRef =
            db.collection(COLECCION).document(correoEstudiante).collection(COLECCION_MATERIAS)
                .get()
                .addOnSuccessListener { document ->

                    for (result in document) {
                        subjects.add(result.id.toString())
                    }

                    val arrayAdapter =
                        ArrayAdapter(this, android.R.layout.simple_spinner_item, subjects)
                    spinnerMateria?.adapter = arrayAdapter
                }


        var materiaString: String = ""
        spinnerMateria?.onItemSelectedListener = object :


            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                materiaString = spinnerMateria.selectedItem.toString()


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
            arrayOf(
                "7:00",
                "8:00",
                "9:00",
                "10:00",
                "11:00",
                "12:00",
                "13:00",
                "14:00",
                "15:00",
                "16:00",
                "17:00",
                "18:00",
                "19:00",
                "20:00"
            )
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
            "8:00",
            "9:00",
            "10:00",
            "11:00",
            "12:00",
            "13:00",
            "14:00",
            "15:00",
            "16:00",
            "17:00",
            "18:00",
            "19:00",
            "20:00",
            "21:00"
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

        var seleccionado: String = ""

        listViewSchedule.setOnItemClickListener { parent, view, position, id ->
            selectedSchedulePosition = position
            seleccionado = horarios[selectedSchedulePosition].materia.toString()
            selectedSchedulePosition = position
            spinnerMateria.setSelection(
                getIndex(
                    spinnerMateria,
                    horarios[selectedSchedulePosition].materia.toString()
                )
            )
            spinnerDia.setSelection(
                getIndex(
                    spinnerDia,
                    horarios[selectedSchedulePosition].dia.toString()
                )
            )
            spinnerHoraInicio.setSelection(
                getIndex(
                    spinnerHoraInicio,
                    horarios[selectedSchedulePosition].horaInicio.toString()
                )
            )
            spinnerHoraFin.setSelection(
                getIndex(
                    spinnerHoraFin,
                    horarios[selectedSchedulePosition].horaFin.toString()
                )
            )

        }



        btnRegistrarHorarioApp.setOnClickListener {

            //Crear variables para enviar tabla
            val nombreMateria = spinnerMateria.selectedItem.toString()
            val dia = spinnerDia.selectedItem.toString()
            val horaInicio = spinnerHoraInicio.selectedItem.toString()
            val horaFin = spinnerHoraFin.selectedItem.toString()


            crearHorario(
                HorarioModelClass(nombreMateria, dia, horaInicio, horaFin),
                correoEstudiante
            )
            consultarHorario(correoEstudiante)

        }

        btnActualizarHorarioApp.setOnClickListener {

            //Crear variables para enviar tabla
            val nombreMateria = spinnerMateria.selectedItem.toString()
            val dia = spinnerDia.selectedItem.toString()
            val horaInicio = spinnerHoraInicio.selectedItem.toString()
            val horaFin = spinnerHoraFin.selectedItem.toString()
            actualizarHorario(
                HorarioModelClass(nombreMateria, dia, horaInicio, horaFin),
                correoEstudiante,
                HorarioModelClass(
                    horarios[selectedSchedulePosition].materia,
                    horarios[selectedSchedulePosition].dia,
                    horarios[selectedSchedulePosition].horaInicio,
                    horarios[selectedSchedulePosition].horaFin
                )
            )
            /*
            val respuesta = ContactDbHelper(this).updateContact(ContactoModelClass(id,nombre,apellido,telefono, email))
            if(respuesta == -1) {
                Toast.makeText(this,"Error al actualizar el contacto", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this,"Contacto actualizado exitosamente", Toast.LENGTH_LONG).show()
            }*/
            consultarHorario(correoEstudiante)


        }

        btnEliminarHorarioApp.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Confirmación de Eliminación")
            //dialogBuilder.setIcon(R.drawable.ic_warning)
            dialogBuilder.setMessage("¿Esta seguro que desea eliminar el horario?")
            dialogBuilder.setPositiveButton("Eliminar", DialogInterface.OnClickListener { _, _ ->
                /*//contactos.removeAt(selectedContactPosition)
                val userId = editTextUserId.text.toString().toInt()
                val filasBorradas = ContactDbHelper(this).deleteContact(userId)
                if(filasBorradas == 0) {
                    Toast.makeText(this,"Error al eliminar el contacto", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this,"Contacto eliminado exitosamente", Toast.LENGTH_LONG).show()
                }*/
                eliminarHorario(
                    correoEstudiante,
                    HorarioModelClass(
                        horarios[selectedSchedulePosition].materia,
                        horarios[selectedSchedulePosition].dia,
                        horarios[selectedSchedulePosition].horaInicio,
                        horarios[selectedSchedulePosition].horaFin
                    )
                )

                consultarHorario(correoEstudiante)
            })
            dialogBuilder.setNegativeButton(
                "Cancel",
                DialogInterface.OnClickListener { dialog, which ->
                    //pass
                })
            dialogBuilder.create().show()

            //////----/////


        }

    }


    fun consultarHorario(correo: String) {
        val db = Firebase.firestore
        horarios.clear()
        db.collection(COLECCION).document(correo).collection(COLECCION_MATERIAS)
            .get()
            .addOnSuccessListener { document ->
                for (result in document) {
                    //horarios.clear()

                    db.collection(COLECCION).document(correo).collection(COLECCION_MATERIAS)
                        .document(result.id).collection(
                            COLECCION
                        )
                        .get()
                        .addOnSuccessListener { document2 ->
                            //horarios.clear()
                            for (result2 in document2) {


                                horarios.add(
                                    HorarioModelClass(
                                        result.id, result2.get("dia").toString(), result2.get(
                                            "horaInicio"
                                        ).toString(), result2.get("horaFin").toString()
                                    )
                                )
                                //Log.d("jfihasi",horario.toString())

                            }

                            //Poblar en ListView información usando mi adaptador
                            val horarioAdaptador = HorarioAdapter(this, horarios)
                            listViewSchedule.adapter = horarioAdaptador


                        }


                }
                //Poblar en ListView información usando mi adaptador
                //val contactoAdaptador = MateriaAdapter(this, materias as ArrayList<String>)
                //listViewSubjects.adapter = contactoAdaptador


            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Error al obtener datos de horarios:-> {$e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        limpiarCamposEditables()
    }


    fun limpiarCamposEditables() {
        spinnerMateria.setSelection(0)
        spinnerDia.setSelection(0)
        spinnerHoraInicio.setSelection(0)
        spinnerHoraFin.setSelection(0)

    }

    fun crearHorario(horario: HorarioModelClass, correo: String) {

        /*val contactoHashMap = hashMapOf(
                "userId" to contactoNuevo.userId,
                "firstName" to contactoNuevo.firstName,
                "lastName" to contactoNuevo.lastName,
                "phoneNumber" to contactoNuevo.phoneNumber,
                "emailAddress" to contactoNuevo.emailAddress
        )*/
        val db = Firebase.firestore
        ids.clear()
        var id: Int = 1
        val docRef = db.collection(COLECCION).document(correo).collection(COLECCION_MATERIAS)
            .document(horario.materia).collection(
                COLECCION
            )
            .get()
            .addOnSuccessListener { document ->
                for (result in document) {
                    //contador++
                    ids.add(result.id.toInt())
                }
            id=devolverId(ids)
                db.collection(COLECCION).document(correo).collection(COLECCION_MATERIAS)
                    .document(horario.materia).collection(
                        COLECCION
                    ).document(id.toString())
                    .set(
                        HorarioCrearModelClass(
                            horario.dia,
                            horario.horaInicio,
                            horario.horaFin
                        )
                    ) //.add(contactoHashMap)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(this, "Horario creado exitosamente", Toast.LENGTH_LONG)
                            .show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            this,
                            "Error al crear el horario:-> {$e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
            .addOnFailureListener { e ->
                db.collection(COLECCION).document(correo).collection(COLECCION_MATERIAS)
                    .document(horario.materia).collection(
                        COLECCION
                    ).document(id.toString())
                    .set(
                        HorarioCrearModelClass(
                            horario.dia,
                            horario.horaInicio,
                            horario.horaFin
                        )
                    ) //.add(contactoHashMap)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(this, "Horario creado exitosamente", Toast.LENGTH_LONG)
                            .show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            this,
                            "Error al crear el horario:-> {$e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
    }

    fun actualizarHorario(
        horarioNuevo: HorarioModelClass,
        correo: String,
        horarioViejo: HorarioModelClass
    ) {


        val db = Firebase.firestore

        var idDocumento: String = ""

        if (!horarioNuevo.materia.equals(horarioViejo.materia)) {

            crearHorario(horarioNuevo,correo)
            eliminarHorario(correo,horarioViejo)

        }else{


        val docRef = db.collection(COLECCION).document(correo).collection(COLECCION_MATERIAS)
            .document(horarioViejo.materia).collection(
                COLECCION
            )
            .get()
            .addOnSuccessListener { document ->
                for (result in document) {


                    if (result.get("dia").toString()
                            .equals(horarioViejo.dia) && result.get("horaInicio")
                            .toString()
                            .equals(horarioViejo.horaInicio) && result.get("horaFin")
                            .toString().equals(horarioViejo.horaFin)
                    ) {
                        idDocumento = result.id
                    }
                }

                db.collection(COLECCION).document(correo).collection(COLECCION_MATERIAS)
                    .document(horarioViejo.materia).collection(
                        COLECCION
                    ).document(idDocumento)
                    .set(
                        HorarioModelClass(
                            horarioNuevo.materia,
                            horarioNuevo.dia,
                            horarioNuevo.horaInicio,
                            horarioNuevo.horaFin
                        )
                    ) //.add(contactoHashMap)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(this, "Horario actualizado exitosamente", Toast.LENGTH_LONG)
                            .show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            this,
                            "Error al actualizar el horario:-> {$e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
    }
    }

    fun eliminarHorario( correo: String,
                         horarioViejo: HorarioModelClass) {

        val db = Firebase.firestore

        var idDocumento: String = ""
        val docRef = db.collection(COLECCION).document(correo).collection(COLECCION_MATERIAS)
            .document(horarioViejo.materia).collection(
                COLECCION
            )
            .get()
            .addOnSuccessListener { document ->
                for (result in document) {

                    if (result.get("dia").toString()
                            .equals(horarioViejo.dia) && result.get("horaInicio")
                            .toString()
                            .equals(horarioViejo.horaInicio) && result.get("horaFin")
                            .toString().equals(horarioViejo.horaFin)
                    ) {
                        idDocumento = result.id
                    }
                }

                //val db = Firebase.firestore
                db.collection(COLECCION)
                    .document(correo).collection(COLECCION_MATERIAS).document(horarioViejo.materia)
                    .collection(COLECCION)
                    .document(idDocumento)
                    .delete()
                    .addOnSuccessListener {
                        Toast.makeText(this, "Horario eliminado exitosamente", Toast.LENGTH_LONG)
                            .show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            this,
                            "Error al eliminar el horario:-> {$e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
    }
    private fun getIndex(spinner: Spinner, myString: String): Int {
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString().equals(myString, ignoreCase = true)) {
                return i
            }
        }
        return 0
    }

    private fun devolverId(arrayList: ArrayList<Int>):Int{
        var id:Int=0
        arrayList.add(-1)
        for(i in 1 .. arrayList.size){

            if(i != arrayList[i-1]){
                id= i
                break
            }


        }


        return id
    }



}

