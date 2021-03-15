
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


class RegistrarRecordatorioActivity : AppCompatActivity() {

    var horariosIdDocumentos = ArrayList<String>()
    var recordatorios = arrayListOf<RecordatorioModelClass>()
    var materias = arrayListOf<String>()
    var ids = arrayListOf<Int>()
    var selectedSchedulePosition = 0
    lateinit var spinnerMateriaRecordatorio: Spinner
    lateinit var spinnerTipoRecordatorio: Spinner
    lateinit var txtNombreRec: EditText
    lateinit var txtDescripcionRec: EditText
    lateinit var listViewReminder: ListView

    lateinit var btnRegistrarRec: Button
    lateinit var btnActualizarRec: Button
    lateinit var btnEliminarRec: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_recordatorio)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        //Variable pasada
        var correoEstudiante: String
        correoEstudiante = intent.getStringExtra(CORREO_ESTUDIANTE).toString()


        //Inicializar variables
        spinnerMateriaRecordatorio = findViewById(R.id.spinnerMateriaRecordatorio)
        spinnerTipoRecordatorio = findViewById(R.id.spinnerTipoRecordatorio)
        txtNombreRec = findViewById(R.id.txtNombreRec)
        txtDescripcionRec = findViewById(R.id.txtDescripcionRec)
        listViewReminder = findViewById(R.id.listViewReminder)

        btnRegistrarRec = findViewById(R.id.btnRegistrarRec)
        btnActualizarRec = findViewById(R.id.btnActualizarRec)
        btnEliminarRec = findViewById(R.id.btnEliminarRec)


        /*/////////////////////////////////ListView escuchador

        */

        consultarRecordatorio(correoEstudiante)
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
                    spinnerMateriaRecordatorio?.adapter = arrayAdapter
                }


        var materiaString: String = ""
        spinnerMateriaRecordatorio?.onItemSelectedListener = object :


            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                materiaString = spinnerMateriaRecordatorio.selectedItem.toString()


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        ///Llenar spinner Tipo recordatorio

        val tipoRecordatorio = arrayOf("Tarea", "Examen", "Prueba", "Trabajo grupal", "Revision", "Otro")
        val arrayAdapterTipoRecordatorio = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipoRecordatorio)
        spinnerTipoRecordatorio?.adapter = arrayAdapterTipoRecordatorio

        var tipoRecordatorioString: String = "Tipo recordatorio"
        spinnerTipoRecordatorio?.onItemSelectedListener = object :


            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                tipoRecordatorioString = tipoRecordatorio[position].toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }



        var seleccionado: String = ""

        listViewReminder.setOnItemClickListener { parent, view, position, id ->
            selectedSchedulePosition = position
            seleccionado = recordatorios[selectedSchedulePosition].materia.toString()
            selectedSchedulePosition = position
            spinnerMateriaRecordatorio.setSelection(
                getIndex(
                    spinnerMateriaRecordatorio,
                    recordatorios[selectedSchedulePosition].materia.toString()
                )
            )
            txtNombreRec.setText(recordatorios[selectedSchedulePosition].nombre.toString())

            spinnerTipoRecordatorio.setSelection(
                getIndex(
                    spinnerTipoRecordatorio,
                    recordatorios[selectedSchedulePosition].tipo.toString()
                )
            )
            txtDescripcionRec.setText(recordatorios[selectedSchedulePosition].descripcion.toString())

        }



        btnRegistrarRec.setOnClickListener {

            //Crear variables para enviar tabla
            val nombreMateria = spinnerMateriaRecordatorio.selectedItem.toString()
            val nombreRecordatorio = txtNombreRec.text.toString()
            val tipoRecordatorio = spinnerTipoRecordatorio.selectedItem.toString()
            val descripcionRecordatorio = txtDescripcionRec.text.toString()


            crearRecordatorio(
                RecordatorioModelClass(nombreMateria, nombreRecordatorio, tipoRecordatorio, descripcionRecordatorio),
                correoEstudiante
            )
            consultarRecordatorio(correoEstudiante)

        }

        btnActualizarRec.setOnClickListener {

            //Crear variables para enviar tabla
            val nombreMateria = spinnerMateriaRecordatorio.selectedItem.toString()
            val nombreRecordatorio = txtNombreRec.text.toString()
            val tipoRecordatorio = spinnerTipoRecordatorio.selectedItem.toString()
            val descripcionRecordatorio = txtDescripcionRec.text.toString()
            actualizarRecordatorio(
                RecordatorioModelClass(nombreMateria, nombreRecordatorio, tipoRecordatorio, descripcionRecordatorio),
                correoEstudiante,
                RecordatorioModelClass(
                    recordatorios[selectedSchedulePosition].materia,
                    recordatorios[selectedSchedulePosition].nombre,
                    recordatorios[selectedSchedulePosition].tipo,
                    recordatorios[selectedSchedulePosition].descripcion
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
            consultarRecordatorio(correoEstudiante)


        }

        btnEliminarRec.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Confirmación de Eliminación")
            //dialogBuilder.setIcon(R.drawable.ic_warning)
            dialogBuilder.setMessage("¿Esta seguro que desea eliminar el recordatorio?")
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
                eliminarRecordatorio(
                    correoEstudiante,
                    RecordatorioModelClass(
                        recordatorios[selectedSchedulePosition].materia,
                        recordatorios[selectedSchedulePosition].nombre,
                        recordatorios[selectedSchedulePosition].tipo,
                        recordatorios[selectedSchedulePosition].descripcion
                    )
                )

                consultarRecordatorio(correoEstudiante)
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


    fun consultarRecordatorio(correo: String) {
        val db = Firebase.firestore
        recordatorios.clear()
        db.collection(COLECCION).document(correo).collection(COLECCION_MATERIAS)
            .get()
            .addOnSuccessListener { document ->
                for (result in document) {
                    //horarios.clear()

                    db.collection(COLECCION).document(correo).collection(COLECCION_MATERIAS)
                        .document(result.id).collection(
                            RECORDATORIOS
                        )
                        .get()
                        .addOnSuccessListener { document2 ->
                            //horarios.clear()
                            for (result2 in document2) {


                                recordatorios.add(
                                    RecordatorioModelClass(
                                        result.id, result2.get("nombre").toString(), result2.get(
                                            "tipo"
                                        ).toString(), result2.get("descripcion").toString()
                                    )
                                )
                                //Log.d("jfihasi",horario.toString())

                            }

                            //Poblar en ListView información usando mi adaptador
                            val recordatorioAdapter = RecordatorioAdapter(this, recordatorios)
                            listViewReminder.adapter = recordatorioAdapter


                        }


                }
                //Poblar en ListView información usando mi adaptador
                //val contactoAdaptador = MateriaAdapter(this, materias as ArrayList<String>)
                //listViewSubjects.adapter = contactoAdaptador


            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Error al obtener datos de recordatorios:-> {$e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        limpiarCamposEditables()
    }


    fun limpiarCamposEditables() {
        spinnerMateriaRecordatorio.setSelection(0)
        spinnerTipoRecordatorio.setSelection(0)
        txtNombreRec.setText("")
        txtDescripcionRec.setText("")

    }

    fun crearRecordatorio(recordatorio: RecordatorioModelClass, correo: String) {

        val db = Firebase.firestore
        ids.clear()
        var id: Int = 1
        val docRef = db.collection(COLECCION).document(correo).collection(COLECCION_MATERIAS)
            .document(recordatorio.materia).collection(
                RECORDATORIOS
            )
            .get()
            .addOnSuccessListener { document ->
                for (result in document) {
                    //contador++
                    ids.add(result.id.toInt())
                }
                id=devolverId(ids)
                db.collection(COLECCION).document(correo).collection(COLECCION_MATERIAS)
                    .document(recordatorio.materia).collection(
                        RECORDATORIOS
                    ).document(id.toString())
                    .set(
                        RecordatorioCrearModelClass(
                            recordatorio.nombre,
                            recordatorio.tipo,
                            recordatorio.descripcion
                        )
                    ) //.add(contactoHashMap)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(this, "Recordatorio creado exitosamente", Toast.LENGTH_LONG)
                            .show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            this,
                            "Error al crear el recordatorio:-> {$e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
            .addOnFailureListener { e ->
                db.collection(COLECCION).document(correo).collection(COLECCION_MATERIAS)
                    .document(recordatorio.materia).collection(
                        RECORDATORIOS
                    ).document(id.toString())
                    .set(
                        RecordatorioCrearModelClass(
                            recordatorio.nombre,
                            recordatorio.tipo,
                            recordatorio.descripcion
                        )
                    ) //.add(contactoHashMap)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(this, "Recordatorio creado exitosamente", Toast.LENGTH_LONG)
                            .show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            this,
                            "Error al crear el recordatorio:-> {$e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
    }

    fun actualizarRecordatorio(
        recordatorioNuevo: RecordatorioModelClass,
        correo: String,
        recordatorioViejo: RecordatorioModelClass
    ) {


        val db = Firebase.firestore

        var idDocumento: String = ""

        if (!recordatorioNuevo.materia.equals(recordatorioViejo.materia)) {

            crearRecordatorio(recordatorioNuevo,correo)
            eliminarRecordatorio(correo,recordatorioViejo)

        }else{


            val docRef = db.collection(COLECCION).document(correo).collection(COLECCION_MATERIAS)
                .document(recordatorioViejo.materia).collection(
                    RECORDATORIOS
                )
                .get()
                .addOnSuccessListener { document ->
                    for (result in document) {


                        if (result.get("nombre").toString()
                                .equals(recordatorioViejo.nombre) && result.get("tipo")
                                .toString()
                                .equals(recordatorioViejo.tipo) && result.get("descripcion")
                                .toString().equals(recordatorioViejo.descripcion)
                        ) {
                            idDocumento = result.id
                        }
                    }

                    db.collection(COLECCION).document(correo).collection(COLECCION_MATERIAS)
                        .document(recordatorioViejo.materia).collection(
                            RECORDATORIOS
                        ).document(idDocumento)
                        .set(
                            RecordatorioModelClass(
                                recordatorioNuevo.materia,
                                recordatorioNuevo.nombre,
                                recordatorioNuevo.tipo,
                                recordatorioNuevo.descripcion
                            )
                        ) //.add(contactoHashMap)
                        .addOnSuccessListener { documentReference ->
                            Toast.makeText(this, "Recordatorio actualizado exitosamente", Toast.LENGTH_LONG)
                                .show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                this,
                                "Error al actualizar el recordatorio:-> {$e.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                }
        }
    }

    fun eliminarRecordatorio( correo: String,
                         recordatorioViejo: RecordatorioModelClass) {

        val db = Firebase.firestore

        var idDocumento: String = ""
        val docRef = db.collection(COLECCION).document(correo).collection(COLECCION_MATERIAS)
            .document(recordatorioViejo.materia).collection(
                RECORDATORIOS
            )
            .get()
            .addOnSuccessListener { document ->
                for (result in document) {

                    if (result.get("nombre").toString()
                            .equals(recordatorioViejo.nombre) && result.get("tipo")
                            .toString()
                            .equals(recordatorioViejo.tipo) && result.get("descripcion")
                            .toString().equals(recordatorioViejo.descripcion)
                    ) {
                        idDocumento = result.id
                    }
                }

                //val db = Firebase.firestore
                db.collection(COLECCION)
                    .document(correo).collection(COLECCION_MATERIAS).document(recordatorioViejo.materia)
                    .collection(RECORDATORIOS)
                    .document(idDocumento)
                    .delete()
                    .addOnSuccessListener {
                        Toast.makeText(this, "Recordatorio eliminado exitosamente", Toast.LENGTH_LONG)
                            .show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            this,
                            "Error al eliminar el recordatorio:-> {$e.message}",
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

