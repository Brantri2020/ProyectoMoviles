package com.proyecto.horarioestudiantil

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MateriaAdapter (private val context: Activity, private val materias: ArrayList<String>)   : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.subject_list, null, true)

        //val materias = mutableListOf<String>()


        val textViewSubject = rowView.findViewById<TextView>(R.id.textViewSubject)


        textViewSubject.text = "  ${materias[position]}   "
        return rowView
    }

    override fun getItem(position: Int): Any? {
        return materias.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return materias.size
    }
}
