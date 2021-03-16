package com.proyecto.horarioestudiantil

data class HorarioModelClass(var materia:String,var dia:String, var horaInicio:String, var horaFin:String) {

    constructor():this("","","","")

}
