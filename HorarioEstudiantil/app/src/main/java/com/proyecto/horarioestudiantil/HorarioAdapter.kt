package com.proyecto.horarioestudiantil

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Spinner

class HorarioAdapter (private val context: Activity, private val horarios: ArrayList<HorarioModelClass>)   : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.schedule_list, null, true)

        //val spinnerMateria=rowView.findViewById<Spinner>(R.id.spinnerMateria)

        //val textViewSubject = rowView.findViewById<TextView>(R.id.textViewSubject)

        //spinnerMateria.selectedItem = "${horarios[position].idSubject}"

        //textViewSubject.text = "  ${materias[position].NameSubject}   "
        return rowView
    }

    override fun getItem(position: Int): Any? {
        return horarios.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return horarios.size
    }
}
