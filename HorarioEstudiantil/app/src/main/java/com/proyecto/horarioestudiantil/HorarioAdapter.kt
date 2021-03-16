package com.proyecto.horarioestudiantil

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class HorarioAdapter(private val context: Activity, private val horario: ArrayList<HorarioModelClass>)   : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.schedule_list, null, true)

        //val spinnerMateria=rowView.findViewById<Spinner>(R.id.spinnerMateria)

        //val textViewSubject = rowView.findViewById<TextView>(R.id.textViewSubject)

        //spinnerMateria.selectedItem = "${horarios[position].idSubject}"

        //textViewSubject.text = "  ${materias[position].NameSubject}   "

        val textViewMateria = rowView.findViewById<TextView>(R.id.txtMateria)
        val textViewDiaMateria=rowView.findViewById<TextView>(R.id.txtNombre)
        val textViewHoraInicioMateria=rowView.findViewById<TextView>(R.id.txtTipoRecordatorio)
        val textViewHoraFinMateria=rowView.findViewById<TextView>(R.id.txtDescripcionRecordatorio)

        textViewMateria.text = "  ${horario[position].materia}   "
        textViewDiaMateria.text = "  ${horario[position].dia}   "
        textViewHoraInicioMateria.text = "  ${horario[position].horaInicio}   "
        textViewHoraFinMateria.text = "  ${horario[position].horaFin}   "



        return rowView
    }

    override fun getItem(position: Int): Any? {
        return horario.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return horario.size
    }
}
