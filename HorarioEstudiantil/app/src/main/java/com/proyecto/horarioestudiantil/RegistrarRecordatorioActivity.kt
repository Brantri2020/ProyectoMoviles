package com.proyecto.horarioestudiantil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RegistrarRecordatorioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_recordatorio)
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
