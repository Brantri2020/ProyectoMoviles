package com.proyecto.horarioestudiantil

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class VerHorarioActivity : AppCompatActivity() {

    ///Hora1
    lateinit var hora1Lun: TextView
    lateinit var hora1Mar: TextView
    lateinit var hora1Mie: TextView
    lateinit var hora1Jue: TextView
    lateinit var hora1Vie: TextView
    lateinit var hora1Sab: TextView
    lateinit var hora1Dom: TextView

    ///Hora2
    lateinit var hora2Lun: TextView
    lateinit var hora2Mar: TextView
    lateinit var hora2Mie: TextView
    lateinit var hora2Jue: TextView
    lateinit var hora2Vie: TextView
    lateinit var hora2Sab: TextView
    lateinit var hora2Dom: TextView

    ///Hora3
    lateinit var hora3Lun: TextView
    lateinit var hora3Mar: TextView
    lateinit var hora3Mie: TextView
    lateinit var hora3Jue: TextView
    lateinit var hora3Vie: TextView
    lateinit var hora3Sab: TextView
    lateinit var hora3Dom: TextView

    ///Hora4
    lateinit var hora4Lun: TextView
    lateinit var hora4Mar: TextView
    lateinit var hora4Mie: TextView
    lateinit var hora4Jue: TextView
    lateinit var hora4Vie: TextView
    lateinit var hora4Sab: TextView
    lateinit var hora4Dom: TextView

    ///Hora5
    lateinit var hora5Lun: TextView
    lateinit var hora5Mar: TextView
    lateinit var hora5Mie: TextView
    lateinit var hora5Jue: TextView
    lateinit var hora5Vie: TextView
    lateinit var hora5Sab: TextView
    lateinit var hora5Dom: TextView

    ///Hora6
    lateinit var hora6Lun: TextView
    lateinit var hora6Mar: TextView
    lateinit var hora6Mie: TextView
    lateinit var hora6Jue: TextView
    lateinit var hora6Vie: TextView
    lateinit var hora6Sab: TextView
    lateinit var hora6Dom: TextView

    ///Hora7
    lateinit var hora7Lun: TextView
    lateinit var hora7Mar: TextView
    lateinit var hora7Mie: TextView
    lateinit var hora7Jue: TextView
    lateinit var hora7Vie: TextView
    lateinit var hora7Sab: TextView
    lateinit var hora7Dom: TextView

    ///Hora8
    lateinit var hora8Lun: TextView
    lateinit var hora8Mar: TextView
    lateinit var hora8Mie: TextView
    lateinit var hora8Jue: TextView
    lateinit var hora8Vie: TextView
    lateinit var hora8Sab: TextView
    lateinit var hora8Dom: TextView

    ///Hora9
    lateinit var hora9Lun: TextView
    lateinit var hora9Mar: TextView
    lateinit var hora9Mie: TextView
    lateinit var hora9Jue: TextView
    lateinit var hora9Vie: TextView
    lateinit var hora9Sab: TextView
    lateinit var hora9Dom: TextView

    ///Hora10
    lateinit var hora10Lun: TextView
    lateinit var hora10Mar: TextView
    lateinit var hora10Mie: TextView
    lateinit var hora10Jue: TextView
    lateinit var hora10Vie: TextView
    lateinit var hora10Sab: TextView
    lateinit var hora10Dom: TextView

    ///Hora11
    lateinit var hora11Lun: TextView
    lateinit var hora11Mar: TextView
    lateinit var hora11Mie: TextView
    lateinit var hora11Jue: TextView
    lateinit var hora11Vie: TextView
    lateinit var hora11Sab: TextView
    lateinit var hora11Dom: TextView

    ///Hora12
    lateinit var hora12Lun: TextView
    lateinit var hora12Mar: TextView
    lateinit var hora12Mie: TextView
    lateinit var hora12Jue: TextView
    lateinit var hora12Vie: TextView
    lateinit var hora12Sab: TextView
    lateinit var hora12Dom: TextView

    ///Hora13
    lateinit var hora13Lun: TextView
    lateinit var hora13Mar: TextView
    lateinit var hora13Mie: TextView
    lateinit var hora13Jue: TextView
    lateinit var hora13Vie: TextView
    lateinit var hora13Sab: TextView
    lateinit var hora13Dom: TextView

    ///Hora14
    lateinit var hora14Lun: TextView
    lateinit var hora14Mar: TextView
    lateinit var hora14Mie: TextView
    lateinit var hora14Jue: TextView
    lateinit var hora14Vie: TextView
    lateinit var hora14Sab: TextView
    lateinit var hora14Dom: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_horario)


        //Variable pasada
        var correoEstudiante: String
        correoEstudiante = intent.getStringExtra(CORREO_ESTUDIANTE).toString()


        //Inicializar variables
        //Hora 1
        hora1Lun = findViewById(R.id.hora1Lun)
        hora1Mar = findViewById(R.id.hora1Mar)
        hora1Mie = findViewById(R.id.hora1Mie)
        hora1Jue = findViewById(R.id.hora1Jue)
        hora1Vie = findViewById(R.id.hora1Vie)
        hora1Sab = findViewById(R.id.hora1Sab)
        hora1Dom = findViewById(R.id.hora1Dom)

        //Hora 2
        hora2Lun = findViewById(R.id.hora2Lun)
        hora2Mar = findViewById(R.id.hora2Mar)
        hora2Mie = findViewById(R.id.hora2Mie)
        hora2Jue = findViewById(R.id.hora2Jue)
        hora2Vie = findViewById(R.id.hora2Vie)
        hora2Sab = findViewById(R.id.hora2Sab)
        hora2Dom = findViewById(R.id.hora2Dom)

        //Hora 3
        hora3Lun = findViewById(R.id.hora3Lun)
        hora3Mar = findViewById(R.id.hora3Mar)
        hora3Mie = findViewById(R.id.hora3Mie)
        hora3Jue = findViewById(R.id.hora3Jue)
        hora3Vie = findViewById(R.id.hora3Vie)
        hora3Sab = findViewById(R.id.hora3Sab)
        hora3Dom = findViewById(R.id.hora3Dom)

        //Hora 4
        hora4Lun = findViewById(R.id.hora4Lun)
        hora4Mar = findViewById(R.id.hora4Mar)
        hora4Mie = findViewById(R.id.hora4Mie)
        hora4Jue = findViewById(R.id.hora4Jue)
        hora4Vie = findViewById(R.id.hora4Vie)
        hora4Sab = findViewById(R.id.hora4Sab)
        hora4Dom = findViewById(R.id.hora4Dom)

        //Hora 5
        hora5Lun = findViewById(R.id.hora5Lun)
        hora5Mar = findViewById(R.id.hora5Mar)
        hora5Mie = findViewById(R.id.hora5Mie)
        hora5Jue = findViewById(R.id.hora5Jue)
        hora5Vie = findViewById(R.id.hora5Vie)
        hora5Sab = findViewById(R.id.hora5Sab)
        hora5Dom = findViewById(R.id.hora5Dom)

        //Hora 6
        hora6Lun = findViewById(R.id.hora6Lun)
        hora6Mar = findViewById(R.id.hora6Mar)
        hora6Mie = findViewById(R.id.hora6Mie)
        hora6Jue = findViewById(R.id.hora6Jue)
        hora6Vie = findViewById(R.id.hora6Vie)
        hora6Sab = findViewById(R.id.hora6Sab)
        hora6Dom = findViewById(R.id.hora6Dom)

        //Hora 7
        hora7Lun = findViewById(R.id.hora7Lun)
        hora7Mar = findViewById(R.id.hora7Mar)
        hora7Mie = findViewById(R.id.hora7Mie)
        hora7Jue = findViewById(R.id.hora7Jue)
        hora7Vie = findViewById(R.id.hora7Vie)
        hora7Sab = findViewById(R.id.hora7Sab)
        hora7Dom = findViewById(R.id.hora7Dom)

        //Hora 8
        hora8Lun = findViewById(R.id.hora8Lun)
        hora8Mar = findViewById(R.id.hora8Mar)
        hora8Mie = findViewById(R.id.hora8Mie)
        hora8Jue = findViewById(R.id.hora8Jue)
        hora8Vie = findViewById(R.id.hora8Vie)
        hora8Sab = findViewById(R.id.hora8Sab)
        hora8Dom = findViewById(R.id.hora8Dom)

        //Hora 9
        hora9Lun = findViewById(R.id.hora9Lun)
        hora9Mar = findViewById(R.id.hora9Mar)
        hora9Mie = findViewById(R.id.hora9Mie)
        hora9Jue = findViewById(R.id.hora9Jue)
        hora9Vie = findViewById(R.id.hora9Vie)
        hora9Sab = findViewById(R.id.hora9Sab)
        hora9Dom = findViewById(R.id.hora9Dom)

        //Hora 10
        hora10Lun = findViewById(R.id.hora10Lun)
        hora10Mar = findViewById(R.id.hora10Mar)
        hora10Mie = findViewById(R.id.hora10Mie)
        hora10Jue = findViewById(R.id.hora10Jue)
        hora10Vie = findViewById(R.id.hora10Vie)
        hora10Sab = findViewById(R.id.hora10Sab)
        hora10Dom = findViewById(R.id.hora10Dom)

        //Hora 11
        hora11Lun = findViewById(R.id.hora11Lun)
        hora11Mar = findViewById(R.id.hora11Mar)
        hora11Mie = findViewById(R.id.hora11Mie)
        hora11Jue = findViewById(R.id.hora11Jue)
        hora11Vie = findViewById(R.id.hora11Vie)
        hora11Sab = findViewById(R.id.hora11Sab)
        hora11Dom = findViewById(R.id.hora11Dom)

        //Hora 12
        hora12Lun = findViewById(R.id.hora12Lun)
        hora12Mar = findViewById(R.id.hora12Mar)
        hora12Mie = findViewById(R.id.hora12Mie)
        hora12Jue = findViewById(R.id.hora12Jue)
        hora12Vie = findViewById(R.id.hora12Vie)
        hora12Sab = findViewById(R.id.hora12Sab)
        hora12Dom = findViewById(R.id.hora12Dom)

        //Hora 13
        hora13Lun = findViewById(R.id.hora13Lun)
        hora13Mar = findViewById(R.id.hora13Mar)
        hora13Mie = findViewById(R.id.hora13Mie)
        hora13Jue = findViewById(R.id.hora13Jue)
        hora13Vie = findViewById(R.id.hora13Vie)
        hora13Sab = findViewById(R.id.hora13Sab)
        hora13Dom = findViewById(R.id.hora13Dom)

        //Hora 14
        hora14Lun = findViewById(R.id.hora14Lun)
        hora14Mar = findViewById(R.id.hora14Mar)
        hora14Mie = findViewById(R.id.hora14Mie)
        hora14Jue = findViewById(R.id.hora14Jue)
        hora14Vie = findViewById(R.id.hora14Vie)
        hora14Sab = findViewById(R.id.hora14Sab)
        hora14Dom = findViewById(R.id.hora14Dom)


        ///Cambiar a modo horizontal
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)


        val db = Firebase.firestore
        val docRef = db.collection(COLECCION).document(correoEstudiante).collection(
            COLECCION_MATERIAS
        )
            .get()
            .addOnSuccessListener { document ->

                for (result in document) {

                    val docRef2 = db.collection(COLECCION).document(correoEstudiante).collection(
                        COLECCION_MATERIAS
                    ).document(result.id).collection(
                        COLECCION
                    )
                        .get()
                        .addOnSuccessListener { document2 ->

                            for (result2 in document2) {

                                //Aqui va el llenado de text view

                                var horasTotal: ArrayList<Int>
                                horasTotal = horas(
                                    result2.get("horaInicio").toString(),
                                    result2.get("horaFin").toString()
                                )



                                if (result2.get("dia").toString().equals("Lunes")) {

                                    for (i in horasTotal[0]..horasTotal[horasTotal.size - 1]) {

                                        if (i == 7) {
                                            hora1Lun.setText(result.id)
                                        } else if (i == 8) {
                                            hora2Lun.setText(result.id)
                                        } else if (i == 9) {
                                            hora3Lun.setText(result.id)
                                        } else if (i == 10) {
                                            hora4Lun.setText(result.id)
                                        } else if (i == 11) {
                                            hora5Lun.setText(result.id)
                                        } else if (i == 12) {
                                            hora6Lun.setText(result.id)
                                        } else if (i == 13) {
                                            hora7Lun.setText(result.id)
                                        } else if (i == 14) {
                                            hora8Lun.setText(result.id)
                                        } else if (i == 15) {
                                            hora9Lun.setText(result.id)
                                        } else if (i == 16) {
                                            hora10Lun.setText(result.id)
                                        } else if (i == 17) {
                                            hora11Lun.setText(result.id)
                                        } else if (i == 18) {
                                            hora12Lun.setText(result.id)
                                        } else if (i == 19) {
                                            hora13Lun.setText(result.id)
                                        } else if (i == 20) {
                                            hora14Lun.setText(result.id)
                                        }

                                    }

                                }else if (result2.get("dia").toString().equals("Martes")) {

                                    for (i in horasTotal[0]..horasTotal[horasTotal.size - 1]) {

                                        if (i == 7) {
                                            hora1Mar.setText(result.id)
                                        } else if (i == 8) {
                                            hora2Mar.setText(result.id)
                                        } else if (i == 9) {
                                            hora3Mar.setText(result.id)
                                        } else if (i == 10) {
                                            hora4Mar.setText(result.id)
                                        } else if (i == 11) {
                                            hora5Mar.setText(result.id)
                                        } else if (i == 12) {
                                            hora6Mar.setText(result.id)
                                        } else if (i == 13) {
                                            hora7Mar.setText(result.id)
                                        } else if (i == 14) {
                                            hora8Mar.setText(result.id)
                                        } else if (i == 15) {
                                            hora9Mar.setText(result.id)
                                        } else if (i == 16) {
                                            hora10Mar.setText(result.id)
                                        } else if (i == 17) {
                                            hora11Mar.setText(result.id)
                                        } else if (i == 18) {
                                            hora12Mar.setText(result.id)
                                        } else if (i == 19) {
                                            hora13Mar.setText(result.id)
                                        } else if (i == 20) {
                                            hora14Mar.setText(result.id)
                                        }

                                    }

                                }else if (result2.get("dia").toString().equals("Miercoles")) {

                                    for (i in horasTotal[0]..horasTotal[horasTotal.size - 1]) {

                                        if (i == 7) {
                                            hora1Mie.setText(result.id)
                                        } else if (i == 8) {
                                            hora2Mie.setText(result.id)
                                        } else if (i == 9) {
                                            hora3Mie.setText(result.id)
                                        } else if (i == 10) {
                                            hora4Mie.setText(result.id)
                                        } else if (i == 11) {
                                            hora5Mie.setText(result.id)
                                        } else if (i == 12) {
                                            hora6Mie.setText(result.id)
                                        } else if (i == 13) {
                                            hora7Mie.setText(result.id)
                                        } else if (i == 14) {
                                            hora8Mie.setText(result.id)
                                        } else if (i == 15) {
                                            hora9Mie.setText(result.id)
                                        } else if (i == 16) {
                                            hora10Mie.setText(result.id)
                                        } else if (i == 17) {
                                            hora11Mie.setText(result.id)
                                        } else if (i == 18) {
                                            hora12Mie.setText(result.id)
                                        } else if (i == 19) {
                                            hora13Mie.setText(result.id)
                                        } else if (i == 20) {
                                            hora14Mie.setText(result.id)
                                        }

                                    }

                                }else if (result2.get("dia").toString().equals("Jueves")) {

                                    for (i in horasTotal[0]..horasTotal[horasTotal.size - 1]) {

                                        if (i == 7) {
                                            hora1Jue.setText(result.id)
                                        } else if (i == 8) {
                                            hora2Jue.setText(result.id)
                                        } else if (i == 9) {
                                            hora3Jue.setText(result.id)
                                        } else if (i == 10) {
                                            hora4Jue.setText(result.id)
                                        } else if (i == 11) {
                                            hora5Jue.setText(result.id)
                                        } else if (i == 12) {
                                            hora6Jue.setText(result.id)
                                        } else if (i == 13) {
                                            hora7Jue.setText(result.id)
                                        } else if (i == 14) {
                                            hora8Jue.setText(result.id)
                                        } else if (i == 15) {
                                            hora9Jue.setText(result.id)
                                        } else if (i == 16) {
                                            hora10Jue.setText(result.id)
                                        } else if (i == 17) {
                                            hora11Jue.setText(result.id)
                                        } else if (i == 18) {
                                            hora12Jue.setText(result.id)
                                        } else if (i == 19) {
                                            hora13Jue.setText(result.id)
                                        } else if (i == 20) {
                                            hora14Jue.setText(result.id)
                                        }

                                    }

                                }else if (result2.get("dia").toString().equals("Viernes")) {

                                    for (i in horasTotal[0]..horasTotal[horasTotal.size - 1]) {

                                        if (i == 7) {
                                            hora1Vie.setText(result.id)
                                        } else if (i == 8) {
                                            hora2Vie.setText(result.id)
                                        } else if (i == 9) {
                                            hora3Vie.setText(result.id)
                                        } else if (i == 10) {
                                            hora4Vie.setText(result.id)
                                        } else if (i == 11) {
                                            hora5Vie.setText(result.id)
                                        } else if (i == 12) {
                                            hora6Vie.setText(result.id)
                                        } else if (i == 13) {
                                            hora7Vie.setText(result.id)
                                        } else if (i == 14) {
                                            hora8Vie.setText(result.id)
                                        } else if (i == 15) {
                                            hora9Vie.setText(result.id)
                                        } else if (i == 16) {
                                            hora10Vie.setText(result.id)
                                        } else if (i == 17) {
                                            hora11Vie.setText(result.id)
                                        } else if (i == 18) {
                                            hora12Vie.setText(result.id)
                                        } else if (i == 19) {
                                            hora13Vie.setText(result.id)
                                        } else if (i == 20) {
                                            hora14Vie.setText(result.id)
                                        }

                                    }

                                }else if (result2.get("dia").toString().equals("Sabado")) {

                                    for (i in horasTotal[0]..horasTotal[horasTotal.size - 1]) {

                                        if (i == 7) {
                                            hora1Sab.setText(result.id)
                                        } else if (i == 8) {
                                            hora2Sab.setText(result.id)
                                        } else if (i == 9) {
                                            hora3Sab.setText(result.id)
                                        } else if (i == 10) {
                                            hora4Sab.setText(result.id)
                                        } else if (i == 11) {
                                            hora5Sab.setText(result.id)
                                        } else if (i == 12) {
                                            hora6Sab.setText(result.id)
                                        } else if (i == 13) {
                                            hora7Sab.setText(result.id)
                                        } else if (i == 14) {
                                            hora8Sab.setText(result.id)
                                        } else if (i == 15) {
                                            hora9Sab.setText(result.id)
                                        } else if (i == 16) {
                                            hora10Sab.setText(result.id)
                                        } else if (i == 17) {
                                            hora11Sab.setText(result.id)
                                        } else if (i == 18) {
                                            hora12Sab.setText(result.id)
                                        } else if (i == 19) {
                                            hora13Sab.setText(result.id)
                                        } else if (i == 20) {
                                            hora14Sab.setText(result.id)
                                        }

                                    }

                                }else if (result2.get("dia").toString().equals("Domingo")) {

                                    for (i in horasTotal[0]..horasTotal[horasTotal.size - 1]) {

                                        if (i == 7) {
                                            hora1Dom.setText(result.id)
                                        } else if (i == 8) {
                                            hora2Dom.setText(result.id)
                                        } else if (i == 9) {
                                            hora3Dom.setText(result.id)
                                        } else if (i == 10) {
                                            hora4Dom.setText(result.id)
                                        } else if (i == 11) {
                                            hora5Dom.setText(result.id)
                                        } else if (i == 12) {
                                            hora6Dom.setText(result.id)
                                        } else if (i == 13) {
                                            hora7Dom.setText(result.id)
                                        } else if (i == 14) {
                                            hora8Dom.setText(result.id)
                                        } else if (i == 15) {
                                            hora9Dom.setText(result.id)
                                        } else if (i == 16) {
                                            hora10Dom.setText(result.id)
                                        } else if (i == 17) {
                                            hora11Dom.setText(result.id)
                                        } else if (i == 18) {
                                            hora12Dom.setText(result.id)
                                        } else if (i == 19) {
                                            hora13Dom.setText(result.id)
                                        } else if (i == 20) {
                                            hora14Dom.setText(result.id)
                                        }

                                    }

                                }


                            }


                        }


                }


            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Error al mostrar materias:-> {$e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }


    }

    fun horas(hora1: String, hora2: String): ArrayList<Int> {

        var arrayHoras = ArrayList<Int>()

        val hora1_String = hora1.split(":".toRegex()).toTypedArray()
        val hora2_String = hora2.split(":".toRegex()).toTypedArray()

        val hora1_Int: Int = hora1_String[0].toInt()
        val hora2_Int: Int = hora2_String[0].toInt() - 1

        if (hora1_Int < hora2_Int) {

            for (i in hora1_Int..hora2_Int) {
                arrayHoras.add(i)
            }

        } else {

            for (i in hora2_Int..hora1_Int) {
                arrayHoras.add(i)
            }


        }

        return arrayHoras


    }


}